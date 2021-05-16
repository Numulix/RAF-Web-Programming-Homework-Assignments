package rs.raf.WebProgramiranjeDomaci7.services;

import rs.raf.WebProgramiranjeDomaci7.entities.Comment;
import rs.raf.WebProgramiranjeDomaci7.repositories.comments.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment, Integer id) {
        return this.commentRepository.addComment(comment, id);
    }

    public List<Comment> allCommentsForPost(Integer id) {
        return this.commentRepository.allCommentsForPost(id);
    }

}
