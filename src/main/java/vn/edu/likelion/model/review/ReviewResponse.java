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
public class ReviewResponse {
    private Integer id;

    private String comment;

    private int rating;

    @JsonProperty("time_ago")
    private String timeAgo;

    private String thumbnail;

    @JsonProperty("full_name")
    private String fullName;
}
