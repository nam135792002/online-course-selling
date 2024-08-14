package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.Course;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.course.CourseInfoResponse;
import vn.edu.likelion.model.course.CourseReturnDetailResponse;
import vn.edu.likelion.model.course.CourseReturnHomePageResponse;
import vn.edu.likelion.repository.CourseRepository;
import vn.edu.likelion.service.CourseInterface;

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
        return modelMapper.map(course, CourseReturnDetailResponse.class);
    }
}
