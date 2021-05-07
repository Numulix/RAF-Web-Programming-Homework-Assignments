package com.example.WebProgramiranjeDomaci4;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/")
public class HelloServlet extends HttpServlet {
//    private final String absolutePath = "/home/data/WEB PROGRAMIRANJE - RAF/WebProgramiranjeDomaci4/src/main/resources/";

    public void init() {
        getServletContext().setAttribute("submittedList", new ArrayList<>());
        readDailyMenu("ponedeljak.txt");
        readDailyMenu("utorak.txt");
        readDailyMenu("sreda.txt");
        readDailyMenu("cetvrtak.txt");
        readDailyMenu("petak.txt");
    }

    private void readDailyMenu(String fileName) {
        try {
            File dailyMenu = new File(this.getServletContext().getRealPath(fileName));
            Scanner sc = new Scanner(dailyMenu);
            switch (fileName) {
                case "ponedeljak.txt":
                    while (sc.hasNextLine()) {
                        MenuReader.getInstance().getMondayCount().put(sc.nextLine(), 0);
                    }
                    break;
                case "utorak.txt":
                    while (sc.hasNextLine()) {
                        MenuReader.getInstance().getTuesdayCount().put(sc.nextLine(), 0);
                    }
                    break;
                case "sreda.txt":
                    while (sc.hasNextLine()) {
                        MenuReader.getInstance().getWednesdayCount().put(sc.nextLine(), 0);
                    }
                    break;
                case "cetvrtak.txt":
                    while (sc.hasNextLine()) {
                        MenuReader.getInstance().getThursdayCount().put(sc.nextLine(), 0);
                    }
                    break;
                case "petak.txt":
                    while (sc.hasNextLine()) {
                        MenuReader.getInstance().getFridayCount().put(sc.nextLine(), 0);
                    }
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        ArrayList<String> submittedIds = (ArrayList<String>) getServletContext().getAttribute("submittedList");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        if (!submittedIds.contains(request.getSession().getId())) {
            out.println("<h1>Choose your food</h1>");
            out.println("<h2>Odaberite vas rucak:</h2>");
            out.println("<form action=\"submit\" method=\"post\">");

            out.println("<label for=\"monday\">Ponedeljak:</label><br/>");
            out.println("<select id=\"monday\" name=\"monday\">");
            for (Map.Entry entry : MenuReader.getInstance().getMondayCount().entrySet()) {
                out.println("<option value=\"" + entry.getKey() + "\">" + entry.getKey() + "</option>");
            }
            out.println("</select><br/>");


            out.println("<label for=\"tuesday\">Utorak:</label><br/>");
            out.println("<select id=\"tuesday\" name=\"tuesday\">");
            for (Map.Entry entry : MenuReader.getInstance().getTuesdayCount().entrySet()) {
                out.println("<option value=\"" + entry.getKey() + "\">" + entry.getKey() + "</option>");
            }
            out.println("</select><br/>");

            out.println("<label for=\"wednesday\">Sreda:</label><br/>");
            out.println("<select id=\"wednesday\" name=\"wednesday\">");
            for (Map.Entry entry : MenuReader.getInstance().getWednesdayCount().entrySet()) {
                out.println("<option value=\"" + entry.getKey() + "\">" + entry.getKey() + "</option>");
            }
            out.println("</select><br/>");

            out.println("<label for=\"thursday\">Cetvrtak:</label><br/>");
            out.println("<select id=\"thursday\" name=\"thursday\">");
            for (Map.Entry entry : MenuReader.getInstance().getThursdayCount().entrySet()) {
                out.println("<option value=\"" + entry.getKey() + "\">" + entry.getKey() + "</option>");
            }
            out.println("</select><br/>");


            out.println("<label for=\"friday\">Petak:</label><br/>");
            out.println("<select id=\"friday\" name=\"friday\">");
            for (Map.Entry entry : MenuReader.getInstance().getFridayCount().entrySet()) {
                out.println("<option value=\"" + entry.getKey() + "\">" + entry.getKey() + "</option>");
            }
            out.println("</select><br/>");

            out.println("<br/><button type=\"submit\">Potvrdite unos</button>");
            out.println("</form>");
        } else {
            out.println("<h1>Vec ste potvrdili vasu porudzbinu za ovu nedelju</h1>");
            out.println("<ul>");
            out.println("<li><strong>Ponedeljak</strong> - " + request.getSession().getAttribute("monday") + "</li>");
            out.println("<li><strong>Utorak</strong> - " + request.getSession().getAttribute("tuesday") + "</li>");
            out.println("<li><strong>Sreda</strong> - " + request.getSession().getAttribute("wednesday") + "</li>");
            out.println("<li><strong>Cetvrtak</strong> - " + request.getSession().getAttribute("thursday") + "</li>");
            out.println("<li><strong>Petak</strong> - " + request.getSession().getAttribute("friday") + "</li>");
            out.println("</ul>");
        }
        out.println("</body></html>");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void destroy() {
    }
}