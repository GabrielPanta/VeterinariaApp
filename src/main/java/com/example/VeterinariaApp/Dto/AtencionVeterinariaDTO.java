package com.example.VeterinariaApp.Dto;

import lombok.Data;

@Data
public class AtencionVeterinariaDTO {
    private Long citaId;
    private Long veterinarioId;
    private Long mascotaId;
    private String diagnostico;
    private String tratamientos;
}
