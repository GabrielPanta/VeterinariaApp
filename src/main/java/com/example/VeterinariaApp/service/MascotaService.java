package com.example.VeterinariaApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.VeterinariaApp.Dto.MascotaDTO;
import com.example.VeterinariaApp.entities.Mascota;
import com.example.VeterinariaApp.entities.Usuario;
import com.example.VeterinariaApp.repository.MascotaRepository;
import com.example.VeterinariaApp.repository.UsuarioRepository;

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
        mascota.setUsuario(usuario);

        return mascotaRepository.save(mascota);
    }
}
