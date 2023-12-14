package com.example.jobmatch.exception;

import com.example.jobmatch.respon.Respon;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomForbiddenEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        if (request.getAttribute("errorJWT") != null) {
            mapper.writeValue(response.getWriter(), new Respon<>(request.getAttribute("errorJWT").toString()));
        }
        mapper.writeValue(response.getWriter(), new Respon<>(authException.getMessage()));
    }
}