package com.example.VeterinariaApp.service;

import com.example.VeterinariaApp.Dto.HistorialClinicoDTO;
import com.example.VeterinariaApp.entities.AtencionVeterinaria;
import com.example.VeterinariaApp.entities.Cliente;
import com.example.VeterinariaApp.entities.Mascota;
import com.example.VeterinariaApp.repository.AtencionVeterinariaRepository;
import com.example.VeterinariaApp.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class HistorialClinicoService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private AtencionVeterinariaRepository atencionRepository;

    public Page<HistorialClinicoDTO> obtenerHistorial(Cliente cliente, Long idMascota, Pageable pageable) {

        // Verificar que la mascota pertenece al cliente
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        if (!mascota.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("Acceso denegado. La mascota no pertenece al cliente autenticado");
        }

        // Buscar historial de atenciones paginadas y ordenadas
        Page<AtencionVeterinaria> atenciones = atencionRepository.findByMascotaOrderByFechaAtencionDesc(mascota, pageable);

        return atenciones.map(atencion -> {
            return new HistorialClinicoDTO(
                    atencion.getId(),
                    atencion.getFechaAtencion(),
                    atencion.getDiagnostico(),
                    atencion.getTratamientos(),
                    atencion.getVeterinario().getNombre()
            );
        });
    }
}
