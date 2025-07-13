package springboot.java17.realworld.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "articles")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, length = 512)
    private String slug;

    @Column(unique = true, nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    // TEXT 타입으로 지정하여 긴 글도 저장 가능하도록 함
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleTag> articleTags = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void generateSlug() {
        if (this.title != null) {
            this.slug = this.title.toLowerCase().trim().replaceAll("\\s+", "-");
        }
    }

    public void update(String title, String description, String body) {
        if (title != null) {
            this.title = title;
        }
        if (description != null) {
            this.description = description;
        }
        if (body != null) {
            this.body = body;
        }
    }

    // 연관관계 편의 메소드
    public void setAuthor(UserEntity user) {
        this.user = user;
    }
}