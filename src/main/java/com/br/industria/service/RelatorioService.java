package com.br.industria.service;

import com.br.industria.domain.Funcionario;
import com.br.industria.util.FormatadorUtil;

import java.util.List;
import java.util.Map;

public class RelatorioService {
    private final FuncionarioService funcionarioService;

    public RelatorioService(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public void imprimirListaFuncionarios(List<Funcionario> funcionarios) {
        System.out.println("\n=== Lista de Funcionários ===");
        funcionarios.forEach(this::imprimirFuncionario);
    }

    public void imprimirFuncionario(Funcionario funcionario) {
        System.out.println(
                "Nome: " + funcionario.getNome() +
                        ", Data Nascimento: " + FormatadorUtil.formatarData(funcionario.getDataNascimento()) +
                        ", Salário: R$ " + FormatadorUtil.formatarValor(funcionario.getSalario()) +
                        ", Função: " + funcionario.getFuncao()
        );
    }

    public void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        System.out.println("\n=== Funcionários Agrupados por Função ===");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(f -> System.out.println("  - " + f.getNome() +
                    ", Salário: R$ " + FormatadorUtil.formatarValor(f.getSalario())));
        });
    }

    public void imprimirAniversariantes(List<Funcionario> aniversariantes, int... meses) {
        StringBuilder mesesStr = new StringBuilder();
        for (int i = 0; i < meses.length; i++) {
            mesesStr.append(meses[i]);
            if (i < meses.length - 1) {
                mesesStr.append(" e ");
            }
        }

        System.out.println("\n=== Aniversariantes do mês " + mesesStr + " ===");
        if (aniversariantes.isEmpty()) {
            System.out.println("Nenhum aniversariante encontrado para o(s) mês(es) selecionado(s)");
        } else {
            aniversariantes.forEach(f -> System.out.println(
                    "Nome: " + f.getNome() +
                            ", Data Nascimento: " + FormatadorUtil.formatarData(f.getDataNascimento())
            ));
        }
    }

    public void imprimirFuncionarioMaiorIdade(Funcionario funcionario) {
        int idade = funcionarioService.calcularIdade(funcionario);
        System.out.println("\n=== Funcionário com Maior Idade ===");
        System.out.println("Nome: " + funcionario.getNome() + ", Idade: " + idade + " anos");
    }

    public void imprimirTotalSalarios() {
        System.out.println("\n=== Total dos Salários ===");
        System.out.println("Total: R$ " +
                FormatadorUtil.formatarValor(funcionarioService.calcularTotalSalarios()));
    }

    public void imprimirSalariosMinimos() {
        System.out.println("\n=== Quantidade de Salários Mínimos por Funcionário ===");
        funcionarioService.listarTodos().forEach(f -> {
            System.out.println(
                    "Nome: " + f.getNome() +
                            ", Salários Mínimos: " +
                            FormatadorUtil.formatarValor(funcionarioService.calcularSalariosMinimos(f))
            );
        });
    }
}

