package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

}
