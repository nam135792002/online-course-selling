package vn.edu.likelion.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.TrackCourse;
import vn.edu.likelion.model.chapter.ChapterDTO;
import vn.edu.likelion.model.lesson.LessonDTO;

import java.util.List;

@Repository
public interface TrackCourseRepository extends JpaRepository<TrackCourse, Integer> {
    @Query("select t from TrackCourse t where t.lesson.id = ?1 and t.user.id = ?2")
    TrackCourse findTrackCourseByLessonIdAndUserId(Integer lessonId, Integer userId);

    @Query("select t from TrackCourse t where t.lesson.chapter.course.id = ?1 and t.user.id = ?2")
    List<TrackCourse> findAllByCourseAndUser(Integer courseId, Integer userId, Sort sort);

    @Query("select new vn.edu.likelion.model.chapter.ChapterDTO(t.lesson.chapter.id, t.lesson.chapter.name, count(t), " +
            "sum(case when t.isDone = true then 1 else 0 end), " +
            "sum(t.lesson.duration) " +
            "from TrackCourse t " +
            "where t.lesson.chapter.course.id = ?1 and t.user.id = ?2 " +
            "group by t.lesson.chapter.id, t.lesson.chapter.name")
    List<ChapterDTO> printAllChapter(Integer courseId, Integer userId, Sort sort);


    @Query("select new vn.edu.likelion.model.lesson.LessonDTO(t.lesson.id, t.lesson.name, t.lesson.duration, t.isDone, t.isUnlock) " +
            "from TrackCourse t " +
            "where t.lesson.chapter.id = ?1 and t.user.id = ?2")
    List<LessonDTO> printALlLesson(Integer chapterId, Integer userId, Sort sort);


    @Modifying
    @Query("update TrackCourse t set t.isDone = true, t.trackLesson = null, t.isCurrent = false where t.lesson.id = ?1 and t.user.id = ?2")
    void updateDone(Integer lessonPreId, Integer userId);

    @Modifying
    @Query("update TrackCourse t set t.isDone = true, t.trackLesson = null where t.lesson.id = ?1 and t.user.id = ?2")
    void updateLessonLastDone(Integer lessonPreId, Integer userId);

    @Modifying
    @Query("update TrackCourse t set t.isUnlock = true, t.isCurrent = true where t.lesson.id = ?1 and t.user.id = ?2")
    void updateUnlock(Integer lessonNextId, Integer userId);

    @Modifying
    @Query("update TrackCourse t set t.isCurrent = true where t.id = ?1")
    void updateCurrentLesson(Integer trackCourseId);

    @Modifying
    @Query("update TrackCourse t set t.isCurrent = false where t.id = ?1")
    void updatePreLesson(Integer trackCourseId);
}
