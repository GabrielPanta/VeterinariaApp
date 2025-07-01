package com.example.VeterinariaApp.controller;

import com.example.VeterinariaApp.Dto.MascotaDTO;
import com.example.VeterinariaApp.service.MascotaService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    @Autowired
    private MascotaService mascotaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar(@Valid @RequestBody MascotaDTO dto, Authentication auth) {
        String email = auth.getName(); // Email extraído del token JWT
        mascotaService.registrarMascota(dto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mascota registrada con éxito");
    }

    // 📤 Obtener mascotas del cliente autenticado
    @GetMapping("/mis")
    @Operation(
        summary = "Listar mascotas del cliente autenticado",
        description = "Devuelve una lista de mascotas asociadas al usuario autenticado."
    )
    public ResponseEntity<List<MascotaDTO>> listarMisMascotas(Authentication authentication) {
        String username = authentication.getName();
        List<MascotaDTO> mascotas = mascotaService.obtenerMascotasPorCliente(username);
        return ResponseEntity.ok(mascotas);
    }
}
