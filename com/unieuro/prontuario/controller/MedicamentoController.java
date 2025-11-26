package com.unieuro.prontuario.controller;

import com.unieuro.prontuario.model.Medicamento;
import com.unieuro.prontuario.repository.MedicamentoRepository;
import java.util.List;
import java.util.Optional;

public class MedicamentoController {

    private final MedicamentoRepository repository = new MedicamentoRepository();

    public List<Medicamento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Medicamento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Medicamento criar(Medicamento medicamento) {
        return repository.save(medicamento);
    }

    public Optional<Medicamento> atualizar(Long id, Medicamento medicamento) {
        return repository.findById(id).map(existente -> {
            medicamento.setId(existente.getId());
            return repository.save(medicamento);
        });
    }

    public boolean deletar(Long id) {
        return repository.deleteById(id);
    }
}