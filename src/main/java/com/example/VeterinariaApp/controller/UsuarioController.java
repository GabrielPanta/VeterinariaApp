package com.example.VeterinariaApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.VeterinariaApp.Dto.UsuarioDTO;
import com.example.VeterinariaApp.entities.Usuario;
import com.example.VeterinariaApp.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioDTO dto) {
        Usuario nuevoUsuario = usuarioService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<Usuario>> listarUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String nombre) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuarios = usuarioService.listarUsuarios(nombre, pageable);
        return ResponseEntity.ok(usuarios);
    }
    
}
    