package com.example.VeterinariaApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VeterinariaApp.entities.Mascota;
import com.example.VeterinariaApp.entities.Usuario;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByCliente(Usuario cliente);
}
