package vn.edu.likelion.model.chapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.likelion.model.lesson.LessonDTO;

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

    @JsonProperty("lessons")
    private List<LessonDTO> listLessons = new ArrayList<>();
}
