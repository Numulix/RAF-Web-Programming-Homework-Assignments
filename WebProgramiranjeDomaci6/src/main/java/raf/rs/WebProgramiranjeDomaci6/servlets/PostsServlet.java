package raf.rs.WebProgramiranjeDomaci6.servlets;

import raf.rs.WebProgramiranjeDomaci6.models.Post;
import raf.rs.WebProgramiranjeDomaci6.repository.posts.IPostRepository;
import raf.rs.WebProgramiranjeDomaci6.repository.posts.InMemoryPostRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PostsServlet", value = {"/", "/posts"})
public class PostsServlet extends HttpServlet {

    private IPostRepository postRepository;

    @Override
    public void init() throws ServletException {
        this.postRepository = new InMemoryPostRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", this.postRepository.all());
        req.getRequestDispatcher("/posts.jsp").forward(req, resp);
    }


}
