package org.example.gelahsystem.Advice;

import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolationException;
import org.example.gelahsystem.API.APIExecption;
import org.example.gelahsystem.API.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice

public class ControllerAdvice {
@ExceptionHandler(value = APIExecption.class)
    public ResponseEntity<?> APIException(APIExecption apiExecption) {
    String message = apiExecption.getMessage();
    return ResponseEntity.status(400).body(new ApiResponse(message));
}

@ExceptionHandler(value = MethodArgumentNotValidException.class)
public ResponseEntity<?> ValidException(MethodArgumentNotValidException e){
    String error = e.getFieldError().getDefaultMessage();
    return ResponseEntity.status(400).body(new ApiResponse(error));
}

@ExceptionHandler(value = HttpMessageNotReadableException.class)
public ResponseEntity<?> HttpMessageNotReadable(){
   return ResponseEntity.status(400).body(new ApiResponse("your input not readable"));
}

@ExceptionHandler(value = NoResourceFoundException.class)
public ResponseEntity<?> urlError(){
    return ResponseEntity.status(404).body(new ApiResponse("wrong Url"));
}

@ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
public ResponseEntity<?> SQLError(APIExecption apiExecption){
    return ResponseEntity.status(400).body(new ApiResponse(apiExecption.getMessage()));
}

@ExceptionHandler(value = DataIntegrityViolationException.class)
public ResponseEntity<?> DataIntegrity(){
    return ResponseEntity.status(400).body(new ApiResponse("make sure the entered data is correct"));
}


@ExceptionHandler(value = ConstraintViolationException.class)
public ResponseEntity<?> ConstraintViolationException(ConstraintViolationException e){
    String error = e.getConstraintViolations().iterator().next().getMessage();
    return ResponseEntity.status(400).body(new ApiResponse(error));
}

}
