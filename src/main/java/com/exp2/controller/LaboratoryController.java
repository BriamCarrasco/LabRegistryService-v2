package com.exp2.controller;

import com.exp2.model.Laboratory;
import com.exp2.service.LaboratoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



/**
 * Controlador REST para la gestión de laboratorios.
 * Proporciona endpoints para operaciones CRUD y búsquedas personalizadas sobre
 * la entidad Laboratory.
 * Documentado con Swagger/OpenAPI para facilitar la exploración y prueba de la
 * API.
 */
@Tag(name = "Laboratories", description = "Operaciones CRUD para laboratorios")
@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {


    private final LaboratoryService laboratoryService;

    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    /**
     * Crea un nuevo laboratorio.
     *
     * @param laboratory Objeto Laboratory a crear.
     * @return El laboratorio creado.
     */
    @Operation(summary = "Crear un nuevo laboratorio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Laboratorio creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Laboratory> createLaboratory(@RequestBody Laboratory laboratory) {
        Laboratory savedLab = laboratoryService.saveLaboratory(laboratory);
        return ResponseEntity.ok(savedLab);
    }

    /**
     * Obtiene la lista de todos los laboratorios.
     *
     * @return Lista de laboratorios.
     */
    @Operation(summary = "Obtener todos los laboratorios")
    @ApiResponse(responseCode = "200", description = "Lista de laboratorios")
    @GetMapping
    public ResponseEntity<List<Laboratory>> getAllLaboratories() {
        return ResponseEntity.ok(laboratoryService.getAllLaboratories());
    }

    /**
     * Obtiene un laboratorio por su ID.
     *
     * @param id ID del laboratorio.
     * @return El laboratorio encontrado o 404 si no existe.
     */
    @Operation(summary = "Obtener laboratorio por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Laboratorio encontrado"),
            @ApiResponse(responseCode = "404", description = "Laboratorio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Laboratory> getLaboratoryById(@PathVariable Long id) {
        return laboratoryService.getLaboratoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza un laboratorio existente por su ID.
     *
     * @param id         ID del laboratorio a actualizar.
     * @param laboratory Datos nuevos del laboratorio.
     * @return El laboratorio actualizado o 404 si no existe.
     */
    @Operation(summary = "Actualizar laboratorio por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Laboratorio actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Laboratorio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Laboratory> updateLaboratory(@PathVariable Long id, @RequestBody Laboratory laboratory) {
        try {
            Laboratory updatedLab = laboratoryService.updateLaboratory(id, laboratory);
            return ResponseEntity.ok(updatedLab);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un laboratorio por su ID.
     *
     * @param id ID del laboratorio a eliminar.
     * @return Respuesta sin contenido si se elimina correctamente.
     */
    @Operation(summary = "Eliminar laboratorio por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Laboratorio eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Laboratorio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaboratory(@PathVariable Long id) {
        laboratoryService.deleteLaboratory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca laboratorios por especialidad.
     *
     * @param specialty Especialidad a buscar.
     * @return Lista de laboratorios con la especialidad indicada.
     */
    @Operation(summary = "Buscar laboratorios por especialidad")
    @ApiResponse(responseCode = "200", description = "Lista de laboratorios por especialidad")
    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<Laboratory>> getBySpecialty(@PathVariable String specialty) {
        return ResponseEntity.ok(laboratoryService.findBySpecialty(specialty));
    }

    /**
     * Busca laboratorios por nombre (búsqueda parcial, sin distinguir mayúsculas/minúsculas).
     *
     * @param name Nombre o parte del nombre a buscar.
     * @return Lista de laboratorios que coinciden con el nombre.
     */
    @Operation(summary = "Buscar laboratorios por nombre")
    @ApiResponse(responseCode = "200", description = "Lista de laboratorios por nombre")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Laboratory>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(laboratoryService.findByName(name));
    }
}
