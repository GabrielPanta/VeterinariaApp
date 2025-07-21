package com.example.VeterinariaApp.repository;

import com.example.VeterinariaApp.entities.Cita;
import com.example.VeterinariaApp.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    Page<Cita> findByClienteAndEstado(Cliente cliente, String estado, Pageable pageable);
    Page<Cita> findByCliente(Cliente cliente, Pageable pageable);
}

