package mx.edu.utez.AplicacionDePrincipios.services;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.AplicacionDePrincipios.config.ApiResponse;
import mx.edu.utez.AplicacionDePrincipios.models.CedeEntity;
import mx.edu.utez.AplicacionDePrincipios.models.CedeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
public class CedeService {

    public CedeService(CedeRepository repository) {
        this.repository = repository;
    }

    private final CedeRepository repository;

    public ResponseEntity<ApiResponse> findAll() {
        return ResponseEntity.ok(new ApiResponse(repository.findAll(), "Consulta exitosa", true));
    }

    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<CedeEntity> found = repository.findById(id);
        if (found.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cede no encontrada", false));
        return ResponseEntity.ok(new ApiResponse(found.get(), "Cede encontrada", true));
    }

    public ResponseEntity<ApiResponse> save(CedeEntity cede) {
        if (cede.getEstado() == null || cede.getMunicipio() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Estado y municipio son obligatorios", false));

        CedeEntity saved = repository.save(cede);

        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String random = String.format("%04d", new Random().nextInt(10_000));
        saved.setClave("C" + saved.getId() + "-" + fecha + "-" + random);

        saved = repository.save(saved);

        return ResponseEntity.ok(new ApiResponse(saved, "Cede registrada correctamente", true));
    }


    public ResponseEntity<ApiResponse> update(Long id, CedeEntity cede) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cede no encontrada", false));
        cede.setId(id);
        return ResponseEntity.ok(new ApiResponse(repository.save(cede), "Cede actualizada correctamente", true));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cede no encontrada", false));
        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, "Cede eliminada correctamente", true));
    }
}

