// src/main/java/com/example/VeterinariaApp/Dto/AtencionUpdateDTO.java
package com.example.VeterinariaApp.Dto;

import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class AtencionUpdateDTO {

    @Size(max = 1000, message = "El diagnóstico no debe exceder los 1000 caracteres")
    private String diagnostico;

    @Size(max = 2000, message = "Los tratamientos no deben exceder los 2000 caracteres")
    private String tratamientos;

    // Puedes añadir más campos si se permite actualizar otros
}

