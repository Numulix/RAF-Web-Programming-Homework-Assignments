package raf.rs.WebProgramiranjeDomaci6.repository.comments;

import raf.rs.WebProgramiranjeDomaci6.models.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryCommentRepository implements ICommentRepository {
    private static final List<Comment> comments = new CopyOnWriteArrayList<>();

    @Override
    public List<Comment> getCommentsForPost(int id) {
        List<Comment> commentList = new ArrayList<>();
        comments.iterator().forEachRemaining(c -> {
            if (c.getPostId() == id) commentList.add(c);
        });

        return commentList;
    }

    @Override
    public void insert(Comment comment) {
        comments.add(comment);
    }
}
