package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.*;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.chapter.ChapterDTO;
import vn.edu.likelion.model.course.CourseReturnDetailResponse;
import vn.edu.likelion.model.course.CourseReturnHomePageResponse;
import vn.edu.likelion.model.course.CourseReturnResultSearch;
import vn.edu.likelion.model.lesson.LessonDTO;
import vn.edu.likelion.repository.CourseRepository;
import vn.edu.likelion.repository.OrderRepository;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.service.CourseInterface;
import vn.edu.likelion.utility.AppConstant;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseInterface {
    @Autowired private CourseRepository courseRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
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
        Course course = courseRepository.findCoursesBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "slug", slug));

        CourseReturnDetailResponse courseReturnDetailResponse = modelMapper.map(course, CourseReturnDetailResponse.class);

        String email = AppConstant.getEmailFromContextHolder();
        if(!email.equals("anonymousUser")){
            User user = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
            if (orderRepository.existsOrderByUserAndCourse(user, course)) courseReturnDetailResponse.setPurchase(true);
        }

        courseReturnDetailResponse.setDecs(course.getDescription());
        for (CourseDetail courseDetail : course.getListCourseDetails()){
            if(courseDetail.getType().equals(InformationType.TARGET)){
                courseReturnDetailResponse.getTarget().add(courseDetail.getValue());
            }else courseReturnDetailResponse.getRequire().add(courseDetail.getValue());
        }

        return courseReturnDetailResponse;
    }

    @Override
    public List<CourseReturnResultSearch> listCourseByKeyword(String keyword) {
        List<Course> listCourse = courseRepository.searchByKeyword(keyword);
        return listCourse.stream().map(course -> modelMapper.map(course, CourseReturnResultSearch.class)).toList();
    }
}
