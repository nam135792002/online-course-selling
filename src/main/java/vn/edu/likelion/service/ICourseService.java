package vn.edu.likelion.service;

import vn.edu.likelion.model.course.CourseReturnDetailResponse;
import vn.edu.likelion.model.course.CourseReturnHomePageResponse;
import vn.edu.likelion.model.course.CourseReturnResultSearch;

import java.util.List;

public interface ICourseService {
    List<CourseReturnHomePageResponse> listCourseReturnHomePage();
    CourseReturnDetailResponse getCourseDetail(String slug);
    List<CourseReturnResultSearch> listCourseByKeyword(String keyword);

}
