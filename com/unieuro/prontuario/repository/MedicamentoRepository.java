package com.unieuro.prontuario.repository;

import com.unieuro.prontuario.model.Medicamento;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicamentoRepository {

    private final List<Medicamento> medicamentos = new ArrayList<>();
    private Long currentId = 1L;

    public List<Medicamento> findAll() {
        return new ArrayList<>(medicamentos);
    }

    public Optional<Medicamento> findById(Long id) {
        return medicamentos.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public Medicamento save(Medicamento medicamento) {
        if (medicamento.getId() == null) {
            medicamento.setId(currentId++);
            medicamentos.add(medicamento);
        } else {
            medicamentos.removeIf(m -> m.getId().equals(medicamento.getId()));
            medicamentos.add(medicamento);
        }
        return medicamento;
    }

    public boolean deleteById(Long id) {
        return medicamentos.removeIf(m -> m.getId().equals(id));
    }
}