package rs.raf.WebProgramiranjeDomaci7.repositories.comments;

import rs.raf.WebProgramiranjeDomaci7.entities.Comment;

import java.util.List;

public interface CommentRepository {
    public Comment addComment(Comment comment, Integer id);
    public List<Comment> allCommentsForPost(Integer id);
}
