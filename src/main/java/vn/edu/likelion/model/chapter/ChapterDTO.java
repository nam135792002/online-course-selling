package vn.edu.likelion.model.chapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.likelion.model.lesson.LessonDTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDTO {
    private Integer id;

    @JsonProperty("chapter_title")
    private String name;

    @JsonProperty("total_lesson")
    private int totalLesson;

    @JsonProperty("total_lesson_done")
    private int totalLessonDone;

    @JsonProperty("duration_chapter")
    private LocalTime durationChapter;

    @JsonProperty("lessons")
    private List<LessonDTO> listLessons = new ArrayList<>();
}
