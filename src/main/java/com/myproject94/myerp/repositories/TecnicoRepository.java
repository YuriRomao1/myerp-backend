package com.myproject94.myerp.repositories;

import com.myproject94.myerp.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico,Integer> {

    Optional<Tecnico> findByCpf(String cpf);
    Optional<Tecnico> findByEmail(String email);
}
