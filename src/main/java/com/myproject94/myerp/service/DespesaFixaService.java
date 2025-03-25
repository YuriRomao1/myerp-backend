package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.DespesaFixa;
import com.myproject94.myerp.repositories.DespesaFixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesaFixaService {

    private DespesaFixaRepository repository;

    public List<DespesaFixa> findAll() {
        return repository.findAll();
    }

    public DespesaFixa findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada! ID: " + id));
    }

    public DespesaFixa create(DespesaFixa despesa) {
        return repository.save(despesa);
    }

    public DespesaFixa update(Integer id, DespesaFixa updatedDespesa) {
        DespesaFixa existing = findById(id);
        existing.setDescricao(updatedDespesa.getDescricao());
        existing.setValor(updatedDespesa.getValor());
        existing.setDataVencimento(updatedDespesa.getDataVencimento());
        existing.setDataPagamento(updatedDespesa.getDataPagamento());
        existing.setStatus(updatedDespesa.getStatus());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}