package vn.edu.likelion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "users")

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", length = 45, nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(length = 100)
    private String avatar;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(name = "created_time", nullable = false)
    private LocalDate createdTime;

    private boolean enabled;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}
