package com.exp2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


/**
 * Entidad que representa un laboratorio en el sistema.
 * Incluye información relevante como nombre, dirección, teléfono, correo
 * electrónico, sitio web y especialidad.
 * Aplica restricciones de validación para asegurar la integridad de los datos.
 */
@Entity
@Table(name = "tb_laboratories")
@Data
public class Laboratory {

    /**
     * Identificador único del laboratorio.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_laboratory")
    private long id;

    /**
     * Nombre del laboratorio.
     * Debe ser único, no nulo y tener al menos 4 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 4, message = "El nombre debe tener al menos 4 caracteres")
    @Column(name = "nameLaboratory", unique = true)
    private String name;

    /**
     * Dirección física del laboratorio.
     * No puede estar vacía y debe tener un máximo de 150 caracteres.
     */
    @Size(max = 150, message = "La dirección debe tener un máximo de 150 caracteres")
    @NotBlank(message = "La dirección es obligatoria")
    @Column(name = "addressLaboratory")
    private String address;

    /**
     * Teléfono de contacto del laboratorio.
     * Debe ser válido, contener entre 7 y 15 dígitos y no puede estar vacío.
     */
    @Pattern(regexp = "\\+?\\d{7,15}", message = "El teléfono debe ser válido y contener entre 7 y 15 dígitos")
    @NotBlank(message = "El teléfono es obligatorio")
    @Column(name = "phoneLaboratory")
    private String phone;

    /**
     * Correo electrónico del laboratorio.
     * Debe ser válido, no puede estar vacío y debe tener entre 5 y 100 caracteres.
     */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 100, min = 5, message = "El correo electrónico debe tener entre 5 y 100 caracteres")
    @Column(name = "emailLaboratory")
    private String email;

    /**
     * Sitio web del laboratorio (opcional).
     * Máximo 100 caracteres.
     */
    @Size(max = 100, message = "El sitio web debe tener un máximo de 100 caracteres")
    @Column(name = "websiteLaboratory")
    private String website;

    /**
     * Especialidad principal del laboratorio.
     * No puede estar vacía y debe tener entre 2 y 50 caracteres.
     */
    @Size(max = 50, min = 2, message = "La especialidad debe tener entre 2 y 50 caracteres")
    @NotBlank(message = "La especialidad es obligatoria")
    @Column(name = "specialtyLaboratory")
    private String specialty;


}
