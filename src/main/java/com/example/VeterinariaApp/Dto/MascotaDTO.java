package com.example.VeterinariaApp.Dto;

import jakarta.validation.constraints.NotBlank;

public class MascotaDTO {
    @NotBlank
    private String nombre;

    private String raza;

    private Integer edad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    
}
