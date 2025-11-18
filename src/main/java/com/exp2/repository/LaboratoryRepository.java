package com.exp2.repository;

import com.exp2.model.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad {@link Laboratory}.
 * Proporciona métodos CRUD y consultas personalizadas para laboratorios.
 */
@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {

    /**
     * Busca laboratorios por especialidad exacta.
     *
     * @param specialty Especialidad a buscar.
     * @return Lista de laboratorios con la especialidad indicada.
     */
    List<Laboratory> findBySpecialty(String specialty);

    /**
     * Busca laboratorios cuyo nombre contenga la cadena dada (ignorando
     * mayúsculas/minúsculas).
     *
     * @param name Nombre o parte del nombre a buscar.
     * @return Lista de laboratorios que coinciden con el nombre.
     */
    List<Laboratory> findByNameContainingIgnoreCase(String name);

    /**
     * Busca un laboratorio por su ID.
     *
     * @param id ID del laboratorio.
     * @return Un Optional con el laboratorio encontrado o vacío si no existe.
     */
    Optional<Laboratory> findById(Long id);
}
