package com.example.VeterinariaApp.controller;

import com.example.VeterinariaApp.Dto.HistorialClinicoDTO;
import com.example.VeterinariaApp.entities.Cliente;
import com.example.VeterinariaApp.repository.ClienteRepository;
import com.example.VeterinariaApp.service.HistorialClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/historial")
public class HistorialClinicoController {

    @Autowired
    private HistorialClinicoService historialService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/{idMascota}")
    public ResponseEntity<?> getHistorialMascota(
            @PathVariable Long idMascota,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaAtencion,desc") String sort,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("No autorizado");
        }

        Cliente cliente = clienteRepository.findByEmail(userDetails.getUsername())
                .orElse(null);

        if (cliente == null) {
            return ResponseEntity.status(401).body("Cliente no encontrado");
        }

        // Procesar sort
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        try {
            Page<HistorialClinicoDTO> historial = historialService.obtenerHistorial(cliente, idMascota, pageable);
            return ResponseEntity.ok(historial);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}

