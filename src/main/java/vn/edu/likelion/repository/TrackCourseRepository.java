package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.TrackCourse;

@Repository
public interface TrackCourseRepository extends JpaRepository<TrackCourse, Integer> {
    @Query("select t from TrackCourse t where t.lesson.id = ?1 and t.user.id = ?2")
    TrackCourse findTrackCourseByLessonIdAndUserId(Integer lessonId, Integer userId);

    @Modifying
    @Query("update TrackCourse t set t.isDone = true, t.trackLesson = null, t.isCurrent = false where t.lesson.id = ?1 and t.user.id = ?2")
    void updateDone(Integer lessonPreId, Integer userId);

    @Modifying
    @Query("update TrackCourse t set t.isUnlock = true, t.isCurrent = true where t.lesson.id = ?1 and t.user.id = ?2")
    void updateUnlock(Integer lessonNextId, Integer userId);

    @Modifying
    @Query("update TrackCourse t set t.isCurrent = true where t.id = ?1")
    void updateCurrentLesson(Integer trackCourseId);
}
