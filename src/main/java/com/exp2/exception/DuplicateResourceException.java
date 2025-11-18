package com.exp2.exception;

/**
 * Excepción personalizada para indicar que se intenta crear un recurso
 * duplicado.
 * Se utiliza cuando ya existe un laboratorio con un valor único (por ejemplo,
 * nombre o correo).
 * Extiende de {@link RuntimeException} para permitir su uso como excepción no
 * comprobada.
 */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Crea una nueva instancia de DuplicateResourceException con un mensaje
     * personalizado.
     *
     * @param message Mensaje descriptivo del error.
     */
    public DuplicateResourceException(String message) {
        super(message);
    }

}
