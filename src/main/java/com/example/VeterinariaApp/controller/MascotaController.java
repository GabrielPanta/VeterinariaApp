package com.example.VeterinariaApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.VeterinariaApp.Dto.MascotaDTO;
import com.example.VeterinariaApp.service.MascotaService;

import jakarta.validation.Valid;

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
}
