package com.example.VeterinariaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.VeterinariaApp.entities.Cliente;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
}
