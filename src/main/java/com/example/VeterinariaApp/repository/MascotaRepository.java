package com.example.VeterinariaApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VeterinariaApp.entities.Mascota;
import com.example.VeterinariaApp.entities.Usuario;
import java.util.Optional;


public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // Listar todas las mascotas de un cliente (activas e inactivas)
    List<Mascota> findByCliente(Usuario cliente);

    // Listar solo las mascotas activas de un cliente
    List<Mascota> findByClienteAndEstadoTrue(Usuario cliente);

    // Buscar mascota activa por ID
    Optional<Mascota> findByIdAndEstadoTrue(Long id);
}

