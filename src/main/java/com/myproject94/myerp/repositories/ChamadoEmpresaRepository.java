package com.myproject94.myerp.repositories;

import com.myproject94.myerp.domain.Chamado;
import com.myproject94.myerp.domain.ChamadoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoEmpresaRepository extends JpaRepository<ChamadoEmpresa,Integer> {
}
