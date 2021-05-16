package rs.raf.WebProgramiranjeDomaci7.repositories.posts;

import rs.raf.WebProgramiranjeDomaci7.entities.Post;

import java.util.List;

public interface PostRepository {
    public Post addPost(Post post);
    public List<Post> allPosts();
    public Post findPost(Integer id);
}
