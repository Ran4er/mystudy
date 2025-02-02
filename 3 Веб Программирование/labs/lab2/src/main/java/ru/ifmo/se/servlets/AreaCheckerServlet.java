package ru.ifmo.se.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.ifmo.se.model.Point;

@WebServlet("/checkArea")
public class AreaCheckerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                try {
                    var x = Double.parseDouble(request.getParameter("X"));
                    var y = Double.parseDouble(request.getParameter("Y"));
                    var r = Integer.parseInt(request.getParameter("R"));
                    var point = new Point(x, y, r);

                    ServletContext context = getServletContext();

                    List<String> results = (List<String>) context.getAttribute("results");
                    if (results == null) {
                        results = new ArrayList<>();
                    }

                    results.add(point.toString());

                    context.setAttribute("results", results);

                    request.setAttribute("results", results);

                    var action = request.getParameter("action");
                    if ("submitForm".equals(action)) {
                        request.setAttribute("X", x);
                        request.setAttribute("Y", y);
                        request.setAttribute("R", r);
                        request.setAttribute("result", isInside(x, y, r));

                        var dispatcher = request.getRequestDispatcher("./result.jsp");
                        dispatcher.forward(request, response);

                    } else if ("checkPoint".equals(action)) {
                        var gson = new Gson();
                        Map<String, Object> json = new HashMap<>();
                        json.put("x", x);
                        json.put("y", y);
                        json.put("r", r);
                        json.put("result", isInside(x, y, r));
                        var msg = gson.toJson(json);

                        response.setContentType("application/json");
                        response.getWriter().write(msg);
                    }
                } catch (Exception e) {
                    request.getRequestDispatcher("./index.jsp").forward(request, response);
                }

            }

    private boolean isInside(double x, double y, int r) {

        if (x >= 0 && y >= 0) {
            return (x*x + y*y) <= ((r * r)/4);
        }

        if (x >= 0 && y < 0) {
            return (x >= -r) && (y <= r) && (2 *y - 2*x <= 2*r);
        }

        if (y >= 0 && x < 0) {
            return (x >= -r / 2) && (y <= r);
        }

        // For bottom-left quadrant, always return false
        return false;

    }

}
