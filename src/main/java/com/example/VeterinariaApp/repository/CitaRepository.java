package com.example.VeterinariaApp.repository;

import com.example.VeterinariaApp.entities.Cita;
import com.example.VeterinariaApp.entities.Veterinario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    Page<Cita> findByVeterinario(Veterinario veterinario, Pageable pageable);

    Page<Cita> findByVeterinarioAndFechaBetween(Veterinario veterinario, LocalDateTime inicio, LocalDateTime fin, Pageable pageable);
}


