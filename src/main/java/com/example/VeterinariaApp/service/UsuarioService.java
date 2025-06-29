package com.example.VeterinariaApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.VeterinariaApp.Dto.UsuarioDTO;
import com.example.VeterinariaApp.entities.Rol;
import com.example.VeterinariaApp.entities.Usuario;
import com.example.VeterinariaApp.repository.UsuarioRepository;

@Service
public class UsuarioService {

     @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrar(UsuarioDTO dto) {

        // Validar duplicidad de email
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(Rol.ADMIN);

        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> listarUsuarios(String nombre, Pageable pageable) {
        if (nombre != null && !nombre.isBlank()) {
            return usuarioRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        }
        return usuarioRepository.findAll(pageable);
    }


}
