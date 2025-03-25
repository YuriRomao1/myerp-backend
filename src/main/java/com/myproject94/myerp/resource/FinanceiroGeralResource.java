package com.myproject94.myerp.resource;

import com.myproject94.myerp.domain.dtos.FinanceiroGeralDTO;
import com.myproject94.myerp.service.FinanceiroAnaliseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/financeiro/analise")
@Tag(name = "Analise Financeira", description = "Controller para analisar dados financeiro da aplicação")
public class FinanceiroGeralResource {

    private final FinanceiroAnaliseService service;


    // Exemplo: análise por período, onde o usuário envia datas de início e fim
    @GetMapping
    public ResponseEntity<FinanceiroGeralDTO> getFinancialOverview(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        FinanceiroGeralDTO overview = service.getOverviewByPeriod(inicio, fim);
        return ResponseEntity.ok(overview);
    }

    // Você pode adicionar endpoints específicos para diário, semanal e anual.
}



