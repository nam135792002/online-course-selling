package vn.edu.likelion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.model.ApiResponse;
import vn.edu.likelion.model.review.ReviewRequest;
import vn.edu.likelion.service.impl.ReviewServiceImpl;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewServiceImpl reviewService;

    @PostMapping("/save")
    public ResponseEntity<?> add(@RequestBody ReviewRequest reviewRequest){
        return new ResponseEntity<>(reviewService.createReview(reviewRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(reviewService.listAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ReviewRequest reviewRequest){
        return ResponseEntity.ok(reviewService.updateReview(reviewRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer reviewId){
        return ResponseEntity.ok(new ApiResponse(reviewService.deleteReview(reviewId)));
    }
}
