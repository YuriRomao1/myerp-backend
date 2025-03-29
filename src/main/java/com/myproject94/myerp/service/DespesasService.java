package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.Despesas;
import com.myproject94.myerp.repositories.DespesasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesasService {

    private final DespesasRepository repository;

    public List<Despesas> findAll() {
        return repository.findAll();
    }

    public Despesas findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada! ID: " + id));
    }

    public Despesas create(Despesas despesa) {
        return repository.save(despesa);
    }

    public Despesas update(Integer id, Despesas updatedDespesa) {
        Despesas existing = findById(id);
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