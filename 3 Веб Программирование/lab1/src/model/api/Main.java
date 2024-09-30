package model.api;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

import com.fastcgi.*;

public class Main {

    private static final Queue<Result> results = new LinkedList<>();

    public static void main(String[] args) {
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            long startTime = System.currentTimeMillis();
            String requestBody = readRequestBody();
            if (requestBody == null || requestBody.isEmpty()) {
                httpResponse(422, "Invalid request");
                continue;
            }

            String[] parts = requestBody.split(",");
            if (parts.length < 3) {
                httpResponse(422, "Missing parameters");
                continue;
            }

            try {
                int x = Integer.parseInt(parts[0].trim());
                Double y = Double.parseDouble(parts[1].trim());
                Double R = Double.parseDouble(parts[2].trim());

                CoordinatesValidator validator = new CoordinatesValidator(x, y, R);
                if (validator.checkData()) {
                    boolean isInArea = new AreaChecker().inArea(x, y, R);
                    String coordsStatus = isInArea ? "<span class='success'>Попал</span>" : "<span class='fail'>Промазал</span>";
                    
                    // Record the result
                    LocalDateTime currentTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedCurrentTime = currentTime.format(formatter);

                    double benchmarkTime = (System.currentTimeMillis() - startTime) / 1000.0;

                    results.add(new Result(x, y, R, coordsStatus, formattedCurrentTime, benchmarkTime));

                    // Generate the HTML table
                    StringBuilder htmlTable = new StringBuilder();
                    htmlTable.append("<table id='outputTable'>")
                             .append("<tr>")
                             .append("<th>x</th>")
                             .append("<th>y</th>")
                             .append("<th>r</th>")
                             .append("<th>Точка входит в ОДЗ</th>")
                             .append("<th>Текущее время</th>")
                             .append("<th>Время работы скрипта</th>")
                             .append("</tr>");
                    
                    for (Result result : results) {
                        htmlTable.append("<tr>")
                                 .append("<td>").append(result.getX()).append("</td>")
                                 .append("<td>").append(result.getY()).append("</td>")
                                 .append("<td>").append(result.getR()).append("</td>")
                                 .append("<td>").append(result.getCoordsStatus()).append("</td>")
                                 .append("<td>").append(result.getCurrentTime()).append("</td>")
                                 .append("<td>").append(result.getBenchmarkTime()).append("</td>")
                                 .append("</tr>");
                    }
                    htmlTable.append("</table>");
                    
                    httpResponse(200, htmlTable.toString());
                } else {
                    httpResponse(422, "Validation failed");
                }
            } catch (NumberFormatException e) {
                httpResponse(422, "Invalid number format");
            }
        }
    }

    private static String readRequestBody() {
        try {
            FCGIInterface.request.inStream.fill();
            var contentLength = FCGIInterface.request.inStream.available();
            var buffer = ByteBuffer.allocate(contentLength);
            var readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0, contentLength);
            return new String(buffer.array(), 0, readBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    private static void httpResponse(int statusCode, String content) {
        String httpResponse = String.format(
            "HTTP/1.1 %d OK\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: %d\r\n" +
            "\r\n" +
            "%s",
            statusCode, content.getBytes(StandardCharsets.UTF_8).length, content
        );
        System.out.println(httpResponse);
    }

}
