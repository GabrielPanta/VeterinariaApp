package com.example.VeterinariaApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.VeterinariaApp.Dto.MascotaDTO;
import com.example.VeterinariaApp.entities.Mascota;
import com.example.VeterinariaApp.entities.Usuario;
import com.example.VeterinariaApp.repository.MascotaRepository;
import com.example.VeterinariaApp.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class MascotaServiceTest {
    @InjectMocks
    private MascotaService mascotaService;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void registrarMascota_deberiaGuardarMascotaCorrectamente() {
        // Arrange
        MascotaDTO dto = new MascotaDTO();
        dto.setNombre("Rocky");
        dto.setRaza("Labrador");
        dto.setEdad(3);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("cliente@mail.com");

        when(usuarioRepository.findByEmail("cliente@mail.com")).thenReturn(Optional.of(usuario));
        when(mascotaRepository.save(any(Mascota.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Mascota resultado = mascotaService.registrarMascota(dto, "cliente@mail.com");

        // Assert
        assertNotNull(resultado);
        assertEquals("Rocky", resultado.getNombre());
        assertEquals("Labrador", resultado.getRaza());
        assertEquals(3, resultado.getEdad());
        assertEquals(usuario, resultado.getCliente());
    }

    @Test
    void registrarMascota_usuarioNoExiste_deberiaLanzarExcepcion() {
        // Arrange
        MascotaDTO dto = new MascotaDTO();
        dto.setNombre("Toby");
        dto.setEdad(2);

        when(usuarioRepository.findByEmail("invalido@mail.com")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            mascotaService.registrarMascota(dto, "invalido@mail.com");
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
    }
}
