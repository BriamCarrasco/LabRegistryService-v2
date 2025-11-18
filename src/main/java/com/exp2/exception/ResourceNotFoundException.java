package com.exp2.exception;

/**
 * Excepción personalizada para indicar que un recurso no fue encontrado.
 * Se utiliza principalmente en los servicios cuando no se encuentra una entidad
 * solicitada.
 * Extiende de {@link RuntimeException} para permitir su uso como excepción no
 * comprobada.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Crea una nueva instancia de ResourceNotFoundException con un mensaje
     * personalizado.
     *
     * @param message Mensaje descriptivo del error.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}