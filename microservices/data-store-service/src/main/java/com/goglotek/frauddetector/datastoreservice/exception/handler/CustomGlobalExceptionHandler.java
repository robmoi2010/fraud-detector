/*
 *
 *  * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  *
 *  * This file is part of the Fraud Detector System.
 *  *
 *  * The Fraud Detector System is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * The Fraud Detector System is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.goglotek.frauddetector.datastoreservice.exception.handler;

import com.goglotek.frauddetector.datastoreservice.exception.ErrorResponse;
import com.goglotek.frauddetector.datastoreservice.exception.FileNotFoundException;
import com.goglotek.frauddetector.datastoreservice.exception.GeneralException;
import com.goglotek.frauddetector.datastoreservice.exception.InvalidEncryptionKeyException;
import com.goglotek.frauddetector.datastoreservice.exception.ProcessingNotFoundException;
import com.goglotek.frauddetector.datastoreservice.exception.ProviderLocalTransactionsNotFoundException;
import com.goglotek.frauddetector.datastoreservice.exception.ProviderTransactionsNotFoundException;
import com.goglotek.frauddetector.datastoreservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(FileNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleMpesaFileNotFoundException(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"),
        HttpStatus.NOT_FOUND);

  }

  @ExceptionHandler(ProviderTransactionsNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleMpesaFetchedTransNotFoundException(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ProviderLocalTransactionsNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleMpesaReconTransactionsNotFoundException(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"),
        HttpStatus.NOT_FOUND);

  }

  @ExceptionHandler(ProcessingNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleMpesaReconciliations(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"),
        HttpStatus.NOT_FOUND);

  }

  @ExceptionHandler(GeneralException.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
    return new ResponseEntity<>(generateErrorResponse(ex, request, 500, "CODE_2"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(generateErrorResponse(ex, request, 404, "CODE_1"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InvalidEncryptionKeyException.class)
  public ResponseEntity<ErrorResponse> handleInvalidEncryptionKeyEception(Exception ex,
      WebRequest request) {
    return new ResponseEntity<>(generateErrorResponse(ex, request, 500, "CODE_2"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ErrorResponse generateErrorResponse(Exception ex, WebRequest request, int status,
      String code) {
    String message = ex.getMessage();
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(code);
    errorResponse.setStatus(status);
    errorResponse.setMessage(message);
    return errorResponse;
  }
}
