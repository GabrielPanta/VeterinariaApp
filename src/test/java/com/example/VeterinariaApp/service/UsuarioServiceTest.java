package com.example.VeterinariaApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.example.VeterinariaApp.Dto.UsuarioDTO;
import com.example.VeterinariaApp.entities.Rol;
import com.example.VeterinariaApp.entities.Usuario;
import com.example.VeterinariaApp.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void registrar_DeberiaGuardarUsuarioCuandoEmailEsNuevo() {
        // Arrange
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("test@mail.com");
        dto.setNombre("Test");
        dto.setPassword("123456");

        when(usuarioRepo.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("hashed123");
        when(usuarioRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        // Act
        Usuario result = usuarioService.registrar(dto);

        // Assert
        assertEquals("test@mail.com", result.getEmail());
        assertEquals("Test", result.getNombre());
        assertEquals("hashed123", result.getPassword());
        assertEquals(Rol.ADMIN, result.getRol());
    }

    @Test
    void registrar_DeberiaLanzarExcepcionSiEmailYaExiste() {
        // Arrange
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("ya@existe.com");
        when(usuarioRepo.existsByEmail(dto.getEmail())).thenReturn(true);

        // Act + Assert
        assertThrows(ResponseStatusException.class, () -> usuarioService.registrar(dto));
    }
}
