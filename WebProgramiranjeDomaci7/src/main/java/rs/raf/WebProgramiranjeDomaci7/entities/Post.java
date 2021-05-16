package rs.raf.WebProgramiranjeDomaci7.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Post {

    private Integer id;

    @NotNull(message = "The post must have an author")
    @NotEmpty(message = "The post must have an author")
    private String author;

    @NotNull(message = "The post must have a title")
    @NotEmpty(message = "The post must have a title")
    private String title;

    @NotNull(message = "The post must have some content")
    @NotEmpty(message = "The post must have some content")
    private String content;

    private Date publishDate;

    public Post() {
    }

    public Post(String author, String title, String content, Date publishDate) {
        this();
        this.author = author;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
    }

    public Post(Integer id, String author, String title, String content, Date publishDate) {
        this(author, title, content, publishDate);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
