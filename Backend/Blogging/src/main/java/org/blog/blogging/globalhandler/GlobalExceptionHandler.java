package org.blog.blogging.globalhandler;

import org.blog.blogging.exceptions.CustomerNotFoundException;
import org.blog.blogging.exceptions.EmailAlreadyExistsException;
import org.blog.blogging.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException existsException)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .ErrorDescription(BusinessErrorCodes.EMAIL_EXISTS.getDescription())
                        .ErrorCode(BusinessErrorCodes.EMAIL_EXISTS.getCode())
                        .error(existsException.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException)
    {
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .ErrorDescription(BusinessErrorCodes.CUSTOMER_NOT_FOUND.getDescription())
                        .ErrorCode(BusinessErrorCodes.CUSTOMER_NOT_FOUND.getCode())
                        .error(customerNotFoundException.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleCustomerNotFoundException(ResourceNotFoundException resourceNotFoundException)
    {
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .ErrorDescription(BusinessErrorCodes.RESOURCE_NOT_FOUND.getDescription())
                        .ErrorCode(BusinessErrorCodes.RESOURCE_NOT_FOUND.getCode())
                        .error(resourceNotFoundException.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleMethodArgsNotValidException(MethodArgumentNotValidException validException)
    {
        Map<String, String> validationErrors = new HashMap<>();
        validException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .ErrorDescription("Validation Failed")
                                .errors(validationErrors)
                                .build()
                );
    }
}
