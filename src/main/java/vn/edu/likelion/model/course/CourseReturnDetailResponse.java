package vn.edu.likelion.model.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.likelion.entity.Chapter;
import vn.edu.likelion.model.chapter.ChapterDTO;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseReturnDetailResponse {
    private Integer id;

    private String title;

    private String slug;

    private String decs;

    private String thumbnail;

    @JsonProperty("new_price")
    private long newPrice;

    private List<String> target = new ArrayList<>();

    private List<String> require = new ArrayList<>();

    private List<ChapterDTO> chapter = new ArrayList<>();
}
