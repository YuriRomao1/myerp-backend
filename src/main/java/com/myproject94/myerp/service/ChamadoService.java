package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.Chamado;
import com.myproject94.myerp.domain.Cliente;
import com.myproject94.myerp.domain.Tecnico;
import com.myproject94.myerp.domain.dtos.ChamadoDTO;
import com.myproject94.myerp.domain.enums.Prioridade;
import com.myproject94.myerp.domain.enums.Status;
import com.myproject94.myerp.repositories.ChamadoRepository;
import com.myproject94.myerp.service.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChamadoService {


    private final ChamadoRepository repository;

    private final TecnicoService tecnicoService;

    private final ClienteService clienteService;

    public List<Chamado> findAll(){
        return repository.findAll();
    }

    public Chamado findById(Integer id){
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id ));
    }

    public Chamado create(@Valid ChamadoDTO objDTO) {
        return repository.save(newChamado(objDTO));
    }

    public Chamado update(Integer id, @Valid ChamadoDTO objDTO){
        objDTO.setId(id);
        Chamado oldObjd = findById(id);
        oldObjd = newChamado(objDTO);
        return repository.save(oldObjd);
    }

    private Chamado newChamado(ChamadoDTO obj){
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        Chamado chamado = new Chamado();
        if (obj.getId() != null) {
            chamado.setId(obj.getId());
        }

        if (obj.getStatus().equals(2)){
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        chamado.setValor(obj.getValor());

        return chamado;
    }
}
