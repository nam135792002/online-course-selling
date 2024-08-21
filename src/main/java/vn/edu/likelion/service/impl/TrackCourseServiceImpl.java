package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.TrackCourse;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.CustomHttpStatus;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.chapter.ChapterDTO;
import vn.edu.likelion.model.course.CourseReturnLearningResponse;
import vn.edu.likelion.model.lesson.LessonDTO;
import vn.edu.likelion.repository.CourseRepository;
import vn.edu.likelion.repository.OrderRepository;
import vn.edu.likelion.repository.TrackCourseRepository;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.service.TrackCourseInterface;
import vn.edu.likelion.utility.AppConstant;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
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

    @Override
    public CourseReturnLearningResponse trackCourseDetail(String slug) {
        String email = AppConstant.getEmailFromContextHolder();

        Course course = courseRepository.findCoursesBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "slug", slug));

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        if (!orderRepository.existsOrderByUserAndCourse(user, course)) {
            throw new ApiException(CustomHttpStatus.NOT_PURCHASE);
        }

        return buildCourseLearningResponse(course, user);
    }

    private CourseReturnLearningResponse buildCourseLearningResponse(Course course, User user) {
        CourseReturnLearningResponse courseLearning = modelMapper.map(course, CourseReturnLearningResponse.class);

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
