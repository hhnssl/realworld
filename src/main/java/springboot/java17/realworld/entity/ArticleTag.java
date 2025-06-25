package springboot.java17.realworld.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Article_tag")
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article; // ArticleTagId.article과 연결

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    public void setTag(TagEntity tag) {
        this.tag = tag;

        tag.getArticleTagList().add(this);
    }

}
