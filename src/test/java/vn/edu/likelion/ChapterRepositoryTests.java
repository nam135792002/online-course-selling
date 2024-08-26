package vn.edu.likelion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vn.edu.likelion.entity.Chapter;
import vn.edu.likelion.entity.Course;
import vn.edu.likelion.repository.ChapterRepository;
import vn.edu.likelion.repository.CourseRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ChapterRepositoryTests {

    @Autowired private ChapterRepository chapterRepository;
    @Autowired private CourseRepository courseRepository;

    @Test
    public void createChapter(){
        Course course = courseRepository.findById(1).get();
        Chapter chapter1 = new Chapter("Getting Started", course);
        Chapter chapter2 = new Chapter("Programming Tool Setup", course);
        Chapter chapter3 = new Chapter("First Steps", course);
        Chapter chapter4 = new Chapter("IntelliJ Basic", course);
        Chapter chapter5 = new Chapter("OOP", course);
        Chapter chapter6 = new Chapter("Arrays", course);
        Chapter chapter7 = new Chapter("Generics", course);
        Chapter chapter8 = new Chapter("Java Collections", course);
        Chapter chapter9 = new Chapter("Streams", course);
        Chapter chapter10 = new Chapter("Working with Database", course);

        List<Chapter> listChapters = chapterRepository.saveAll(List.of(chapter1, chapter2, chapter3, chapter4, chapter5, chapter6, chapter7, chapter8, chapter9, chapter10));
        Assertions.assertThat(listChapters.size()).isEqualTo(10);

    }

    @Test
    public void createChapter01(){
        Course course = courseRepository.findById(2).get();
        Chapter chapter1 = new Chapter("Introduction to C++", course);
        Chapter chapter2 = new Chapter("Setting Up Development Environment", course);
        Chapter chapter3 = new Chapter("Basic Syntax and Structure", course);
        Chapter chapter4 = new Chapter("Control Flow", course);
        Chapter chapter5 = new Chapter("Functions in C++", course);
        Chapter chapter6 = new Chapter("Object-Oriented Programming", course);
        Chapter chapter7 = new Chapter("Pointers and Memory Management", course);
        Chapter chapter8 = new Chapter("Advanced Data Structures", course);
        Chapter chapter9 = new Chapter("Templates and Generics", course);
        Chapter chapter10 = new Chapter("File Handling in C++", course);

        List<Chapter> listChapters = chapterRepository.saveAll(List.of(chapter1, chapter2, chapter3, chapter4, chapter5, chapter6, chapter7, chapter8, chapter9, chapter10));
        Assertions.assertThat(listChapters.size()).isEqualTo(10);
    }
}
