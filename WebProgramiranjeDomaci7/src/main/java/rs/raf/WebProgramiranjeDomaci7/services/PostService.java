package rs.raf.WebProgramiranjeDomaci7.services;

import rs.raf.WebProgramiranjeDomaci7.entities.Post;
import rs.raf.WebProgramiranjeDomaci7.repositories.posts.PostRepository;

import javax.inject.Inject;
import java.util.List;

public class PostService {

    @Inject
    private PostRepository postRepository;

    public Post addPost(Post post) {
        return this.postRepository.addPost(post);
    }

    public List<Post> allPosts() {
        return this.postRepository.allPosts();
    }

    public Post findPost(Integer id) {
        return this.postRepository.findPost(id);
    }

}
