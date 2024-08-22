package vn.edu.likelion.service;

import vn.edu.likelion.model.review.ReviewRequest;
import vn.edu.likelion.model.review.ReviewResponse;

public interface ReviewInterface {
    ReviewResponse createReview(ReviewRequest reviewRequest);
}
