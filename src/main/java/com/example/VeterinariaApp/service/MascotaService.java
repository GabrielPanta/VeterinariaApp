package com.example.VeterinariaApp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.VeterinariaApp.Dto.MascotaDTO;
import com.example.VeterinariaApp.entities.Mascota;
import com.example.VeterinariaApp.entities.Usuario;
import com.example.VeterinariaApp.repository.MascotaRepository;
import com.example.VeterinariaApp.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Mascota registrarMascota(MascotaDTO dto, String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setRaza(dto.getRaza());
        mascota.setEdad(dto.getEdad());
        mascota.setTipo(dto.getTipo());
        mascota.setUsuario(usuario);

        return mascotaRepository.save(mascota);
    }

    public List<MascotaDTO> obtenerMascotasPorCliente(String username) {
        Usuario usuario = usuarioRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Mascota> mascotas = mascotaRepository.findByUsuarioId(usuario.getId());
        return mascotas.stream()
            .map(m -> new MascotaDTO(m.getId(), m.getNombre(), m.getTipo(), m.getRaza(), m.getEdad()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void actualizarMascota(Long id, MascotaDTO dto, String emailUsuario) {
    Mascota mascota = mascotaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

    // Verifica que la mascota pertenezca al usuario autenticado
    if (!mascota.getUsuario().getEmail().equals(emailUsuario)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para actualizar esta mascota");
    }

    // Actualiza los campos
    mascota.setNombre(dto.getNombre());
    mascota.setRaza(dto.getRaza());
    mascota.setEdad(dto.getEdad());
    mascota.setTipo(dto.getTipo());

    mascotaRepository.save(mascota);
}

@Transactional
public void eliminarMascota(Long id, String emailUsuario) {
    Mascota mascota = mascotaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

    // Verifica que la mascota pertenezca al usuario autenticado
    if (!mascota.getUsuario().getEmail().equals(emailUsuario)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar esta mascota");
    }

    mascotaRepository.delete(mascota);
}

}
