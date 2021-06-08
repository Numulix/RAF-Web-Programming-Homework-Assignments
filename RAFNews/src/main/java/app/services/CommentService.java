package app.services;

import app.entities.Comment;
import app.repositories.comment.CommentRepository;
import app.repositories.comment.SqlCommentRepository;

import java.util.List;

public class CommentService {

    private static final CommentRepository commentRepository = new SqlCommentRepository();

    public static Comment addComment(Comment comment) {
        return commentRepository.addComment(comment);
    }

    public static List<Comment> getCommentsForArticle(Integer id) {
        return commentRepository.getCommentsForArticle(id);
    };

}
