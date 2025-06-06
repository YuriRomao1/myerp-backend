package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.Chamado;
import com.myproject94.myerp.domain.Cliente;
import com.myproject94.myerp.domain.Tecnico;
import com.myproject94.myerp.domain.dtos.ChamadoDTO;
import com.myproject94.myerp.domain.enums.Prioridade;
import com.myproject94.myerp.domain.enums.Status;
import com.myproject94.myerp.repositories.ChamadoRepository;
import com.myproject94.myerp.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ChamadoServiceTest {

    @Mock
    private ChamadoRepository repository;

    @Mock
    private TecnicoService tecnicoService;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ChamadoService service;

    private ChamadoDTO dto;
    private Tecnico mockTecnico;
    private Cliente mockCliente;
    private Chamado sampleChamado;

    @BeforeEach
    void setup() {
        mockTecnico = new Tecnico(1, "Richard Stallman", "903.347.070-56", "21 98888-3333", "Rua Compiladores, 50", LocalDate.of(1906, 12, 9), "stallman@mail.com", "senha123");


        mockCliente = new Cliente(2,"Albert","11166189074","albert@mail.com","21 99999-0001","Rua das Ciências, 42 – Petrópolis, RJ", LocalDate.of(1879, 3, 14));
        dto = ChamadoDTO.builder()
                .id(null)
                .prioridade(Prioridade.ALTA.getCodigo())
                .status(Status.ABERTO.getCodigo())
                .titulo("Titulo")
                .observacoes("Obs")
                .tecnico(mockTecnico.getId())
                .cliente(mockCliente.getId())
                .valor(BigDecimal.valueOf(123.45))
                .build();
        sampleChamado = new Chamado();
        sampleChamado.setId(1);
        sampleChamado.setPrioridade(Prioridade.ALTA);
        sampleChamado.setStatus(Status.ABERTO);
        sampleChamado.setTitulo("Titulo");
        sampleChamado.setObservacoes("Obs");
        sampleChamado.setTecnico(mockTecnico);
        sampleChamado.setCliente(mockCliente);
        sampleChamado.setValor(BigDecimal.valueOf(123.45));
    }

    @Test
    void findAllShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(sampleChamado));
        List<Chamado> result = service.findAll();
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void findByIdShouldReturnChamado() {
        when(repository.findById(1)).thenReturn(Optional.of(sampleChamado));
        Chamado result = service.findById(1);
        assertNotNull(result);
        assertEquals(sampleChamado.getId(), result.getId());
    }

    @Test
    void findByIdShouldThrowException() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void createShouldSaveChamado() {
        when(tecnicoService.findById(mockTecnico.getId())).thenReturn(mockTecnico);
        when(clienteService.findById(mockCliente.getId())).thenReturn(mockCliente);
        when(repository.save(any(Chamado.class))).thenReturn(sampleChamado);

        Chamado result = service.create(dto);

        assertNotNull(result);
        assertEquals(Prioridade.ALTA, result.getPrioridade());
        assertEquals(Status.ABERTO, result.getStatus());
        verify(repository).save(any(Chamado.class));
    }

    @Test
    void updateShouldModifyChamado() {
        ChamadoDTO updateDto = dto;
        updateDto.setStatus(Status.ENCERRADO.getCodigo());
        when(repository.findById(1)).thenReturn(Optional.of(sampleChamado));
        when(tecnicoService.findById(mockTecnico.getId())).thenReturn(mockTecnico);
        when(clienteService.findById(mockCliente.getId())).thenReturn(mockCliente);
        when(repository.save(any(Chamado.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Chamado updated = service.update(1, updateDto);

        assertEquals(Status.ENCERRADO, updated.getStatus());
        assertNotNull(updated.getDataFechamento());
        verify(repository).save(any(Chamado.class));
    }
}