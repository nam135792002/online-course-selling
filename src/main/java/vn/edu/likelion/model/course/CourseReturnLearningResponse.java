package vn.edu.likelion.model.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.model.chapter.ChapterDTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseReturnLearningResponse {
    Integer id;

    String title;

    @JsonProperty("chapter")
    List<ChapterDTO> listChapters = new ArrayList<>();

    @JsonProperty("total_lesson_of_course")
    int totalLesson;

    @JsonProperty("total_lesson_done_of_course")
    int totalLessonDone;

    @JsonProperty("average_lesson")
    int averageLesson;

    @JsonProperty("lesson_current")
    Integer lessonCurrent;

    @JsonProperty("lesson_pre")
    Integer lessonPre;

    @JsonProperty("lesson_next")
    Integer lessonNext;

    @JsonProperty("lesson_url")
    String lessonUrl;

    @JsonProperty("current_time")
    LocalTime currentTime;
}
