package com.example.VeterinariaApp.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioDTO {

    @NotBlank
    private String nombre;

   @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    
}
