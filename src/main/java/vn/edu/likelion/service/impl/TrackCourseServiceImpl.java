package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Lesson;
import vn.edu.likelion.entity.TrackCourse;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.CustomHttpStatus;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.chapter.ChapterDTO;
import vn.edu.likelion.model.course.CourseReturnLearningResponse;
import vn.edu.likelion.model.lesson.LessonDTO;
import vn.edu.likelion.repository.*;
import vn.edu.likelion.service.TrackCourseInterface;
import vn.edu.likelion.utility.AppConstant;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class TrackCourseServiceImpl implements TrackCourseInterface {
    @Autowired
    private TrackCourseRepository trackCourseRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public CourseReturnLearningResponse trackCourseDetail(String slug, Integer lessonId) {
        String email = AppConstant.getEmailFromContextHolder();

        Course course = courseRepository.findCoursesBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "slug", slug));

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        if (!orderRepository.existsOrderByUserAndCourse(user, course)) {
            throw new ApiException(CustomHttpStatus.NOT_PURCHASE);
        }

        return buildCourseLearningResponse(course, user, lessonId);
    }

    @Override
    public String confirmLesson(Integer lessonId, LocalTime duration) {
        String email = AppConstant.getEmailFromContextHolder();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        TrackCourse trackCourse = trackCourseRepository.findTrackCourseByLessonIdAndUserId(lesson.getId(), user.getId());

        if(trackCourse == null) throw new ApiException(CustomHttpStatus.NOT_LESSON);
        if(!trackCourse.isUnlock() || trackCourse.isDone()) throw new ApiException(CustomHttpStatus.NOT_ACCESS_LESSON);
        if(duration != null){
            trackCourse.setTrackLesson(duration);
            trackCourseRepository.save(trackCourse);
            return "SUCCESS";
        }else{

            List<TrackCourse> listTrackCourses = trackCourseRepository
                    .findAllByCourseAndUser(lesson.getChapter().getCourse().getId(), user.getId(),
                    Sort.by(Sort.Direction.ASC, "id"));

            Optional<Integer> lessonIdNext = listTrackCourses.stream()
                    .filter(track -> track.getLesson().getId().equals(lessonId))
                    .map(track -> {
                        int index = listTrackCourses.indexOf(track);
                        if(index != -1 && index < listTrackCourses.size() - 1){
                            return listTrackCourses.get(index + 1).getLesson().getId();
                        }else{
                            return -1;
                        }
                    }).findFirst();

            if(lessonIdNext.get() != -1 ){
                trackCourseRepository.updateDone(trackCourse.getLesson().getId(), trackCourse.getUser().getId());
                Lesson lessonNext = lessonRepository.findById(lessonIdNext.get())
                        .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonIdNext.get()));

                TrackCourse trackCourseNext = trackCourseRepository.findTrackCourseByLessonIdAndUserId(lessonNext.getId(), user.getId());
                if(!trackCourseNext.isUnlock()){
                    trackCourseRepository.updateUnlock(lessonNext.getId(), user.getId());
                }
                return "SUCCESS";
            }else{
                trackCourseRepository.updateLessonLastDone(trackCourse.getLesson().getId(), trackCourse.getUser().getId());
                return "Bạn đã hoàn thành khóa học này";
            }
        }
    }

    private CourseReturnLearningResponse buildCourseLearningResponse(Course course, User user, Integer lessonId) {
        CourseReturnLearningResponse courseLearning = modelMapper.map(course, CourseReturnLearningResponse.class);

        List<TrackCourse> listTrack = trackCourseRepository.findAllByCourseAndUser(courseLearning.getId(), user.getId(),
                Sort.by(Sort.Direction.ASC, "id"));

        Optional<TrackCourse> firstIsCurrent = listTrack.stream().filter(TrackCourse::isCurrent).findFirst();
        if(lessonId != 0){
            Integer lessonIsCurrent = firstIsCurrent.get().getLesson().getId();

            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

            if(!Objects.equals(lesson.getChapter().getCourse().getId(), course.getId()))
                throw new ApiException(CustomHttpStatus.NOT_EXISTED_LESSON);

            TrackCourse trackCourse = trackCourseRepository.findTrackCourseByLessonIdAndUserId(lesson.getId(), user.getId());
            TrackCourse trackCoursePre = trackCourseRepository.findTrackCourseByLessonIdAndUserId(lessonIsCurrent, user.getId());

            if(trackCourse == null) throw new ApiException(CustomHttpStatus.NOT_LESSON);
            if(!trackCourse.isUnlock()) throw new ApiException(CustomHttpStatus.NOT_ACCESS_LESSON);
            trackCourseRepository.updateCurrentLesson(trackCourse.getId());
            trackCourseRepository.updatePreLesson(trackCoursePre.getId());
            return null;
        }else{
            Optional<TrackCourse> trackCourseCurrent = listTrack.stream().filter(TrackCourse::isCurrent).findFirst();

            if(trackCourseCurrent.isPresent()){
                courseLearning.setLessonCurrent(trackCourseCurrent.get().getLesson().getId());
                courseLearning.setLessonUrl(trackCourseCurrent.get().getLesson().getUrl());
                courseLearning.setCurrentTime(trackCourseCurrent.get().getTrackLesson());

                int currentIndex = listTrack.indexOf(trackCourseCurrent.get());
                TrackCourse trackCoursePre = (currentIndex > 0) ? listTrack.get(currentIndex - 1) : null;
                TrackCourse trackCourseNext = (currentIndex < listTrack.size() - 1) ? listTrack.get(currentIndex + 1) : null;
                courseLearning.setLessonPre(trackCoursePre != null ? trackCoursePre.getLesson().getId() : null);
                courseLearning.setLessonNext(trackCourseNext != null ? trackCourseNext.getLesson().getId() : null);

            }else{
                courseLearning.setLessonCurrent(listTrack.get(listTrack.size()-1).getLesson().getId());
                courseLearning.setLessonPre(listTrack.get(listTrack.size()-2).getLesson().getId());
                courseLearning.setLessonNext(null);
                courseLearning.setLessonUrl(listTrack.get(listTrack.size()-1).getLesson().getUrl());
            }
        }

        int totalLesson = 0;
        int totalLessonDone = 0;

        for (ChapterDTO chapterDTO : courseLearning.getListChapters()) {
            int[] lessonsCount = calculateChapterDetails(chapterDTO, user);
            chapterDTO.setTotalLesson(lessonsCount[0]);
            chapterDTO.setTotalLessonDone(lessonsCount[1]);

            totalLesson += lessonsCount[0];
            totalLessonDone += lessonsCount[1];
        }

        courseLearning.setTotalLesson(totalLesson);
        courseLearning.setTotalLessonDone(totalLessonDone);
        courseLearning.setAverageLesson(calculateAverageLessonDone(totalLesson, totalLessonDone));

        return courseLearning;
    }

    private int[] calculateChapterDetails(ChapterDTO chapterDTO, User user) {
        int totalLesson = chapterDTO.getListLessons().size();
        int totalLessonDone = 0;

        Duration durationInChapter = Duration.ZERO;

        for (LessonDTO lessonDTO : chapterDTO.getListLessons()) {
            TrackCourse trackCourse = trackCourseRepository.findTrackCourseByLessonIdAndUserId(lessonDTO.getId(), user.getId());
            if (trackCourse.isDone()) {
                lessonDTO.setDone(true);
                totalLessonDone++;
            }
            lessonDTO.setUnlock(trackCourse.isUnlock());
            durationInChapter = durationInChapter.plus(Duration.ofMinutes(lessonDTO.getDuration().getMinute())
                    .plusSeconds(lessonDTO.getDuration().getSecond()));
        }

        chapterDTO.setDurationChapter(convertDurationToLocalTime(durationInChapter));

        return new int[]{totalLesson, totalLessonDone};
    }

    private LocalTime convertDurationToLocalTime(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return LocalTime.of((int) hours, (int) minutes, (int) seconds);
    }

    private int calculateAverageLessonDone(int totalLesson, int totalLessonDone) {
        if (totalLesson == 0) return 0;
        return Math.round((float) totalLessonDone * 100 / totalLesson);
    }

}
