package springboot.java17.realworld.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    private String image;

    private String bio;

    private String role;

    public void update(String username, String encodedPassword, String image, String bio) {
        if (username != null) this.username = username;
        if (encodedPassword != null) this.password = encodedPassword;
        if (image != null) this.image = image;
        if (bio != null) this.bio = bio;
    }
}
