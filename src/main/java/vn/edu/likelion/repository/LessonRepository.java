package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

}
