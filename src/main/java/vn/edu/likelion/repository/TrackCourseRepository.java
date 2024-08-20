package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.TrackCourse;

@Repository
public interface TrackCourseRepository extends JpaRepository<TrackCourse, Integer> {

}
