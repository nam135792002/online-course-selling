package vn.edu.likelion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vn.edu.likelion.entity.Chapter;
import vn.edu.likelion.entity.Lesson;
import vn.edu.likelion.repository.ChapterRepository;
import vn.edu.likelion.repository.LessonRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class LessonRepositoryTest {
    @Autowired private LessonRepository lessonRepository;
    @Autowired private ChapterRepository chapterRepository;

    @Test
    public void testCreateLesson1(){
        Chapter chapter = chapterRepository.findById(1).get();
        Lesson lesson1 = new Lesson("Introduction To The Course"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1715842020/yvnvlrg32pg2bonr9hbi.mp4",
                chapter);
        Lesson lesson2 = new Lesson("Remaster in Progress",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1717597291/gwxlhhukyywu2bavcl7h.mp4",
                chapter);
        Lesson lesson3 = new Lesson("Which Verion of Java?",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1715786427/swxwii04pqnrb2olowgy.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson1, lesson2, lesson3));
        Assertions.assertThat(listLessons.size()).isEqualTo(3);
    }

    @Test
    public void testCreateLesson2(){
        Chapter chapter = chapterRepository.findById(2).get();
        Lesson lesson1 = new Lesson("Software Tools Introduction"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1715262877/mt58tqmn3v3lkdwxbob6.mp4",
                chapter);
        Lesson lesson2 = new Lesson("Install JDK 17 for Windows",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1714321050/dpqhwsy0beghkxjntwyu.mp4",
                chapter);
        Lesson lesson3 = new Lesson("Install JDK 17 for Mac",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1714278963/wp1dcbjxjidpzilmy7gd.mp4",
                chapter);
        Lesson lesson4 = new Lesson("Install JDK 17 for Linux",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1714272313/ym5axecl2xvv5lx0pb70.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson4));
        Assertions.assertThat(listLessons.size()).isEqualTo(1);
    }

    @Test
    public void testCreateLesson3(){
        Chapter chapter = chapterRepository.findById(3).get();
        Lesson lesson1 = new Lesson("Variables"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712569069/he6w39biizysgme3ceqd.mp4",
                chapter);
        Lesson lesson2 = new Lesson("Casting in Java",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568598/pglcufliizx40hkdpa4o.mp4",
                chapter);
        Lesson lesson3 = new Lesson("primitive Types Challenge",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568466/fjl50mr4zmo0ebdzhbhr.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson1, lesson2, lesson3));
        Assertions.assertThat(listLessons.size()).isEqualTo(3);
    }

    @Test
    public void testCreateLesson4(){
        Chapter chapter = chapterRepository.findById(4).get();
        Lesson lesson1 = new Lesson("From JShell to an IDE"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568309/c9jzpqjwjzrvdxqpf5mh.mp4",
                chapter);
        Lesson lesson2 = new Lesson("Ternary Operator",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712568084/n9ql1gxe0sobe0eyuile.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson1, lesson2));
        Assertions.assertThat(listLessons.size()).isEqualTo(2);
    }

    @Test
    public void testCreateLesson5(){
        Chapter chapter = chapterRepository.findById(5).get();
        Lesson lesson1 = new Lesson("Inheritance"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567824/ju7dv8b4qq6vjiasuepb.mp4",
                chapter);
        Lesson lesson2 = new Lesson("Polymorphism",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567127/jizysfqfpdrd0m0lckq1.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson1, lesson2));
        Assertions.assertThat(listLessons.size()).isEqualTo(2);
    }

    @Test
    public void testCreateLesson6(){
        Chapter chapter = chapterRepository.findById(6).get();
        Lesson lesson1 = new Lesson("Arrays Recap"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567075/gzk8psbinifezucesgnu.mp4",
                chapter);
        Lesson lesson2 = new Lesson("Two Dimensional Arrays",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712567018/wt3ccvwiby77p3vjlqqb.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson1, lesson2));
        Assertions.assertThat(listLessons.size()).isEqualTo(2);
    }

    @Test
    public void testCreateLesson7(){
        Chapter chapter = chapterRepository.findById(7).get();
        Lesson lesson1 = new Lesson("Generics Part 1"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712566919/bsidyrtzyor2fxvupgw3.mp4",
                chapter);
        Lesson lesson2 = new Lesson("Generics Part 2",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712566482/a6kograxfxscnxhyshgf.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson1, lesson2));
        Assertions.assertThat(listLessons.size()).isEqualTo(2);
    }

    @Test
    public void testCreateLesson8(){
        Chapter chapter = chapterRepository.findById(8).get();
        Lesson lesson1 = new Lesson("Introduction to collections"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712556089/srobmpx7pwqfhpvaqowv.mp4",
                chapter);
        Lesson lesson2 = new Lesson("Intro to Set & HashSet",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712555884/qdem0r0wsveiwputamom.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson1, lesson2));
        Assertions.assertThat(listLessons.size()).isEqualTo(2);
    }

    @Test
    public void testCreateLesson9(){
        Chapter chapter = chapterRepository.findById(9).get();
        Lesson lesson1 = new Lesson("Stream Sources"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712555730/wk8xzjpwnqiheic2mxnb.mp4",
                chapter);
        Lesson lesson2 = new Lesson("Code Setup",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1712555481/wxblsi60ehws5sene54v.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson1, lesson2));
        Assertions.assertThat(listLessons.size()).isEqualTo(2);
    }

    @Test
    public void testCreateLesson10(){
        Chapter chapter = chapterRepository.findById(10).get();
        Lesson lesson1 = new Lesson("JDBC"
                , "https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597751/wzvpwpjsfiwh1woyh7fi.mp4",
                chapter);
        Lesson lesson2 = new Lesson("SQL Injection",
                "https://res.cloudinary.com/dqnoopa0x/video/upload/v1710597751/wzvpwpjsfiwh1woyh7fi.mp4",
                chapter);

        List<Lesson> listLessons = lessonRepository.saveAll(List.of(lesson1, lesson2));
        Assertions.assertThat(listLessons.size()).isEqualTo(2);
    }

}
