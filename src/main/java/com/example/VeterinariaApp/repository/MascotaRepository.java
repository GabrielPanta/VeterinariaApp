package com.example.VeterinariaApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VeterinariaApp.entities.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
   List<Mascota> findByUsuarioId(Long id);
}
