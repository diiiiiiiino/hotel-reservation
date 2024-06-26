package com.dino.hotel.api.common.http;

import com.dino.hotel.api.common.exception.ApplicationException;
import com.dino.hotel.api.common.exception.CustomIllegalArgumentException;
import com.dino.hotel.api.common.exception.CustomNullPointerException;
import com.dino.hotel.api.common.exception.ValidationErrorException;
import com.dino.hotel.api.common.http.response.ErrorCode;
import com.dino.hotel.api.common.http.response.ErrorResponse;
import com.dino.hotel.api.common.http.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * {@code RestController}의 예외를 처리하는 핸들러
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> totalHandle(Exception exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(ErrorCode.SERVER_ERROR.getCode())
                .build();

        return ResponseEntity.status(ErrorCode.SERVER_ERROR.getStatus())
                .body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> totalHandle(ApplicationException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder();

        if(exception instanceof ValidationErrorException){
            ValidationErrorException validationErrorException = (ValidationErrorException) exception;
            builder.errors(validationErrorException.getErrors());
        }

        Response response = builder.message(exception.getMessage())
                .errorCode(errorCode.getCode())
                .build();

        return ResponseEntity.status(errorCode.getStatus())
                .body(response);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> totalHandle(IllegalArgumentException exception) {
        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder()
                .message(exception.getMessage());

        if(exception instanceof CustomIllegalArgumentException){
            CustomIllegalArgumentException customEx = (CustomIllegalArgumentException)exception;
            ErrorCode errorCode = customEx.getErrorCode();

            builder.errorCode(errorCode.getCode())
                    .errorDetail(customEx.getDetail());

            return ResponseEntity.status(errorCode.getStatus())
                    .body(builder.build());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(builder.build());
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<?> totalHandle(NullPointerException exception) {
        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder()
                .message(exception.getMessage());

        if(exception instanceof CustomNullPointerException){
            CustomNullPointerException customEx = (CustomNullPointerException)exception;
            ErrorCode errorCode = customEx.getErrorCode();

            builder.errorCode(errorCode.getCode())
                    .errorDetail(customEx.getDetail());

            return ResponseEntity.status(errorCode.getStatus())
                    .body(builder.build());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(builder.build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> totalHandle(MethodArgumentNotValidException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(ErrorCode.INVALID_REQUEST.getCode())
                .build();

        return ResponseEntity.status(ErrorCode.INVALID_REQUEST.getStatus())
                .body(response);
    }
}
