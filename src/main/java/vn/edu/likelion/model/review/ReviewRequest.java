package vn.edu.likelion.model.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private Integer id;

    private String comment;

    private int rating;

    @JsonProperty("course_id")
    private Integer courseId;
}
