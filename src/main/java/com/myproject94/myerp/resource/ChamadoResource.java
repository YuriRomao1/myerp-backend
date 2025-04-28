package com.myproject94.myerp.resource;

import com.myproject94.myerp.domain.Chamado;
import com.myproject94.myerp.domain.dtos.ChamadoDTO;
import com.myproject94.myerp.service.ChamadoService;
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
@RequestMapping(value = "/chamados")
@Tag(name = "Chamado", description = "Controlador para salvar e editar dados do chamado")
public class ChamadoResource {

    private final ChamadoService service;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca CHAMADO por ID", description = "Método responsavel por buscar um chamado por id")
    @ApiResponse(responseCode = "200", description = "CHAMADO encontrado com sucesso")
    @ApiResponse(responseCode = "400", description = "CHAMADO não encontrado")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
        Chamado obj = service.findById(id);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }

    @GetMapping
    @Operation(summary = "Buscar CHAMADOS", description = "Método responsavel por buscar todos os CHAMADO")
    @ApiResponse(responseCode = "202", description = "CHAMADO encontrado com sucesso")
    public ResponseEntity<List<ChamadoDTO>>findAll(){
        List<Chamado> list = service.findAll();
        List<ChamadoDTO> listDTO = list.stream().map(ChamadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    @Operation(summary = "Criar CHAMADO", description = "Método responsavel por criar novos CHAMADOS")
    @ApiResponse(responseCode = "201", description = "Chamado criado com sucesso")
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO objDTO){
        Chamado obj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar CHAMADO", description = "Método responsavel por atualizar novos CHAMADOS")
    @ApiResponse(responseCode = "201", description = "Chamado encontrado com sucesso")
    public ResponseEntity<ChamadoDTO> update (@PathVariable Integer id, @RequestBody ChamadoDTO objDTO){
        Chamado newObj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ChamadoDTO(newObj));
    }
}
