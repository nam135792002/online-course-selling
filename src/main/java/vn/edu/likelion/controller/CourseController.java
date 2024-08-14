package vn.edu.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.likelion.model.course.CourseReturnHomePageResponse;
import vn.edu.likelion.service.impl.CourseServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(
        name = "REST APIs for Course Resource"
)
public class CourseController {
    @Autowired private CourseServiceImpl courseService;

    @Operation(
            summary = "Get all course REST API in Home Page for all end-users",
            description = "Get all course REST API is used to list of all courses from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @GetMapping("/home-page")
    public ResponseEntity<?> getAllReturnHomePage(){
        List<CourseReturnHomePageResponse> listCourses = courseService.listCourseReturnHomePage();
        if (listCourses.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(listCourses);
    }

    @Operation(
            summary = "Get course return page course detail by slug REST API for all end-users",
            description = "Get course return page course detail by slug REST API is used to get single course from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @GetMapping("/get/{slug}")
    public ResponseEntity<?> getDetailCourse(@PathVariable(value = "slug") String slug){
        return ResponseEntity.ok(courseService.getCourseDetail(slug));
    }
}
