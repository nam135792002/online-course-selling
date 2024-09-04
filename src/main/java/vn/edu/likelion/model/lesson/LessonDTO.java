package vn.edu.likelion.model.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
public class LessonDTO {
    private Integer id;

    @JsonProperty("lesson_title")
    private String name;

    private LocalTime duration;

    @JsonProperty("is_done")
    private boolean isDone;

    @JsonProperty("is_unlock")
    private boolean isUnlock;

    public LessonDTO(Integer id, String name, LocalTime duration, boolean isDone, boolean isUnlock) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.isDone = isDone;
        this.isUnlock = isUnlock;
    }
}
