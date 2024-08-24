package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Review;
import vn.edu.likelion.entity.User;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsReviewByUserAndCourse(User user, Course course);
    List<Review> findReviewByCourse(Course course);
}
