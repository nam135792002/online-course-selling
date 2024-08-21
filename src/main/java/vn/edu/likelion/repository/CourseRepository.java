package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.Course;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findCoursesBySlug(String slug);

    @Query("select c from Course c where lower(c.title) like lower(concat('%', ?1, '%')) " +
            "or lower(c.description) like lower(concat('%', ?1, '%')) " +
            "or lower(c.category.name) like lower(concat('%', ?1, '%'))")
    List<Course> searchByKeyword(String keyword);

}
