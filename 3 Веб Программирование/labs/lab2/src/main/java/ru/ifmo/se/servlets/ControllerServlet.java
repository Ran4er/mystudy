package ru.ifmo.se.servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    //EL adding (Исправить)
    //changeCurrentTimeZone when reloading page (тоже в третьей обязательно)
    //масштабирование (в третьей обязательно)
    //init() and filter() для предобработки (по желанию)

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var INVALID_DATA_MSG = "Please set the data values in correct form.";

        try {
            if (
                    request.getParameter("R") == null
                            || request.getParameter("X") == null
                            || request.getParameter("Y") == null
            ) {
                sendError(response, INVALID_DATA_MSG);
                return;
            }
            if (
                    request.getParameter("R").isEmpty()
                            || request.getParameter("X").isEmpty()
                            || request.getParameter("Y").isEmpty()
            ) {
                sendError(response, INVALID_DATA_MSG);
                return;
            }
            if (
                    Double.parseDouble(request.getParameter("Y")) < -3
                            || Double.parseDouble(request.getParameter("Y")) > 3
            ) {
                sendError(response, INVALID_DATA_MSG);
                return;
            }
            if (
                    Double.parseDouble(request.getParameter("X")) < -5
                            || Double.parseDouble(request.getParameter("X")) > 5
            ) {
                sendError(response, INVALID_DATA_MSG);
                return;
            }

            Double.parseDouble(request.getParameter("X"));
            Integer.parseInt(request.getParameter("R"));

            response.sendRedirect("./checkArea?" + request.getQueryString());
        } catch (Exception e) {
            sendError(response, e.toString());
        }
    }

    private void sendError(HttpServletResponse response, String errorMessage) throws IOException {
        var json = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>() {{
            put("error", errorMessage);
            put("status", "UNPROCESSABLE_ENTITY");
        }};

        response.setContentType("application/json");
        response.getWriter().write(json.toJson(jsonResponse));
        response.setStatus(422);
    }
}
