package autumn.user;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, unique = true, length = 40)
    private String email;

    @Column(nullable = false, length = 40)
    private String password;

    @Column(name = "sign_up_time", nullable = false)
    private Timestamp signUpTime;

    @Column(name = "last_visit_time", nullable = false)
    private Timestamp lastVisitTime;

    public User() {
    }

    public User(String username, String email, String password, Timestamp signUpTime, Timestamp lastVisitTime) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.signUpTime = signUpTime;
        this.lastVisitTime = lastVisitTime;
    }
}
