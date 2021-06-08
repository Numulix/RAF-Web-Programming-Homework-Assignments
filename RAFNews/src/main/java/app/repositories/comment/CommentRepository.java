package app.repositories.comment;

import app.entities.Comment;

import java.util.List;

public interface CommentRepository {
    public Comment addComment(Comment comment);
    public List<Comment> getCommentsForArticle(Integer id);
}
