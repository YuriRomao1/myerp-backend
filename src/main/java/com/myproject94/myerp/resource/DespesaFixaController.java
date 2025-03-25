package com.myproject94.myerp.resource;

import com.myproject94.myerp.domain.DespesaFixa;
import com.myproject94.myerp.domain.dtos.DespesaFixaDTO;
import com.myproject94.myerp.service.DespesaFixaService;
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
public class DespesaFixaController {

    private DespesaFixaService service;

    @GetMapping
    public ResponseEntity<List<DespesaFixaDTO>> findAll() {
        List<DespesaFixaDTO> list = service.findAll()
                .stream().map(DespesaFixaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaFixaDTO> findById(@PathVariable Integer id) {
        DespesaFixa despesa = service.findById(id);
        return ResponseEntity.ok(new DespesaFixaDTO(despesa));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody DespesaFixaDTO dto) {
        DespesaFixa despesa = new DespesaFixa();
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setDataVencimento(dto.getDataVencimento());
        despesa.setDataPagamento(dto.getDataPagamento());
        despesa.setStatus(dto.getStatus());
        DespesaFixa novaDespesa = service.create(despesa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaDespesa.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaFixaDTO> update(@PathVariable Integer id, @RequestBody DespesaFixaDTO dto) {
        DespesaFixa despesa = new DespesaFixa();
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setDataVencimento(dto.getDataVencimento());
        despesa.setDataPagamento(dto.getDataPagamento());
        despesa.setStatus(dto.getStatus());
        DespesaFixa updated = service.update(id, despesa);
        return ResponseEntity.ok(new DespesaFixaDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
