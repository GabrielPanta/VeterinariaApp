package com.example.VeterinariaApp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialClinicoDTO {

    private Long idAtencion;
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamientos;
    private String nombreVeterinario;

}

