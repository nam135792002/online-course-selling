package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Order;
import vn.edu.likelion.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    boolean existsOrderByUserAndCourse(User user, Course course);
    List<Order> findAllByUser(User user);
}
