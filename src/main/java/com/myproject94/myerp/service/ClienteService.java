package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.Cliente;
import com.myproject94.myerp.domain.dtos.ClienteDTO;
import com.myproject94.myerp.repositories.ClienteRepository;
import com.myproject94.myerp.service.exceptions.DataIntegrityViolationException;
import com.myproject94.myerp.service.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:" + id));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(@Valid ClienteDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return repository.save(newObj);
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        Cliente updated = new Cliente(objDTO);
        return repository.save(updated);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (!obj.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Cliente> obj = repository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && !Objects.equals(obj.get().getId(), objDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = repository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && !Objects.equals(obj.get().getId(), objDTO.getId())) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema");
        }
    }
}