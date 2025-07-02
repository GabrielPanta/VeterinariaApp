package com.example.VeterinariaApp.service;

import com.example.VeterinariaApp.Dto.MascotaDTO;
import com.example.VeterinariaApp.entities.Mascota;
import com.example.VeterinariaApp.entities.Usuario;
import com.example.VeterinariaApp.repository.MascotaRepository;
import com.example.VeterinariaApp.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ✅ Registrar nueva mascota
    public Mascota registrarMascota(MascotaDTO dto, String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setRaza(dto.getRaza());
        mascota.setEdad(dto.getEdad());
        mascota.setTipo(dto.getTipo());
        mascota.setCliente(usuario); // Asegúrate que el atributo se llama "cliente"

        return mascotaRepository.save(mascota);
    }

    // ✅ Obtener mascotas activas del cliente autenticado
    public List<MascotaDTO> obtenerMascotasPorCliente(String emailCliente) {
        Usuario cliente = usuarioRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return mascotaRepository.findByClienteAndEstadoTrue(cliente).stream()
                .map(m -> new MascotaDTO(m.getId(), m.getNombre(), m.getRaza(), m.getEdad(), m.getTipo()))
                .collect(Collectors.toList());
    }

    // ✅ Eliminar lógicamente una mascota (HU-7)
    public boolean eliminarMascota(Long idMascota, String emailCliente) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findByIdAndEstadoTrue(idMascota);

        if (mascotaOpt.isPresent()) {
            Mascota mascota = mascotaOpt.get();

            // Validar que la mascota pertenezca al cliente autenticado
            if (!mascota.getCliente().getEmail().equals(emailCliente)) {
                return false;
            }

            mascota.setEstado(false); // Eliminación lógica
            mascotaRepository.save(mascota);
            return true;
        }

        return false;
    }
}

