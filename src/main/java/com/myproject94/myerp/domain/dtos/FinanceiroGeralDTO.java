package com.myproject94.myerp.domain.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceiroGeralDTO {

    private BigDecimal totalReceita;

    private BigDecimal totalDespesas;

    private BigDecimal lucroLiquido;

    private Long totalChamados;

    private BigDecimal ticketMedio;
}
