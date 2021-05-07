package raf.rs.WebProgramiranjeDomaci6.servlets;

import raf.rs.WebProgramiranjeDomaci6.models.Comment;
import raf.rs.WebProgramiranjeDomaci6.models.Post;
import raf.rs.WebProgramiranjeDomaci6.repository.comments.ICommentRepository;
import raf.rs.WebProgramiranjeDomaci6.repository.comments.InMemoryCommentRepository;
import raf.rs.WebProgramiranjeDomaci6.repository.posts.IPostRepository;
import raf.rs.WebProgramiranjeDomaci6.repository.posts.InMemoryPostRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SinglePostServlet", value = "/posts/*")
public class SinglePostServlet extends HttpServlet {

    private ICommentRepository commentRepository;
    private IPostRepository postRepository;

    @Override
    public void init() throws ServletException {
        commentRepository = new InMemoryCommentRepository();
        postRepository = new InMemoryPostRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        Post post = postRepository.find(id);
        if (post == null) req.setAttribute("notFound", true);
        List<Comment> commentsForPost = commentRepository.getCommentsForPost(id);
        req.setAttribute("post", post);
        req.setAttribute("comments", commentsForPost);

        req.getRequestDispatcher("/single-post.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String author = req.getParameter("author");
        String comment = req.getParameter("comment");
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        if (!author.isEmpty() && !comment.isEmpty()) {
            commentRepository.insert(new Comment(author, comment, id));
        }

        resp.sendRedirect("/posts/"+id);
    }
}
