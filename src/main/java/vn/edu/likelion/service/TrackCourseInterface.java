package vn.edu.likelion.service;

import vn.edu.likelion.model.course.CourseReturnLearningResponse;

public interface TrackCourseInterface {
    CourseReturnLearningResponse trackCourseDetail(String slug, Integer lessonId);
    String confirmLesson(Integer lessonId);
}
