package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByEmail(String email);
    @Modifying
    @Query("update User u set u.verificationCode = null, u.enabled = true where u.email = ?1")
    void enable(String email);
    User findUserByEmail(String email);
}
