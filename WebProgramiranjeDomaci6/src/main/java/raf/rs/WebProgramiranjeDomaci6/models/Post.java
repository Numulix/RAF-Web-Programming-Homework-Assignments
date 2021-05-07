package raf.rs.WebProgramiranjeDomaci6.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Post implements Serializable {

    private int id;
    private String author;
    private String title;
    private String content;
    private Date postdate;

    public Post() {
    }

    public Post(int id, String author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.postdate = new Date();
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPostdate() {
        return postdate;
    }
}
