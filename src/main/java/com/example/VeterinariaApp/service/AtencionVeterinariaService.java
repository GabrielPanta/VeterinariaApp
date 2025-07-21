package com.example.VeterinariaApp.service;

import com.example.VeterinariaApp.Dto.AtencionUpdateDTO;
import com.example.VeterinariaApp.Dto.AtencionVeterinariaDTO;
import com.example.VeterinariaApp.entities.*;
import com.example.VeterinariaApp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AtencionVeterinariaService {

    @Autowired
    private AtencionVeterinariaRepository atencionRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Transactional
    public AtencionVeterinaria registrarAtencion(AtencionVeterinariaDTO dto, String emailVeterinario) {

        // Obtener cita
        Cita cita = citaRepository.findById(dto.getCitaId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        // Obtener veterinario autenticado (por email o usa dto.getVeterinarioId para validar)
        Veterinario veterinarioAutenticado = veterinarioRepository.findByEmail(emailVeterinario)
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        // Verificar que el veterinario autenticado coincida con el veterinario que se asigna en la atención
        if (!veterinarioAutenticado.getId().equals(dto.getVeterinarioId())) {
            throw new RuntimeException("No autorizado para registrar atención con ese veterinario");
        }

        // Validar que la cita pertenece a este veterinario
        if (!cita.getVeterinario().getId().equals(veterinarioAutenticado.getId())) {
            throw new RuntimeException("La cita no está asignada a este veterinario");
        }

        // Obtener mascota
        Mascota mascota = mascotaRepository.findById(dto.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        // Validar que la mascota es la asignada en la cita
        if (!mascota.getId().equals(cita.getCliente().getId())) {
            // En caso que la entidad Mascota tenga relación diferente, ajusta esta validación según tu modelo
            // Lo básico es validar que la mascota esté vinculada al cliente que tiene la cita
            // Por ejemplo, puedes validar mascota.getCliente().getId() == cita.getCliente().getId()
            throw new RuntimeException("La mascota no corresponde a la cita");
        }

        // Crear entidad atención
        AtencionVeterinaria atencion = new AtencionVeterinaria();
        atencion.setCita(cita);
        atencion.setVeterinario(veterinarioAutenticado);
        atencion.setMascota(mascota);
        atencion.setFechaAtencion(LocalDateTime.now());
        atencion.setDiagnostico(dto.getDiagnostico());
        atencion.setTratamientos(dto.getTratamientos());

        return atencionRepository.save(atencion);
    }
    // Nuevo método para actualizar atención (HU13)
    @Transactional
    public AtencionVeterinaria actualizarDiagnosticoTratamiento(
            Long atencionId, AtencionUpdateDTO dto, String emailVeterinario) {

        AtencionVeterinaria atencion = atencionRepository.findById(atencionId)
                .orElseThrow(() -> new RuntimeException("Atención veterinaria no encontrada."));

        Veterinario veterinarioAutenticado = veterinarioRepository.findByEmail(emailVeterinario)
                .orElseThrow(() -> new RuntimeException("Veterinario autenticado no encontrado."));

        // Validar que el veterinario autenticado es el que registró esta atención
        if (!atencion.getVeterinario().getId().equals(veterinarioAutenticado.getId())) {
            throw new RuntimeException("No tiene permisos para actualizar esta atención.");
        }

        // Actualizar campos
        if (dto.getDiagnostico() != null) {
            atencion.setDiagnostico(dto.getDiagnostico());
        }
        if (dto.getTratamientos() != null) {
            atencion.setTratamientos(dto.getTratamientos());
        }

        // Puedes añadir aquí validaciones de campos clínicos si necesitas lógica compleja,
        // por ejemplo, si un diagnóstico no puede estar vacío si ya se ha registrado.
        // Las validaciones de tamaño ya están en el DTO con @Size.

        return atencionRepository.save(atencion);
    }
}

