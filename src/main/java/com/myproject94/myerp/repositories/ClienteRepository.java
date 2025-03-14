package com.myproject94.myerp.repositories;

import com.myproject94.myerp.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
