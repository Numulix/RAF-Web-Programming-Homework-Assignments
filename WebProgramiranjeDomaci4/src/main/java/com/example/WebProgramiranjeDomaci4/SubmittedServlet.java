package com.example.WebProgramiranjeDomaci4;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "submittedServlet", value = "/submit")
public class SubmittedServlet extends HttpServlet {

    public void init() {}

    private void incrementMenuItems(HttpServletRequest request) {
        incrementByDay("monday", request);
        incrementByDay("tuesday", request);
        incrementByDay("wednesday", request);
        incrementByDay("thursday", request);
        incrementByDay("friday", request);
    }

    private void incrementByDay(String day, HttpServletRequest request) {
        switch (day) {
            case "monday":
                MenuReader.getInstance().getMondayCount().put(request.getParameter(day),
                        MenuReader.getInstance().getMondayCount().get(request.getParameter(day)) + 1);
                break;
            case "tuesday":
                MenuReader.getInstance().getTuesdayCount().put(request.getParameter(day),
                        MenuReader.getInstance().getTuesdayCount().get(request.getParameter(day)) + 1);
                break;
            case "wednesday":
                MenuReader.getInstance().getWednesdayCount().put(request.getParameter(day),
                        MenuReader.getInstance().getWednesdayCount().get(request.getParameter(day)) + 1);
                break;
            case "thursday":
                MenuReader.getInstance().getThursdayCount().put(request.getParameter(day),
                        MenuReader.getInstance().getThursdayCount().get(request.getParameter(day)) + 1);
                break;
            case "friday":
                MenuReader.getInstance().getFridayCount().put(request.getParameter(day),
                        MenuReader.getInstance().getFridayCount().get(request.getParameter(day)) + 1);
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        ArrayList<String> submitted = (ArrayList<String>) getServletContext().getAttribute("submittedList");

        submitted.add(request.getSession().getId());
        request.getSession().setAttribute("monday", request.getParameter("monday"));
        request.getSession().setAttribute("tuesday", request.getParameter("tuesday"));
        request.getSession().setAttribute("wednesday", request.getParameter("wednesday"));
        request.getSession().setAttribute("thursday", request.getParameter("thursday"));
        request.getSession().setAttribute("friday", request.getParameter("friday"));

        incrementMenuItems(request);
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Vasa porudzbina je potvrdjena!</h1>");
        out.println("<ul>");
        out.println("<li>Ponedeljak - " + request.getParameter("monday") + "</li>");
        out.println("<li>Utorak - " + request.getParameter("tuesday") + "</li>");
        out.println("<li>Sreda - " + request.getParameter("wednesday") + "</li>");
        out.println("<li>Cetvrtak - " + request.getParameter("thursday") + "</li>");
        out.println("<li>Petak - " + request.getParameter("friday") + "</li>");
        out.println("</ul>");
        out.println("</body></html>");
    }

}
