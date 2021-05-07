package raf.rs.WebProgramiranjeDomaci6.models;

import java.io.Serializable;

public class Comment implements Serializable {

    private String name;
    private String comment;
    private int postId;

    public Comment() {
    }

    public Comment(String name, String comment, int postId) {
        this.name = name;
        this.comment = comment;
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public int getPostId() {
        return postId;
    }
}
