package vn.edu.likelion.model.trackcourse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackCourseResponse {

    @JsonProperty("lesson_id")
    Integer lessonId;

    @JsonProperty("url_video")
    String urlVideo;
}
