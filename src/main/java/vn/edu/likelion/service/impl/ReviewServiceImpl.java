package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Review;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.CustomHttpStatus;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.review.ReviewRequest;
import vn.edu.likelion.model.review.ReviewResponse;
import vn.edu.likelion.repository.CourseRepository;
import vn.edu.likelion.repository.OrderRepository;
import vn.edu.likelion.repository.ReviewRepository;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.service.ReviewInterface;
import vn.edu.likelion.utility.AppConstant;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewServiceImpl implements ReviewInterface {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        Review review = modelMapper.map(reviewRequest, Review.class);

        String email = AppConstant.getEmailFromContextHolder();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        Course course = courseRepository.findById(reviewRequest.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", reviewRequest.getCourseId()));

        if(!orderRepository.existsOrderByUserAndCourse(user, course)){
            throw new ApiException(CustomHttpStatus.NOT_PURCHASE);
        }

        if(reviewRepository.existsReviewByUserAndCourse(user, course)){
            throw new ApiException(CustomHttpStatus.USER_COMMENTED);
        }
        review.setReviewTime(LocalDateTime.now());
        review.setUser(user);
        review.setCourse(course);
        Review savedReview = reviewRepository.save(review);

        return convertToResponse(savedReview);
    }

    @Override
    public List<ReviewResponse> listAll() {
        List<Review> listReviews = reviewRepository.findAll();
        return listReviews.stream().map(this::convertToResponse).toList();
    }

    @Override
    public ReviewResponse updateReview(ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(reviewRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewRequest.getId()));

        String email = AppConstant.getEmailFromContextHolder();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        if(!Objects.equals(review.getUser().getId(), user.getId()))
            throw new ApiException(CustomHttpStatus.USER_NOT_COMMENT);

        review.setComment(reviewRequest.getComment());
        Review savedReview = reviewRepository.save(review);
        return convertToResponse(savedReview);
    }

    @Override
    public String deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        String email = AppConstant.getEmailFromContextHolder();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        if(!Objects.equals(review.getUser().getId(), user.getId()))
            throw new ApiException(CustomHttpStatus.USER_NOT_COMMENT);

        reviewRepository.delete(review);
        return "SUCCESS";
    }

    private ReviewResponse convertToResponse(Review savedReview){
        ReviewResponse reviewResponse = modelMapper.map(savedReview, ReviewResponse.class);
        reviewResponse.setFullName(savedReview.getUser().getFullName());
        reviewResponse.setThumbnail(savedReview.getUser().getAvatar());

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(savedReview.getReviewTime(), now);
        reviewResponse.setTimeAgo(formatDuration(duration));
        return reviewResponse;
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + " seconds ago";
        } else if (seconds < 3600) {
            return (seconds / 60) + " minutes ago";
        } else if (seconds < 86400) {
            return (seconds / 3600) + " hours ago";
        } else if (seconds < 2592000) {
            return (seconds / 86400) + " days ago";
        } else if (seconds < 31536000) {
            return (seconds / 2592000) + " months ago";
        } else {
            return (seconds / 31536000) + " years ago";
        }
    }
}
