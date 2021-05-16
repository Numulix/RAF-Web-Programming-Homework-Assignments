package rs.raf.WebProgramiranjeDomaci7.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {

    private Integer id;

    private Integer postId;

    @NotEmpty(message = "Comment must have an author")
    @NotNull(message = "Comment must have an author")
    private String author;

    @NotEmpty(message = "Comment must have content")
    @NotNull(message = "Comment must have content")
    private String content;

    public Comment() {
    }

    public Comment(Integer postId, String author, String content) {
        this();
        this.postId = postId;
        this.author = author;
        this.content = content;
    }

    public Comment(Integer id, Integer postId, String author, String content) {
        this(postId, author, content);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
