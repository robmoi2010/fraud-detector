package com.goglotek.frauddetector.dataextractionservice.datastoreservice.exception.handler;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMpesaFileNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ProviderTransactionsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMpesaFetchedTransNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProviderLocalTransactionsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMpesaReconTransactionsNotFoundException(Exception ex,
                                                                                       WebRequest request) {
        return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ProcessingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMpesaReconciliations(Exception ex, WebRequest request) {
        return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(generateErrorResponse(ex, request, 500, "CODE_2"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(InvalidEncryptionKeyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEncryptionKeyEception(Exception ex, WebRequest request) {
        return new ResponseEntity<>(generateErrorResponse(ex, request, 500, "CODE_2"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse generateErrorResponse(Exception ex, WebRequest request, int status, String code) {
        String message = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        return errorResponse;
    }
}
