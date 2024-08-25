package vn.edu.likelion.model.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @JsonProperty("created_time")
    private LocalDateTime reviewTime;

    private String thumbnail;

    @JsonProperty("full_name")
    private String fullName;
}
