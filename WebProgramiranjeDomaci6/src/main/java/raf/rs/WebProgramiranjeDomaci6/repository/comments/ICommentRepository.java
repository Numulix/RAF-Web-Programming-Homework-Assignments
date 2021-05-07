package raf.rs.WebProgramiranjeDomaci6.repository.comments;

import raf.rs.WebProgramiranjeDomaci6.models.Comment;

import java.util.List;

public interface ICommentRepository {
    public List<Comment> getCommentsForPost(int id);
    public void insert(Comment comment);
}
