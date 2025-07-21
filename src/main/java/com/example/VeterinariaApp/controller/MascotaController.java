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

    
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody MascotaDTO dto, Authentication auth) {
        String email = auth.getName(); // Email extraído del token JWT
        mascotaService.registrarMascota(dto, email);
        System.out.println("Autenticado: " + auth.getName());
        System.out.println("Roles: " + auth.getAuthorities());
        return ResponseEntity.status(HttpStatus.CREATED).body("Mascota registrada con éxito");
        
    }

    // 📤 Obtener mascotas del cliente autenticadoooo
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

    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMascota(@PathVariable Long id, @Valid @RequestBody MascotaDTO dto,
            Authentication auth) {
        String email = auth.getName();
        mascotaService.actualizarMascota(id, dto, email);
        return ResponseEntity.ok("Mascota actualizada con éxito");
    }

    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMascota(@PathVariable Long id, Authentication auth) {
        String email = auth.getName();
        mascotaService.eliminarMascota(id, email);
        return ResponseEntity.ok("Mascota eliminada correctamente");
    }


}
