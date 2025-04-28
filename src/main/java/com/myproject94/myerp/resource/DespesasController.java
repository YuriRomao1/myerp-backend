package com.myproject94.myerp.resource;

import com.myproject94.myerp.domain.Despesas;
import com.myproject94.myerp.domain.dtos.DespesasDTO;
import com.myproject94.myerp.service.DespesasService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/despesas")
@Tag(name = "Despesa", description = "Controller para salvar e editar dados das despesas fixa")
public class DespesasController {

    private final DespesasService service;

    @GetMapping
    public ResponseEntity<List<DespesasDTO>> findAll() {
        List<DespesasDTO> list = service.findAll()
                .stream().map(DespesasDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesasDTO> findById(@PathVariable Integer id) {
        Despesas despesa = service.findById(id);
        return ResponseEntity.ok(new DespesasDTO(despesa));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody DespesasDTO dto) {
        Despesas despesas = new Despesas();
        despesas.setDescricao(dto.getDescricao());
        despesas.setValor(dto.getValor());
        despesas.setDataVencimento(dto.getDataVencimento());
        despesas.setStatus(dto.getStatus());
        Despesas novaDespesa = service.create(despesas);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaDespesa.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesasDTO> update(@PathVariable Integer id, @RequestBody DespesasDTO dto) {
        Despesas despesas = new Despesas();
        despesas.setDescricao(dto.getDescricao());
        despesas.setValor(dto.getValor());
        despesas.setDataVencimento(dto.getDataVencimento());
        despesas.setStatus(dto.getStatus());
        Despesas updated = service.update(id, despesas);
        return ResponseEntity.ok(new DespesasDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
