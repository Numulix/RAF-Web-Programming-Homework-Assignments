package rs.raf.WebProgramiranjeDomaci7;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.WebProgramiranjeDomaci7.repositories.comments.CommentRepository;
import rs.raf.WebProgramiranjeDomaci7.repositories.comments.MySqlCommentRepository;
import rs.raf.WebProgramiranjeDomaci7.repositories.posts.MySqlPostRepository;
import rs.raf.WebProgramiranjeDomaci7.repositories.posts.PostRepository;
import rs.raf.WebProgramiranjeDomaci7.services.CommentService;
import rs.raf.WebProgramiranjeDomaci7.services.PostService;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Application extends ResourceConfig {

    public Application() {
        // Ukljucivanje validacije
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisanje implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlPostRepository.class).to(PostRepository.class).in(Singleton.class);
                this.bindAsContract(PostService.class);

                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bindAsContract(CommentService.class);
            }
        };
        register(binder);

        packages("rs.raf.WebProgramiranjeDomaci7");
    }
}
