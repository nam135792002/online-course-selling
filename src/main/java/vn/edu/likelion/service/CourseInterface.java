package vn.edu.likelion.service;

import vn.edu.likelion.model.course.CourseInfoResponse;
import vn.edu.likelion.model.course.CourseReturnDetailResponse;
import vn.edu.likelion.model.course.CourseReturnHomePageResponse;

import java.util.List;

public interface CourseInterface {
    List<CourseReturnHomePageResponse> listCourseReturnHomePage();
    CourseReturnDetailResponse getCourseDetail(String slug);
}
