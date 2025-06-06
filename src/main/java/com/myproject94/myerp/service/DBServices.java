package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.*;

import com.myproject94.myerp.domain.enums.Perfil;
import com.myproject94.myerp.domain.enums.Prioridade;
import com.myproject94.myerp.domain.enums.Status;
import com.myproject94.myerp.domain.enums.StatusDespesa;
import com.myproject94.myerp.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DBServices {

    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository;
    private final ChamadoRepository chamadoRepository;
    private final DespesasRepository despesasRepository;
    private final EmpresaRepository empresaRepository;
    private final ChamadoEmpresaRepository chamadoEmpresaRepository;
    private final BCryptPasswordEncoder encoder;

    public void instanciaDB() {
        Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "550.482.150-95", "21 98888-1111", "Rua Algoritmos, 100", LocalDate.of(1815, 12, 10),"valdir@mail.com", encoder.encode("123"));
        Tecnico tec6 = new Tecnico(null, "Yuri Romao", "550.483.150-95", "21 98888-2222", "Rua Criptografia, 42",  LocalDate.of(1912, 12, 10), "yuri@mail.com", encoder.encode("123"));
        tec1.addPerfis(Perfil.ADMIN);
        tec6.addPerfis(Perfil.ADMIN);
        Tecnico tec2 = new Tecnico(null, "Richard Stallman", "903.347.070-56","21 98888-3333", "Rua Compiladores, 50", LocalDate.of(1906, 12, 13), "stallman@mail.com", encoder.encode("123"));
        Tecnico tec3 = new Tecnico(null, "Joao Silva", "903.347.070-40","21 98888-4444", "Rua Compiladores, 80", LocalDate.of(1909, 11, 14), "compiladores@mail.com", encoder.encode("123"));
        Tecnico tec4 = new Tecnico(null, "Tim Berners-Lee", "162.720.120-39", "21 98888-2222", "Rua Criptografia, 42", LocalDate.of(1904, 06, 23),"lee@mail.com", encoder.encode("123"));
        Tecnico tec5 = new Tecnico(null, "Linus Torvalds", "778.556.170-27", "21 98888-3333", "Rua Compiladores, 50", LocalDate.of(1906, 12, 11),"linus@mail.com", encoder.encode("123"));

        tecnicoRepository.saveAll(List.of(tec1, tec2, tec3, tec4, tec5, tec6));

        Cliente cli1 = new Cliente(null, "Albert Einstein", "11166189074", "albert@mail.com", "21 99999-0001", "Rua das Ciências, 42 – Petrópolis, RJ", LocalDate.of(1879, 3, 14));
        Cliente cli2 = new Cliente(null, "Marie Curie", "32242914006", "marie@mail.com", "21 99999-0002", "Av. Rádio, 10 – Petrópolis, RJ", LocalDate.of(1867, 11, 7));
        Cliente cli3 = new Cliente(null, "Charles Darwin", "79204383062", "charles@mail", "21 99999-0003", "Praça Origem, 15 – Rio de Janeiro", LocalDate.of(1809, 2, 12));
        Cliente cli4 = new Cliente(null, "Stephen Hawking", "17740968030", "stephen@mail.com", "21 99999-0004", "Rua Relatividade, 1 – Petrópolis, RJ", LocalDate.of(1942, 1, 8));
        Cliente cli5 = new Cliente(null, "Max Planck", "08139930083", "max@mail.com", "21 99999-0005", "Alameda Quântica, 7 – Petrópolis, RJ", LocalDate.of(1858, 4, 23));

        clienteRepository.saveAll(List.of(cli1, cli2, cli3, cli4, cli5));

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "Teste chamado 1", tec1, cli1, BigDecimal.valueOf(1000.00));
        Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 2", "Teste chamado 2", tec1, cli2, BigDecimal.valueOf(1000.00));
        Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 3", "Teste chamado 3", tec2, cli3, BigDecimal.valueOf(1000.00));
        Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 4", "Teste chamado 4", tec3, cli3, BigDecimal.valueOf(1000.00));
        Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 5", "Teste chamado 5", tec2, cli1, BigDecimal.valueOf(1000.00));
        Chamado c6 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 7", "Teste chamado 6", tec1, cli5, BigDecimal.valueOf(1000.00));
        Chamado c7 = new Chamado( null, LocalDate.of(25,1,25), LocalDate.of(25,1,26),Prioridade.BAIXA, Status.ENCERRADO, "Chamado 7", "Teste chamado 7", tec1, cli5, BigDecimal.valueOf(1000.00));
        Chamado c8 = new Chamado (null, Prioridade.MEDIA, Status.ANDAMENTO,"Chamado Abertura","Apenas abertura",tec1,cli1,BigDecimal.valueOf(100));
        Chamado c9 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Com Visita", "Visita agendada", tec1, cli2, BigDecimal.valueOf(1500.00), LocalDate.of(2025, 6, 10));
        Chamado c10 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Completo", "Chamado encerrado", tec2, cli3, BigDecimal.valueOf(2000.00), LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 12));


        chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));

        Despesas d1 = new Despesas(null, "Ajudante", BigDecimal.valueOf(200.00), LocalDate.of(2025, 3, 15), StatusDespesa.PENDENTE);
        Despesas d2 = new Despesas(null, "Luz", BigDecimal.valueOf(200.00), LocalDate.of(2025, 4, 15), StatusDespesa.PENDENTE);
        Despesas d3 = new Despesas(null, "Agua", BigDecimal.valueOf(300.00), LocalDate.of(2025, 3, 14), StatusDespesa.PAGO);
        Despesas d4 = new Despesas(null, "Agua", BigDecimal.valueOf(100.00), LocalDate.of(2025, 4, 14), StatusDespesa.PAGO);
        Despesas d5 = new Despesas(null, "Telefone", BigDecimal.valueOf(200.00), LocalDate.of(2025, 4, 14), StatusDespesa.ATRASADO);
        Despesas d6 = new Despesas(null, "Fornecedor", BigDecimal.valueOf(700.00), LocalDate.of(2025, 4, 14), StatusDespesa.ATRASADO);

        despesasRepository.saveAll(Arrays.asList(d1, d2, d3, d4, d5, d6));

        Empresa emp1 = new Empresa(null, "Acme Tecnologia LTDA", "AcmeTech", "12345678000195", "contato@acmetech.com.br", "21 99999-0001", LocalDate.now());
        Empresa emp2 = new Empresa(null, "Inovatech Soluções EIRELI", "Inovatech", "22345678000196", "suporte@inovatech.com.br", "21 99999-0002", LocalDate.now());
        Empresa emp3 = new Empresa(null, "Sigma Serviços LTDA", "SigmaServ", "32345678000197", "contato@sigmaserv.com.br", "21 99999-0003", LocalDate.now());
        Empresa emp4 = new Empresa(null, "Alpha Consultoria LTDA", "AlphaConsul", "42345678000198", "vendas@alphaconsul.com.br", "21 99999-0004", LocalDate.now());
        Empresa emp5 = new Empresa(null, "Beta Distribuidora LTDA", "BetaDist", "52345678000199", "financeiro@betadist.com.br", "21 99999-0005", LocalDate.now());

        empresaRepository.saveAll(List.of(emp1, emp2, emp3, emp4, emp5));

        ChamadoEmpresa ce1 = new ChamadoEmpresa(null, Prioridade.ALTA, Status.ABERTO, "Chamado Empresa 01", "Teste chamado empresa", tec1, emp1, BigDecimal.valueOf(100));
        ChamadoEmpresa ce2 = new ChamadoEmpresa(null, Prioridade.MEDIA, Status.ABERTO, "Chamado Empresa 02", "Teste chamado empresa 2", tec2, emp2, BigDecimal.valueOf(10));
        ChamadoEmpresa ce3 = new ChamadoEmpresa(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado Empresa 03", "Teste chamado empresa 3", tec3, emp3, BigDecimal.valueOf(20));
        ChamadoEmpresa ce4 = new ChamadoEmpresa(null, Prioridade.ALTA, Status.ANDAMENTO, "Chamado Empresa 04", "Teste chamado empresa 4", tec4, emp4, BigDecimal.valueOf(50));
        ChamadoEmpresa ce5 = new ChamadoEmpresa(null, Prioridade.ALTA, Status.ABERTO, "Chamado Empresa 05", "Teste chamado empresa 5 ", tec5, emp5, BigDecimal.valueOf(1000));
        ChamadoEmpresa ce6 = new ChamadoEmpresa(
                null,
                Prioridade.BAIXA,
                Status.ABERTO,
                "Empresa Abertura",
                "Somente abertura registrada",
                tec1,
                emp1,
                BigDecimal.valueOf(300.00)
        );

        ChamadoEmpresa ce7 = new ChamadoEmpresa(
                null,
                Prioridade.MEDIA,
                Status.ANDAMENTO,
                "Empresa Visita",
                "Abertura e visita marcada",
                tec2,
                emp2,
                BigDecimal.valueOf(500.00),
                LocalDate.of(2025, 7, 1)
        );

        ChamadoEmpresa ce8 = new ChamadoEmpresa(
                null,
                Prioridade.ALTA,
                Status.ENCERRADO,
                "Empresa Completo",
                "Com abertura, visita e encerramento",
                tec3,
                emp3,
                BigDecimal.valueOf(800.00),
                LocalDate.of(2025, 6, 10),
                LocalDate.of(2025, 6, 15)
        );

        chamadoEmpresaRepository.saveAll(List.of(ce1, ce2, ce3, ce4, ce5, ce6, ce7, ce8));
    }
}