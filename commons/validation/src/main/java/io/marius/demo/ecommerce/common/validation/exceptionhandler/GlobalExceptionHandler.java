package io.marius.demo.ecommerce.common.validation.exceptionhandler;

import io.marius.demo.ecommerce.common.validation.view.ErrorView;
import io.marius.demo.ecommerce.common.validation.view.builder.ErrorViewBuilder;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorView<String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    logger.error(
        "MethodArgumentNotValidException exception encountered: {}",
        ex.getBindingResult().getAllErrors(),
        ex);

    List<String> errors =
        ex.getBindingResult().getAllErrors().stream()
            .map(
                (error) -> {
                  String fieldName = ((FieldError) error).getField();
                  String errorMessage = error.getDefaultMessage();
                  return String.format("%s : %s", fieldName, errorMessage);
                })
            .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new ErrorViewBuilder<String>()
                .setName("MethodArgumentNotValidException violation exception")
                .setDetails(errors)
                .createErrorView());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorView<String>> handleInternalServerError(
      HttpServletRequest request, Exception ex) {
    logger.error("handleInternalServerError {}\n", request.getRequestURI(), ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new ErrorViewBuilder<String>()
                .setName("Internal server error")
                .setDetails(List.of(ex.getMessage()))
                .createErrorView());
  }
}
