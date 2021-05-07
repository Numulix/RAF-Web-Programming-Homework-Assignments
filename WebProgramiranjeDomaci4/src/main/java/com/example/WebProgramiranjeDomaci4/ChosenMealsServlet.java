package com.example.WebProgramiranjeDomaci4;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

@WebServlet(name = "chosenMealsServlet", value = "/odabrana-jela")
public class ChosenMealsServlet extends HttpServlet {

    private String password = "";

    public void init() {
        try {
            File passwordFile = new File(this.getServletContext().getRealPath("password.txt"));
            Scanner in = new Scanner(passwordFile);
            password = in.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        if (request.getParameter("lozinka") != null && request.getParameter("lozinka").equals(password)) {
            out.println("<h1>Odabrana jela</h1>");

            out.println("<form method=\"post\" action=\"/odabrana-jela?lozinka=" + password + "\">");
            out.println("<button type=\"submit\">Ocisti</button>");
            out.println("</form>");

            out.println("<h1>PONEDELJAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : MenuReader.getInstance().getMondayCount().entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>UTORAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : MenuReader.getInstance().getTuesdayCount().entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>SREDA</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : MenuReader.getInstance().getWednesdayCount().entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>CETVRTAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : MenuReader.getInstance().getThursdayCount().entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>PETAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : MenuReader.getInstance().getFridayCount().entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            response.setStatus(403);
            out.println("<h1>UNAUTHORIZED</h1>");
        }
        out.println("</html></body>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (Map.Entry entry: MenuReader.getInstance().getMondayCount().entrySet())
            entry.setValue(0);
        for (Map.Entry entry: MenuReader.getInstance().getTuesdayCount().entrySet())
            entry.setValue(0);
        for (Map.Entry entry: MenuReader.getInstance().getWednesdayCount().entrySet())
            entry.setValue(0);
        for (Map.Entry entry: MenuReader.getInstance().getThursdayCount().entrySet())
            entry.setValue(0);
        for (Map.Entry entry: MenuReader.getInstance().getFridayCount().entrySet())
            entry.setValue(0);

        getServletContext().setAttribute("submittedList", new ArrayList<>());

        doGet(request, response);
    }
}
