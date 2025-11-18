package com.exp2.service;


import com.exp2.exception.DuplicateResourceException;
import com.exp2.model.Laboratory;
import com.exp2.repository.LaboratoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz {@link LaboratoryService} para la gestión de
 * laboratorios.
 * Proporciona operaciones CRUD y búsquedas personalizadas sobre la entidad
 * Laboratory.
 * Maneja la excepción de recurso duplicado al intentar guardar laboratorios con
 * nombre repetido.
 */
@Service
public class LaboratoryServiceImpl implements LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryServiceImpl(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    /**
     * Guarda un nuevo laboratorio en la base de datos.
     * Lanza una excepción si el laboratorio ya existe.
     *
     * @param laboratory Laboratorio a guardar.
     * @return Laboratorio guardado.
     * @throws DuplicateResourceException si ya existe un laboratorio con el mismo
     *                                    nombre.
     */
    @Override
    public Laboratory saveLaboratory(Laboratory laboratory) {
        try {
            return laboratoryRepository.save(laboratory);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException("Ya existe un laboratorio con el nombre: " + laboratory.getName());
        }
    }

    /**
     * Obtiene la lista de todos los laboratorios.
     *
     * @return Lista de laboratorios.
     */
    @Override
    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    /**
     * Busca un laboratorio por su ID.
     *
     * @param id ID del laboratorio.
     * @return Un Optional con el laboratorio encontrado o vacío si no existe.
     */
    @Override
    public Optional<Laboratory> getLaboratoryById(Long id) {
        return laboratoryRepository.findById(id);
    }

    /**
     * Actualiza los datos de un laboratorio existente.
     *
     * @param id         ID del laboratorio a actualizar.
     * @param laboratory Datos nuevos del laboratorio.
     * @return Laboratorio actualizado.
     * @throws RuntimeException si no se encuentra el laboratorio.
     */
    @Override
    public Laboratory updateLaboratory(Long id, Laboratory laboratory) {
        return laboratoryRepository.findById(id)
                .map(existingLab -> {
                    existingLab.setName(laboratory.getName());
                    existingLab.setAddress(laboratory.getAddress());
                    existingLab.setPhone(laboratory.getPhone());
                    existingLab.setEmail(laboratory.getEmail());
                    existingLab.setWebsite(laboratory.getWebsite());
                    existingLab.setSpecialty(laboratory.getSpecialty());
                    return laboratoryRepository.save(existingLab);
                })
                .orElseThrow(() -> new RuntimeException("Laboratorio no encontrado con ID: " + id));
    }

    /**
     * Elimina un laboratorio por su ID.
     *
     * @param id ID del laboratorio a eliminar.
     */
    @Override
    public void deleteLaboratory(Long id) {
        laboratoryRepository.deleteById(id);
    }

    /**
     * Busca laboratorios por especialidad.
     *
     * @param specialty Especialidad a buscar.
     * @return Lista de laboratorios con la especialidad indicada.
     */
    @Override
    public List<Laboratory> findBySpecialty(String specialty) {
        return laboratoryRepository.findBySpecialty(specialty);
    }

    /**
     * Busca laboratorios por nombre (búsqueda parcial, sin distinguir
     * mayúsculas/minúsculas).
     *
     * @param name Nombre o parte del nombre a buscar.
     * @return Lista de laboratorios que coinciden con el nombre.
     */
    @Override
    public List<Laboratory> findByName(String name) {
        return laboratoryRepository.findByNameContainingIgnoreCase(name);
    }
}
