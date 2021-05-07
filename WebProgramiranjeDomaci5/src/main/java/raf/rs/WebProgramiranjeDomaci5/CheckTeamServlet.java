package raf.rs.WebProgramiranjeDomaci5;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "checkTeam", value = "/proveri-tim")
public class CheckTeamServlet extends HttpServlet {

    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String teamName = request.getParameter("imeTima");
        String contactEmail = request.getParameter("kontaktMejl");
        String contactPhone = request.getParameter("kontaktTelefon");
        String teamMotto = request.getParameter("motoTima");
        String hackathonInfo = request.getParameter("saznanjeZaHakaton");
        int teamSize = Integer.parseInt(request.getParameter("brojClanova"));

        request.getSession().setAttribute("imeTima", teamName);
        request.getSession().setAttribute("kontaktMejl", contactEmail);
        request.getSession().setAttribute("kontaktTelefon", contactPhone);
        request.getSession().setAttribute("motoTima", teamMotto);
        request.getSession().setAttribute("saznanjeZaHakaton", hackathonInfo);
        request.getSession().setAttribute("brojClanova", teamSize);

        response.sendRedirect("clanovi.jsp");
    }

}
