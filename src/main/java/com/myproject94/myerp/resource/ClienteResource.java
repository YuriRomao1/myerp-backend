package com.myproject94.myerp.resource;

import com.myproject94.myerp.domain.Cliente;
import com.myproject94.myerp.domain.dtos.ClienteDTO;
import com.myproject94.myerp.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/clientes")
@Tag(name = "Clientes", description = "Controlador para salvar e editar Clientes")
public class ClienteResource {

    private final ClienteService service;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca CLIENTE por ID", description = "Método responsavel por buscar um CLIENTE por id")
    @ApiResponse(responseCode = "200", description = "CLIENTE encontrado com sucesso")
    @ApiResponse(responseCode = "400", description = "CLIENTE não encontrado")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    @GetMapping
    @Operation(summary = "Buscar CLIENTE", description = "Método responsavel por buscar todos os CLIENTES")
    @ApiResponse(responseCode = "202", description = "CLIENTE encontrado com sucesso")
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDTO = list.stream().map(ClienteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    @Operation(summary = "Criar CLIENTE", description = "Método responsavel por criar novos CLIENTES")
    @ApiResponse(responseCode = "201", description = "CLIENTE criado com sucesso")
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar CLIENTE", description = "Método responsavel por atualizar novos CLIENTES")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO){
        Cliente obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Atualizar CHAMADO", description = "Método responsavel por deletar CLIENTE")
    public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
