package vn.edu.likelion.service;

import vn.edu.likelion.model.review.ReviewRequest;
import vn.edu.likelion.model.review.ReviewResponse;

import java.util.List;

public interface ReviewInterface {
    ReviewResponse createReview(ReviewRequest reviewRequest);
    List<ReviewResponse> listAll();
    ReviewResponse updateReview(ReviewRequest reviewRequest);
}
