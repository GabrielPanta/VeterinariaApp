package com.example.VeterinariaApp.repository;

import com.example.VeterinariaApp.entities.AtencionVeterinaria;
import com.example.VeterinariaApp.entities.Mascota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtencionVeterinariaRepository extends JpaRepository<AtencionVeterinaria, Long> {

    Page<AtencionVeterinaria> findByMascotaOrderByFechaAtencionDesc(Mascota mascota, Pageable pageable);
}

