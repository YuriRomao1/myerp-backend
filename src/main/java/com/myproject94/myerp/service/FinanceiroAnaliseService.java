package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.Chamado;
import com.myproject94.myerp.domain.Despesas;
import com.myproject94.myerp.domain.dtos.FinanceiroGeralDTO;
import com.myproject94.myerp.repositories.ChamadoRepository;
import com.myproject94.myerp.repositories.DespesasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceiroAnaliseService {

    private ChamadoRepository chamadoRepository;

    private DespesasRepository despesasRepository;


    public FinanceiroGeralDTO getOverviewByPeriod(LocalDate inicio, LocalDate fim) {
        // Filtrar chamados pelo período
        List<Chamado> chamados = chamadoRepository.findAll().stream()
                .filter(c -> !c.getDataAbertura().isBefore(inicio) && !c.getDataAbertura().isAfter(fim))
                .collect(Collectors.toList());

        // Filtrar despesas fixas pelo período (por data de vencimento ou data de pagamento)
        List<Despesas> despesas = despesasRepository.findAll().stream()
                .filter(d -> !d.getDataVencimento().isBefore(inicio) && !d.getDataVencimento().isAfter(fim))
                .collect(Collectors.toList());

        BigDecimal totalReceita = chamados.stream()
                .map(c -> c.getValor() != null ? c.getValor() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = despesas.stream()
                .map(d -> d.getValor() != null ? d.getValor() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal lucroLiquido = totalReceita.subtract(totalDespesas);
        Long totalChamados = (long) chamados.size();
        BigDecimal ticketMedio = totalChamados > 0 ?
                totalReceita.divide(BigDecimal.valueOf(totalChamados), BigDecimal.ROUND_HALF_UP) :
                BigDecimal.ZERO;

        FinanceiroGeralDTO overview = new FinanceiroGeralDTO();
        overview.setTotalReceita(totalReceita);
        overview.setTotalDespesas(totalDespesas);
        overview.setLucroLiquido(lucroLiquido);
        overview.setTotalChamados(totalChamados);
        overview.setTicketMedio(ticketMedio);

        return overview;
    }

}
