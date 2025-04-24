package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.Cliente;
import com.myproject94.myerp.domain.Pessoa;
import com.myproject94.myerp.domain.dtos.ClienteDTO;
import com.myproject94.myerp.repositories.ClienteRepository;
import com.myproject94.myerp.repositories.PessoaRepository;
import com.myproject94.myerp.service.exceptions.DataIntegrityViolationException;
import com.myproject94.myerp.service.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final PessoaRepository pessoaRepository;
    private final BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:" + id));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return repository.save(newObj);
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);

        if(!objDTO.getSenha().equals(oldObj.getSenha())) {
            objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        }

        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (!obj.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && !Objects.equals(obj.get().getId(), objDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && !Objects.equals(obj.get().getId(), objDTO.getId())) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema");
        }
    }
}
