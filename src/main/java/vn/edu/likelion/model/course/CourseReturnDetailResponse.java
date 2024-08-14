package vn.edu.likelion.model.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String description;

    private String thumbnail;

    @JsonProperty("new_price")
    private long newPrice;

    @JsonProperty("list_course_info")
    private List<CourseInfoResponse> listCourseDetails = new ArrayList<>();
}
