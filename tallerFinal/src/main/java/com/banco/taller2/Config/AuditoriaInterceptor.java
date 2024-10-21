package com.banco.taller2.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditoriaInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
     ){
         System.out.println("Solicitud entrante" + request.getMethod());
         return true;
     }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception exception
    ) throws Exception {
        if (exception == null) {
            System.out.println("Operación completada correctamente para:");
            System.out.println("Método HTTP: " + request.getMethod());
            System.out.println("URI: " + request.getRequestURI());
        } else {
            System.out.println("Ocurrió un error durante la operación:");
            System.out.println("Método HTTP: " + request.getMethod());
            System.out.println("URI: " + request.getRequestURI());
            System.out.println("Error: " + exception.getMessage());
        }
    }
}
