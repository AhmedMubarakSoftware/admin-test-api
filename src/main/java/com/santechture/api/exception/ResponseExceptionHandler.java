package com.santechture.api.exception;

import com.santechture.api.dto.GeneralResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResponseExceptionHandler {
    private final MessageSource messageSource;
    private final int BUSINESS_EXCEPTION_CODE = 1001;
    private final int VALIDATION_EXCEPTION_CODE = 1002;
    private final int GENERAL_EXCEPTION_CODE = 1003;

    public ResponseExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({BusinessExceptions.class})
    protected ResponseEntity<GeneralResponse> handleBadRequestException(BusinessExceptions exception, Locale locale) {
        GeneralResponse response = new GeneralResponse();
        String messageName = exception.message;
        String localMessage = messageSource.getMessage(messageName, null, locale);
        if (localMessage == null) {
            localMessage = exception.message;
        }
        exception.message = localMessage;
        String trace ="";
        for ( StackTraceElement stackTraceElement:exception.getStackTrace())
        {
            if(stackTraceElement!=null ) trace+= "  "+stackTraceElement.toString();
        }
        response.response(BUSINESS_EXCEPTION_CODE, localMessage, trace);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse> handleValidationErrors(MethodArgumentNotValidException ex, Locale locale) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        GeneralResponse response = new GeneralResponse();
        response.response(BUSINESS_EXCEPTION_CODE, getErrorsMap(errors, locale), ex.getStackTrace());

        return ResponseEntity.badRequest().body(response);
    }

    private String getErrorsMap(List<String> errors, Locale locale) {
        return errors.stream().map(x -> messageSource.getMessage(x, null, locale)).reduce("  ", String::concat);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleBadRequestException(Exception exception, Locale locale) {
        String messageName = exception.getMessage();

        String message = messageSource.getMessage(messageName, null, locale);

        GeneralResponse response = new GeneralResponse();
        String trace = Arrays.stream(exception.getStackTrace()).map(x -> messageSource.getMessage(x.toString(), null, locale)).reduce("   ", String::concat);
        response.response(BUSINESS_EXCEPTION_CODE, message, trace);

        return ResponseEntity.badRequest().body(response);
    }
}