package vn.edu.likelion.model.chapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private long totalLesson;

    @JsonProperty("total_lesson_done")
    private long totalLessonDone;

    @JsonProperty("duration_chapter")
    private LocalTime durationChapter;

    @JsonIgnore
    private long totalSecondDuration;

    @JsonProperty("lessons")
    private List<LessonDTO> listLessons = new ArrayList<>();

    public ChapterDTO(Integer id, String name, long totalLesson, long totalLessonDone, double totalSecondDuration) {
        this.id = id;
        this.name = name;
        this.totalLesson = totalLesson;
        this.totalLessonDone = totalLessonDone;
        this.totalSecondDuration = (long)totalSecondDuration;
    }
}
