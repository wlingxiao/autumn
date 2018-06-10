package autumn.user;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@Entity(name = "t_user")
public class User implements UserDetails {

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

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<Role> roles;

    public User() {
    }

    public User(String username, String email, String password, Timestamp signUpTime, Timestamp lastVisitTime) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.signUpTime = signUpTime;
        this.lastVisitTime = lastVisitTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
