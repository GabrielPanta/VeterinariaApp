package com.example.VeterinariaApp.service;

import com.example.VeterinariaApp.Dto.CitaDTO;
import com.example.VeterinariaApp.entities.Cita;
import com.example.VeterinariaApp.entities.Veterinario;
import com.example.VeterinariaApp.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public Page<CitaDTO> obtenerCitasDeVeterinario(Veterinario veterinario, LocalDateTime fechaFiltro, Pageable pageable) {
        Page<Cita> citas;

        if (fechaFiltro != null) {
            LocalDateTime inicioDia = fechaFiltro.toLocalDate().atStartOfDay();
            LocalDateTime finDia = inicioDia.plusDays(1).minusNanos(1);
            citas = citaRepository.findByVeterinarioAndFechaBetween(veterinario, inicioDia, finDia, pageable);
        } else {
            citas = citaRepository.findByVeterinario(veterinario, pageable);
        }

        return citas.map(cita -> new CitaDTO(cita.getId(), cita.getFecha(), cita.getEstado()));
    }
}

