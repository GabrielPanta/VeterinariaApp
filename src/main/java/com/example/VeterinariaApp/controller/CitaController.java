package com.example.VeterinariaApp.controller;

import com.example.VeterinariaApp.Dto.CitaDTO;
import com.example.VeterinariaApp.entities.Cliente;
import com.example.VeterinariaApp.repository.ClienteRepository;
import com.example.VeterinariaApp.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/mis")
    public ResponseEntity<Page<CitaDTO>> getMisCitas(
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fecha,desc") String sort,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        Cliente cliente = clienteRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<CitaDTO> citas = citaService.obtenerCitasDeCliente(cliente, estado, pageable);

        return ResponseEntity.ok(citas);
    }
}

