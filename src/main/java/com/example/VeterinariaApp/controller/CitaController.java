package com.example.VeterinariaApp.controller;

import com.example.VeterinariaApp.Dto.CitaDTO;
import com.example.VeterinariaApp.entities.Veterinario;
import com.example.VeterinariaApp.repository.VeterinarioRepository;
import com.example.VeterinariaApp.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @GetMapping("/veterinario")
    @PreAuthorize("hasRole('VETERINARIO')")
    public ResponseEntity<Page<CitaDTO>> getCitasVeterinario(
            @RequestParam(value = "fecha", required = false) String fechaStr,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fecha,asc") String sort,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        Veterinario veterinario = veterinarioRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (veterinario == null) {
            return ResponseEntity.status(401).build();
        }

        LocalDateTime fechaFiltro = null;
        if (fechaStr != null && !fechaStr.isBlank()) {
            try {
                fechaFiltro = LocalDateTime.parse(fechaStr + "T00:00:00");
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<CitaDTO> citas = citaService.obtenerCitasDeVeterinario(veterinario, fechaFiltro, pageable);

        return ResponseEntity.ok(citas);
    }
}
