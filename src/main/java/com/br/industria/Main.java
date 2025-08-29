package com.br.industria;

import com.br.industria.domain.Funcionario;
import com.br.industria.repository.FuncionarioRepository;
import com.br.industria.service.FuncionarioService;
import com.br.industria.service.RelatorioService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Main {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        FuncionarioRepository repository = new FuncionarioRepository();
        FuncionarioService funcionarioService = new FuncionarioService(repository);
        RelatorioService relatorioService = new RelatorioService(funcionarioService);

        cadastrarFuncionarios(funcionarioService);

        try {
            funcionarioService.removerFuncionario("João");
            System.out.println("Funcionário João removido com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao remover funcionário: " + e.getMessage());
        }

        relatorioService.imprimirListaFuncionarios(funcionarioService.listarTodos());

        funcionarioService.aumentarSalarios();
        System.out.println("\n=== Após aumento de 10% ===");
        relatorioService.imprimirListaFuncionarios(funcionarioService.listarTodos());

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarioService.agruparPorFuncao();
        relatorioService.imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        relatorioService.imprimirAniversariantes(funcionarioService.buscarAniversariantesMes(10, 12), 10, 12);

        Funcionario maisVelho = funcionarioService.buscarFuncionarioMaiorIdade();
        relatorioService.imprimirFuncionarioMaiorIdade(maisVelho);

        System.out.println("\n=== Funcionários por Ordem Alfabética ===");
        relatorioService.imprimirListaFuncionarios(funcionarioService.listarPorOrdemAlfabetica());

        relatorioService.imprimirTotalSalarios();

        relatorioService.imprimirSalariosMinimos();
    }

    private static void cadastrarFuncionarios(FuncionarioService service) {
        service.cadastrarFuncionario(new Funcionario(
                "Maria",
                LocalDate.parse("18/10/2000", formatter),
                new BigDecimal("2009.44"),
                "Operador"
        ));

        service.cadastrarFuncionario(new Funcionario(
                "João",
                LocalDate.parse("12/05/1990", formatter),
                new BigDecimal("2284.38"),
                "Operador"
        ));

        service.cadastrarFuncionario(new Funcionario(
                "Caio",
                LocalDate.parse("02/05/1961", formatter),
                new BigDecimal("9836.14"),
                "Coordenador"
        ));

        service.cadastrarFuncionario(new Funcionario(
                "Miguel",
                LocalDate.parse("14/10/1988", formatter),
                new BigDecimal("19119.88"),
                "Diretor"
        ));

        service.cadastrarFuncionario(new Funcionario(
                "Alice",
                LocalDate.parse("05/01/1995", formatter),
                new BigDecimal("2234.68"),
                "Recepcionista"
        ));

        service.cadastrarFuncionario(new Funcionario(
                "Heitor",
                LocalDate.parse("19/11/1999", formatter),
                new BigDecimal("1582.72"),
                "Operador"
        ));

        service.cadastrarFuncionario(new Funcionario(
                "Arthur",
                LocalDate.parse("31/03/1993", formatter),
                new BigDecimal("4071.84"),
                "Contador"
        ));

        service.cadastrarFuncionario(new Funcionario(
                "Laura",
                LocalDate.parse("08/07/1994", formatter),
                new BigDecimal("3017.45"),
                "Gerente"
        ));

        service.cadastrarFuncionario(new Funcionario(
                "Heloísa",
                LocalDate.parse("24/05/2003", formatter),
                new BigDecimal("1606.85"),
                "Eletricista"
        ));

        service.cadastrarFuncionario(new Funcionario(
                "Helena",
                LocalDate.parse("02/09/1996", formatter),
                new BigDecimal("2799.93"),
                "Gerente"
        ));
    }

}