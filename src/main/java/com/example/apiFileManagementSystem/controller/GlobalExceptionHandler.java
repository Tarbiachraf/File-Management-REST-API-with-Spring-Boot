package com.example.apiFileManagementSystem.controller;

import com.example.apiFileManagementSystem.exception.*;
import com.example.apiFileManagementSystem.models.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur : " + ex.getMessage());
    }

    @ExceptionHandler({GroupNotFoundException.class, FileNotFoundException.class})
    public ResponseEntity<String> handleCustomExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(RessourceNotFoundException ex) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                ex.getErrorCode(), ex.getResourceType(), ex.getResourceId(), ex.getErrorMessage()
        );
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<CustomErrorResponse> handleDuplicateResourceException(DuplicateResourceException duplicateResourceException){
        CustomErrorResponse customErrorResponse =
                new CustomErrorResponse(duplicateResourceException.getErrorCode(),duplicateResourceException.getResourceType(),duplicateResourceException.getErrorMessage());
        return new ResponseEntity<>(customErrorResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorResponse> handleUnauthorizedException(UnauthorizedException unauthorizedException){
        CustomErrorResponse
                customErrorResponse = new CustomErrorResponse(unauthorizedException.getErrorCode(),
                        unauthorizedException.getResourceType(),
                        unauthorizedException.getResourceId(),
                        unauthorizedException.getErrorMessage());
        return new ResponseEntity<>(customErrorResponse,HttpStatus.UNAUTHORIZED);

    }
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<String> handleFileStorageException(UnauthorizedException unauthorizedException) {
        return new ResponseEntity<>("vous n'avez envoyé aucun fichier",HttpStatus.BAD_REQUEST);
    }

    }

