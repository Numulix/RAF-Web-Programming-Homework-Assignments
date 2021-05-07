package raf.rs.WebProgramiranjeDomaci6.repository.posts;

import raf.rs.WebProgramiranjeDomaci6.models.Post;

import java.util.List;

public interface IPostRepository {
    public List<Post> all();
    public Post find(int id);
    public void insert(Post post);
}
