package vn.edu.likelion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "track_courses")

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrackCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "track_lesson")
    private LocalTime trackLesson;

    @JoinColumn(name = "is_done")
    private boolean isDone;

    @JoinColumn(name = "is_unlock")
    private boolean isUnlock;

    @Column(name = "is_current")
    private boolean isCurrent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public TrackCourse(User user, Lesson lesson) {
        this.user = user;
        this.lesson = lesson;
    }


}
