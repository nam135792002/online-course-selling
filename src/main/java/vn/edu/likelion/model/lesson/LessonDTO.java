package vn.edu.likelion.model.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Integer id;

    @JsonProperty("lesson_title")
    private String lessonTitle;

    private LocalTime duration;

    @JsonProperty("is_done")
    private boolean isDone;
}
