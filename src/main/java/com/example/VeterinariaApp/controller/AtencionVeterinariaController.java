package com.example.VeterinariaApp.controller;

import com.example.VeterinariaApp.Dto.AtencionVeterinariaDTO;
import com.example.VeterinariaApp.entities.AtencionVeterinaria;
import com.example.VeterinariaApp.service.AtencionVeterinariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/atenciones")
public class AtencionVeterinariaController {

    @Autowired
    private AtencionVeterinariaService atencionService;

    @PostMapping
    public ResponseEntity<?> crearAtencion(
            @Valid @RequestBody AtencionVeterinariaDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("No autorizado");
        }

        try {
            AtencionVeterinaria atencion = atencionService.registrarAtencion(dto, userDetails.getUsername());
            return ResponseEntity.ok(atencion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al registrar atención");
        }
    }
}
