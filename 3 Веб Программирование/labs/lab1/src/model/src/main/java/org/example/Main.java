package org.example;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Objects;

import com.fastcgi.*;

public class Main {

    public static void main (String[] args) {
        FCGIInterface fcgiInterface = new FCGIInterface();
        CoordinatesValidator v = new CoordinatesValidator();
        AreaChecker checker = new AreaChecker();

        while(fcgiInterface.FCGIaccept() >= 0) {
            String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
            if (method.equals("GET")) {
                long time = System.nanoTime();
                String req = FCGIInterface.request.params.getProperty("QUERY_STRING");
                if (!Objects.equals(req, "")) {
                    LinkedHashMap<String, String> m = getValues(req);
                    boolean isShot;
                    boolean isValid;
                    try {
                        isValid = v.check(Integer.parseInt(m.get("x")), Double.parseDouble(m.get("y")), Double.parseDouble(m.get("r")));
                        isShot = checker.inArea(Integer.parseInt(m.get("x")), Double.parseDouble(m.get("y")), Double.parseDouble(m.get("r")));
                    } catch (Exception e){
                        System.out.println(err("Invalid data"));
                        continue;
                    }
                    if (isValid) {
                        System.out.println(resp(isShot, m.get("x"), m.get("y"), m.get("r"), time));
                    }
                    else
                        System.out.println(err(v.getErrorMessage()));
                }
                else
                   System.out.println(err("fill"));
            }
            else
                System.out.println(err("method"));
        }
    }
    private static LinkedHashMap<String, String> getValues(String inpString){
        String[] args = inpString.split("&");
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (String s : args) {
            String[] arg = s.split("=");
            map.put(arg[0], arg[1]);
        }
        return map;
    }
    private static String resp(boolean isShoot, String x, String y, String r, long wt) {
        String content = """
                {"result":"%s","x":"%s","y":"%s","r":"%s","time":"%s","workTime":"%s","error":"all ok"}
                """.formatted(isShoot, x, y, r, (double)(System.nanoTime() - wt) / 10000000, LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return """
                Content-Type: application/json; charset=utf-8
                Content-Length: %d
                
                
                %s
                """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);
    }

    private static String err(String msg) {
        String content = """
                {"error":"%s"}
                """.formatted(msg);
        return """
                Content-Type: application/json charset=utf-8
                Content-Length: %d
                
                
                %s
                """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);
    }
}
