package com.example.VeterinariaApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(Rol.CLIENTE);

        return usuarioRepository.save(usuario);
    }

}
