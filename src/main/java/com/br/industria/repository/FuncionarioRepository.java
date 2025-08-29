package com.br.industria.repository;

import com.br.industria.domain.Funcionario;
import com.br.industria.exception.FuncionarioNotFoundException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FuncionarioRepository {
    private final List<Funcionario> funcionarios;

    public FuncionarioRepository() {
        this.funcionarios = new ArrayList<>();
    }

    public void adicionar(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public void remover(String nome) {
        boolean removido = funcionarios.removeIf(f -> f.getNome().equals(nome));
        if (!removido) {
            throw new FuncionarioNotFoundException("Funcionário com nome " + nome + " não encontrado");
        }
    }

    public List<Funcionario> listarTodos() {
        return Collections.unmodifiableList(funcionarios);
    }

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public List<Funcionario> buscarAniversariantesMes(int... meses) {
        return funcionarios.stream()
                .filter(f -> {
                    int mesAniversario = f.getDataNascimento().getMonthValue();
                    for (int mes : meses) {
                        if (mesAniversario == mes) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public Funcionario buscarFuncionarioMaiorIdade() {
        return funcionarios.stream()
                .max(Comparator.comparing(f -> f.getDataNascimento()))
                .orElseThrow(() -> new FuncionarioNotFoundException("Nenhum funcionário encontrado"));
    }

    public List<Funcionario> listarPorOrdemAlfabetica() {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    public BigDecimal calcularTotalSalarios() {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

