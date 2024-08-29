package vn.edu.likelion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.model.ApiResponse;
import vn.edu.likelion.model.course.CourseReturnHomePageResponse;
import vn.edu.likelion.model.course.CourseReturnResultSearch;
import vn.edu.likelion.service.impl.CourseServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin
public class CourseController {
    private final CourseServiceImpl courseService;

    @GetMapping("/home-page")
    public ResponseEntity<?> getAllReturnHomePage(){
        List<CourseReturnHomePageResponse> listCourses = courseService.listCourseReturnHomePage();
        if (listCourses.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(listCourses);
    }

    @GetMapping("/get/{slug}")
    public ResponseEntity<?> getDetailCourse(@PathVariable(value = "slug") String slug){
        return ResponseEntity.ok(courseService.getCourseDetail(slug));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllCourseByKeyword(@RequestParam(value = "keyword") String keyword){
        List<CourseReturnResultSearch> listCourses = courseService.listCourseByKeyword(keyword);
        if (listCourses.isEmpty()) return ResponseEntity.ok(new ApiResponse("Không tìm thấy nội dung cần tìm kiếm"));
        else return ResponseEntity.ok(listCourses);
    }
}
