package com.example.VeterinariaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VeterinariaApp.entities.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente, Long>{

}
