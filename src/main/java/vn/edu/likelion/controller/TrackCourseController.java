package vn.edu.likelion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.model.ApiResponse;
import vn.edu.likelion.service.impl.TrackCourseServiceImpl;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/course/learning")
@CrossOrigin
public class TrackCourseController {
    @Autowired
    private TrackCourseServiceImpl trackCourseInterface;

    @GetMapping("/{slug}")
    public ResponseEntity<?> getAllLesson(@RequestParam(value = "id", required = false) Integer lessonId,
                                          @PathVariable(value = "slug") String slug){
        if(lessonId == null) lessonId = 0;
        return ResponseEntity.ok(trackCourseInterface.trackCourseDetail(slug, lessonId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> confirmLessonIsDone(@PathVariable(value = "id") Integer lessonId,
                                                 @RequestParam(value = "current_time", required = false) LocalTime duration){
        return ResponseEntity.ok(new ApiResponse(trackCourseInterface.confirmLesson(lessonId, duration)));
    }
}
