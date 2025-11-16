package com.homeheaven.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = null;
        if (statusObj instanceof Integer) {
            statusCode = (Integer) statusObj;
        } else if (statusObj instanceof String) {
            try { statusCode = Integer.valueOf((String) statusObj); } catch (NumberFormatException ignored) {}
        }

        if (statusCode != null) {
            if (statusCode == 404) {
                return "forward:/error/404.html";
            }
            // you can add more mappings for other status codes if needed
        }
        return "forward:/error/500.html";
    }

}
