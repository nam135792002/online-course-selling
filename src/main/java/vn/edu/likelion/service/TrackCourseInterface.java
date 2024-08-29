package vn.edu.likelion.service;

import vn.edu.likelion.model.ApiResponse;
import vn.edu.likelion.model.course.CourseReturnLearningResponse;

import java.time.LocalTime;

public interface TrackCourseInterface {
    CourseReturnLearningResponse trackCourseDetail(String slug, Integer lessonId);
    ApiResponse confirmLesson(Integer lessonId, LocalTime duration);
}
