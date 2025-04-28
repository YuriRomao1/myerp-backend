package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.Chamado;
import com.myproject94.myerp.domain.Cliente;
import com.myproject94.myerp.domain.Despesas;
import com.myproject94.myerp.domain.Tecnico;

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

@Service
@RequiredArgsConstructor
public class DBServices {


    private final TecnicoRepository tecnicoRepository;

    private final ClienteRepository clienteRepository;

    private final ChamadoRepository chamadoRepository;

    private final PessoaRepository pessoaRepository;

    private final DespesasRepository despesasRepository;

    private final BCryptPasswordEncoder encoder;

    public void instanciaDB() {
        Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "550.482.150-95", "valdir@mail.com", encoder.encode("123"));
        Tecnico tec6 = new Tecnico(null, "Yuri Romao", "550.483.150-95", "yuri@mail.com", encoder.encode("123"));
        tec1.addPerfis(Perfil.ADMIN);
        tec6.addPerfis(Perfil.ADMIN);
        Tecnico tec2 = new Tecnico(null, "Richard Stallman", "903.347.070-56", "stallman@mail.com", encoder.encode("123"));
        Tecnico tec3 = new Tecnico(null, "Claude Elwood Shannon", "271.068.470-54", "shannon@mail.com", encoder.encode("123"));
        Tecnico tec4 = new Tecnico(null, "Tim Berners-Lee", "162.720.120-39", "lee@mail.com", encoder.encode("123"));
        Tecnico tec5 = new Tecnico(null, "Linus Torvalds", "778.556.170-27", "linus@mail.com", encoder.encode("123"));

        Cliente cli1 = new Cliente(null, "Albert Einstein", "111.661.890-74", "einstein@mail.com", encoder.encode("123"));
        Cliente cli2 = new Cliente(null, "Marie Curie", "322.429.140-06", "curie@mail.com",encoder.encode("123"));
        Cliente cli3 = new Cliente(null, "Charles Darwin", "792.043.830-62", "darwin@mail.com", encoder.encode("123"));
        Cliente cli4 = new Cliente(null, "Stephen Hawking", "177.409.680-30", "hawking@mail.com", encoder.encode("123"));
        Cliente cli5 = new Cliente(null, "Max Planck", "081.399.300-83", "planck@mail.com", encoder.encode("123"));

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "Teste chamado 1", tec1, cli1, BigDecimal.valueOf(1000.00));
        Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 2", "Teste chamado 2", tec1, cli2, BigDecimal.valueOf(1000.00));
        Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 3", "Teste chamado 3", tec2, cli3, BigDecimal.valueOf(1000.00));
        Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 4", "Teste chamado 4", tec3, cli3, BigDecimal.valueOf(1000.00));
        Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 5", "Teste chamado 5", tec2, cli1, BigDecimal.valueOf(1000.00));
        Chamado c6 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 7", "Teste chamado 6", tec1, cli5, BigDecimal.valueOf(1000.00));


        Despesas d1 = new Despesas(null, "Ajudante", BigDecimal.valueOf(200.00), LocalDate.of(2025, 3, 15), StatusDespesa.PENDENTE);
        Despesas d2 = new Despesas(null, "uz", BigDecimal.valueOf(200.00), LocalDate.of(2025, 4, 15), StatusDespesa.PENDENTE);
        Despesas d3 = new Despesas(null, "Agua", BigDecimal.valueOf(300.00), LocalDate.of(2025, 3, 14), StatusDespesa.PAGO);
        Despesas d4 = new Despesas(null, "Agua", BigDecimal.valueOf(100.00), LocalDate.of(2025, 4, 14), StatusDespesa.PAGO);
        Despesas d5 = new Despesas(null, "Telefone", BigDecimal.valueOf(200.00), LocalDate.of(2025, 4, 14), StatusDespesa.ATRASADO);
        Despesas d6 = new Despesas(null, "Fornecedor", BigDecimal.valueOf(700.00), LocalDate.of(2025, 4, 14), StatusDespesa.ATRASADO);

        
        pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5));
        chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
        despesasRepository.saveAll(Arrays.asList(d1,d2,d3,d4,d5,d6));
    }
}
