package com.example.VeterinariaApp.service;

import com.example.VeterinariaApp.Dto.CitaDTO;
import com.example.VeterinariaApp.entities.Cliente;
import com.example.VeterinariaApp.entities.Cita;
import com.example.VeterinariaApp.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public Page<CitaDTO> obtenerCitasDeCliente(Cliente cliente, String estado, Pageable pageable) {
        Page<Cita> citas;

        if (StringUtils.hasText(estado)) {
            estado = estado.toUpperCase();
            citas = citaRepository.findByClienteAndEstado(cliente, estado, pageable);
        } else {
            citas = citaRepository.findByCliente(cliente, pageable);
        }

        return citas.map(cita -> new CitaDTO(cita.getId(), cita.getFecha(), cita.getEstado()));
    }
}

