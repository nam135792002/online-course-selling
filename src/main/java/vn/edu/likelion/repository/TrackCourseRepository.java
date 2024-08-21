package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.TrackCourse;

@Repository
public interface TrackCourseRepository extends JpaRepository<TrackCourse, Integer> {
    @Query("select t from TrackCourse t where t.lesson.id = ?1 and t.user.id = ?2")
    TrackCourse findTrackCourseByLessonIdAndUserId(Integer lessonId, Integer userId);
}
