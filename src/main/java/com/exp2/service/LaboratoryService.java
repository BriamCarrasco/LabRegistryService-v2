package com.exp2.service;

import com.exp2.model.Laboratory;
import java.util.List;
import java.util.Optional;


/**
 * Interfaz que define los métodos de servicio para la gestión de laboratorios.
 * Proporciona operaciones CRUD y búsquedas personalizadas sobre la entidad
 * Laboratory.
 */
public interface LaboratoryService {

    /**
     * Guarda un nuevo laboratorio en la base de datos.
     *
     * @param laboratory Laboratorio a guardar.
     * @return Laboratorio guardado.
     */
    Laboratory saveLaboratory(Laboratory laboratory);

    /**
     * Obtiene la lista de todos los laboratorios.
     *
     * @return Lista de laboratorios.
     */
    List<Laboratory> getAllLaboratories();

    /**
     * Busca un laboratorio por su ID.
     *
     * @param id ID del laboratorio.
     * @return Un Optional con el laboratorio encontrado o vacío si no existe.
     */
    Optional<Laboratory> getLaboratoryById(Long id);

    /**
     * Actualiza los datos de un laboratorio existente.
     *
     * @param id         ID del laboratorio a actualizar.
     * @param laboratory Datos nuevos del laboratorio.
     * @return Laboratorio actualizado.
     */
    Laboratory updateLaboratory(Long id, Laboratory laboratory);

    /**
     * Elimina un laboratorio por su ID.
     *
     * @param id ID del laboratorio a eliminar.
     */
    void deleteLaboratory(Long id);

    /**
     * Busca laboratorios por especialidad.
     *
     * @param specialty Especialidad a buscar.
     * @return Lista de laboratorios con la especialidad indicada.
     */
    List<Laboratory> findBySpecialty(String specialty);

    /**
     * Busca laboratorios por nombre (búsqueda parcial, sin distinguir
     * mayúsculas/minúsculas).
     *
     * @param name Nombre o parte del nombre a buscar.
     * @return Lista de laboratorios que coinciden con el nombre.
     */
    List<Laboratory> findByName(String name);
}