package rs.raf.WebProgramiranjeDomaci7.resources;

import rs.raf.WebProgramiranjeDomaci7.entities.Comment;
import rs.raf.WebProgramiranjeDomaci7.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(@Valid Comment comment, @PathParam("id") Integer id) {
        return this.commentService.addComment(comment, id);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> findCommentsForPost(@PathParam("id") Integer id) {
        return this.commentService.allCommentsForPost(id);
    }
}
