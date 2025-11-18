package com.exp2.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;

/**
 * Manejador global de excepciones para la API.
 * Captura y personaliza las respuestas de error para diferentes tipos de
 * excepciones,
 * proporcionando mensajes claros y estructurados para el cliente.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // Constantes para evitar duplicación de literales
    private static final String STATUS_KEY = "status";
    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String ERROR_KEY = "error";
    private static final String PATH_KEY = "path";
    private static final String MESSAGE_KEY = "message";
    private static final String ERRORES_KEY = "errores";

    /**
     * Maneja errores de validación de argumentos en los controladores.
     *
     * @param ex Excepción de validación.
     * @return Respuesta con detalles de los errores de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
        respuesta.put(TIMESTAMP_KEY, LocalDateTime.now());
        respuesta.put(ERRORES_KEY, errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    /**
     * Maneja excepciones cuando un recurso no es encontrado.
     *
     * @param ex      Excepción de recurso no encontrado.
     * @param request Información de la petición.
     * @return Respuesta con mensaje de recurso no encontrado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex,
            WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
        error.put(TIMESTAMP_KEY, LocalDateTime.now());
        error.put(ERROR_KEY, ex.getMessage());
        error.put(PATH_KEY, request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja rutas inexistentes o no mapeadas en la API.
     *
     * @param ex      Excepción de respuesta de error.
     * @param request Información de la petición.
     * @return Respuesta con mensaje de ruta no encontrada.
     */
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<Map<String, Object>> handleErrorResponseException(ErrorResponseException ex,
            WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put(STATUS_KEY, ex.getStatusCode().value());
        error.put(TIMESTAMP_KEY, LocalDateTime.now());
        error.put(ERROR_KEY, "La URL solicitada no existe o no está disponible.");
        error.put(PATH_KEY, request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    /**
     * Maneja argumentos inválidos en la lógica de negocio.
     *
     * @param ex      Excepción de argumento ilegal.
     * @param request Información de la petición.
     * @return Respuesta con mensaje de argumento inválido.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
        error.put(TIMESTAMP_KEY, LocalDateTime.now());
        error.put(ERROR_KEY, ex.getMessage());
        error.put(PATH_KEY, request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja cualquier otra excepción no controlada.
     *
     * @param ex      Excepción general.
     * @param request Información de la petición.
     * @return Respuesta con mensaje de error interno del servidor.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            // Reemplazar RuntimeException genérica con una específica de Spring
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en documentación Swagger", ex);
        }
        log.error("Error interno del servidor: ", ex);
        Map<String, Object> error = new HashMap<>();
        error.put(STATUS_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put(TIMESTAMP_KEY, LocalDateTime.now());
        error.put(ERROR_KEY, "Error interno del servidor");
        error.put(MESSAGE_KEY, "Ha ocurrido un problema inesperado. Por favor, intente más tarde.");
        error.put(PATH_KEY, path);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Maneja excepciones cuando se intenta crear un recurso duplicado.
     *
     * @param ex      Excepción de recurso duplicado.
     * @param request Información de la petición.
     * @return Respuesta con mensaje de recurso duplicado.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateResource(DuplicateResourceException ex,
            WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put(STATUS_KEY, HttpStatus.CONFLICT.value());
        error.put(TIMESTAMP_KEY, LocalDateTime.now());
        error.put(ERROR_KEY, ex.getMessage());
        error.put(PATH_KEY, request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}