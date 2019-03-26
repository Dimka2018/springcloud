package com.epam.springcloud.controller.advice;

import java.nio.file.FileAlreadyExistsException;
import java.util.Locale;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.epam.springcloud.exception.UserNotFoundException;
import com.epam.springcloud.resource.MessageBundle;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({ValidationException.class})
    public Exception handleValidationException(ValidationException exception,
            Locale locale) {
        log.debug("validation exception occurs", exception);
        String message = messageSource.getMessage(exception.getMessage(), null,
                null, locale);
        return new Exception(message);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({UserNotFoundException.class})
    public Exception handleUserExistsException(UserNotFoundException exception,
            Locale locale) {
        log.debug("user not found exception", exception);
        String message = messageSource.getMessage(
                MessageBundle.USER_EXISTS_MESSAGE, null, null, locale);
        return new Exception(message);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({FileAlreadyExistsException.class})
    public Exception handleFileExistsException(
            FileAlreadyExistsException exception, Locale locale) {
        log.debug("file already exists exception occurs", exception);
        String message = messageSource.getMessage(
                MessageBundle.FILE_EXISTS_MESSAGE, null, null, locale);
        return new Exception(message);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UserNotFoundException.class})
    public Exception handleUserNotFoundException(
            UserNotFoundException exception, Locale locale) {
        log.debug("user not found", exception);
        String message = messageSource.getMessage(
                MessageBundle.USER_EXISTS_MESSAGE, null, null, locale);
        return new Exception(message);
    }
    
    @ResponseStatus()
    @ExceptionHandler({Exception.class})
    public Exception handleServerProblemException(Exception exception,
            Locale locale) {
        log.error("Exception occurs", exception);
        String message = messageSource.getMessage(
                MessageBundle.SERVER_PROBLEM_MESSAGE, null, null, locale);
        return new Exception(message);
    }

}
