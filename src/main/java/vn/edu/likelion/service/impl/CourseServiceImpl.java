package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.*;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.chapter.ChapterDTO;
import vn.edu.likelion.model.course.CourseReturnDetailResponse;
import vn.edu.likelion.model.course.CourseReturnHomePageResponse;
import vn.edu.likelion.model.lesson.LessonDTO;
import vn.edu.likelion.repository.CourseRepository;
import vn.edu.likelion.service.CourseInterface;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseInterface {
    @Autowired private CourseRepository courseRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public List<CourseReturnHomePageResponse> listCourseReturnHomePage() {
        List<Course> listCourses = courseRepository.findAll();
        return listCourses.stream()
                .map(course -> modelMapper.map(course, CourseReturnHomePageResponse.class))
                .toList();
    }

    @Override
    public CourseReturnDetailResponse getCourseDetail(String slug) {
        Course course = courseRepository.findCoursesBySlug(slug);
        if (course == null){
            throw new ResourceNotFoundException("Course", "slug", slug);
        }

        CourseReturnDetailResponse courseReturnDetailResponse = modelMapper.map(course, CourseReturnDetailResponse.class);
        courseReturnDetailResponse.setDecs(course.getDescription());
        for (CourseDetail courseDetail : course.getListCourseDetails()){
            if(courseDetail.getType().equals(InformationType.TARGET)){
                courseReturnDetailResponse.getTarget().add(courseDetail.getValue());
            }else courseReturnDetailResponse.getRequire().add(courseDetail.getValue());
        }

        for (Chapter chapter : course.getListChapters()){
            courseReturnDetailResponse.getChapter().add(convertChapterToDTO(chapter));
        }

        return courseReturnDetailResponse;
    }

    private ChapterDTO convertChapterToDTO(Chapter chapter){
        ChapterDTO chapterDTO = new ChapterDTO();
        chapterDTO.setId(chapter.getId());
        chapterDTO.setChapterTitle(chapter.getName());

        for (Lesson lesson : chapter.getListLessons()){
            chapterDTO.getLessons().add(new LessonDTO(lesson.getId(), lesson.getName(),
                    lesson.getDuration(), false));
        }

        return chapterDTO;
    }

}
