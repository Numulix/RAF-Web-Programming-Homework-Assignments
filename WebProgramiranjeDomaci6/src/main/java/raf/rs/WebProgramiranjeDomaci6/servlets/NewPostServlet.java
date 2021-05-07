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

@WebServlet(name = "NewPostServlet", value = "/new-post")
public class NewPostServlet extends HttpServlet {

    private IPostRepository postRepository;

    @Override
    public void init() throws ServletException {
        postRepository = new InMemoryPostRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/new-post.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String author = req.getParameter("author");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (!author.isEmpty() && !title.isEmpty() && !content.isEmpty()) {
            postRepository.insert(new Post(InMemoryPostRepository.idCounter, author, title, content));
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/posts");
    }
}
