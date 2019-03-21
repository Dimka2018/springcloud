package com.epam.springcloud.controller.advice;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.epam.springcloud.resource.MessageBundle;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class ExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    public void handleValidationException(ValidationException exception,
            HttpServletResponse response, Locale locale) {
        log.debug("validation exception occurs", exception);
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    messageSource.getMessage(exception.getMessage(), null,
                            locale));
        } catch (IOException e) {
            log.fatal("Error sending exception", e);
        }
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            Exception.class })
    public void handleServerProblemException(Exception exception,
            HttpServletResponse response, Locale locale) {
        log.error("Exception occurs", exception);
        try {
            String errorMessage = messageSource.getMessage(
                    MessageBundle.SERVER_PROBLEM_MESSAGE, null, locale);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    errorMessage);
        } catch (IOException e) {
            log.fatal("Error sending exception", e);
        }
    }

}
