package com.br.industria.service;

import com.br.industria.domain.Funcionario;
import com.br.industria.repository.FuncionarioRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

public class FuncionarioService {
    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("0.10");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
        repository.adicionar(funcionario);
    }

    public void removerFuncionario(String nome) {
        repository.remover(nome);
    }

    public List<Funcionario> listarTodos() {
        return repository.listarTodos();
    }

    public void aumentarSalarios() {
        List<Funcionario> funcionarios = repository.listarTodos();
        funcionarios.forEach(f -> {
            BigDecimal aumento = f.getSalario().multiply(PERCENTUAL_AUMENTO);
            f.setSalario(f.getSalario().add(aumento));
        });
    }

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return repository.agruparPorFuncao();
    }

    public List<Funcionario> buscarAniversariantesMes(int... meses) {
        return repository.buscarAniversariantesMes(meses);
    }

    public Funcionario buscarFuncionarioMaiorIdade() {
        return repository.buscarFuncionarioMaiorIdade();
    }

    public int calcularIdade(Funcionario funcionario) {
        return Period.between(
                funcionario.getDataNascimento(),
                LocalDate.now()
        ).getYears();
    }

    public List<Funcionario> listarPorOrdemAlfabetica() {
        return repository.listarPorOrdemAlfabetica();
    }

    public BigDecimal calcularTotalSalarios() {
        return repository.calcularTotalSalarios();
    }

    public BigDecimal calcularSalariosMinimos(Funcionario funcionario) {
        return funcionario.getSalario()
                .divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
    }
}

