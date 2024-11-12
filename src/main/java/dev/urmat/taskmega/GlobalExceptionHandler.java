package dev.urmat.taskmega;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e,
                                         HttpServletRequest request) {

        ProblemDetail pd;
        switch (e) {
            case NotFoundException nfe -> {
                pd = ProblemDetail.forStatus(404);
                pd.setTitle("Not Found");
                pd.setDetail(nfe.getMessage());
            }
            case MethodArgumentNotValidException manve -> {
                pd = ProblemDetail.forStatus(400);
                pd.setTitle("Bad Request");
                pd.setDetail(manve.getMessage());
            }
            default -> {
                pd = ProblemDetail.forStatus(500);
                pd.setTitle("Internal Server Error");
                pd.setDetail(e.getMessage());
            }
        }

        pd.setInstance(URI.create(request.getRequestURI()));
        return pd;
    }
}