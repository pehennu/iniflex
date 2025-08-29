package com.br.industria.service;

import com.br.industria.domain.Funcionario;
import com.br.industria.repository.FuncionarioRepository;
import com.br.industria.util.FormatadorUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RelatorioServiceTest {

    @Mock
    private FuncionarioService funcionarioService;

    @InjectMocks
    private RelatorioService relatorioService;

    private Funcionario maria;
    private Funcionario joao;
    private Funcionario caio;
    private Funcionario helena;
    private List<Funcionario> funcionarios;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        maria = new Funcionario("Maria",
                LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"),
                "Operador");

        joao = new Funcionario("João",
                LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"),
                "Operador");

        caio = new Funcionario("Caio",
                LocalDate.of(1961, 5, 2),
                new BigDecimal("9836.14"),
                "Coordenador");

        helena = new Funcionario("Helena",
                LocalDate.of(1962, 9, 2),
                new BigDecimal("19119.88"),
                "Diretor");

        funcionarios = Arrays.asList(maria, joao, caio, helena);

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreSystemOutput() {
        System.setOut(originalOut);
    }

    @Test
    void testImprimirListaFuncionarios() {
        relatorioService.imprimirListaFuncionarios(funcionarios);

        String output = outContent.toString();
        assertTrue(output.contains("=== Lista de Funcionários ==="));
        assertTrue(output.contains("Nome: Maria"));
        assertTrue(output.contains("Nome: João"));
        assertTrue(output.contains("Nome: Caio"));
        assertTrue(output.contains("Nome: Helena"));
    }

    @Test
    void testImprimirFuncionario() {
        relatorioService.imprimirFuncionario(maria);

        String output = outContent.toString();
        assertTrue(output.contains("Nome: Maria"));
        assertTrue(output.contains("Data Nascimento: " + FormatadorUtil.formatarData(maria.getDataNascimento())));
        assertTrue(output.contains("Salário: R$ " + FormatadorUtil.formatarValor(maria.getSalario())));
        assertTrue(output.contains("Função: Operador"));
    }

    @Test
    void testImprimirFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        funcionariosPorFuncao.put("Operador", Arrays.asList(maria, joao));
        funcionariosPorFuncao.put("Coordenador", Collections.singletonList(caio));
        funcionariosPorFuncao.put("Diretor", Collections.singletonList(helena));

        relatorioService.imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        String output = outContent.toString();
        assertTrue(output.contains("=== Funcionários Agrupados por Função ==="));
        assertTrue(output.contains("Função: Operador"));
        assertTrue(output.contains("  - Maria"));
        assertTrue(output.contains("  - João"));
        assertTrue(output.contains("Função: Coordenador"));
        assertTrue(output.contains("  - Caio"));
        assertTrue(output.contains("Função: Diretor"));
        assertTrue(output.contains("  - Helena"));
    }

    @Test
    void testImprimirAniversariantesComResultados() {
        List<Funcionario> aniversariantes = Arrays.asList(maria, joao);

        relatorioService.imprimirAniversariantes(aniversariantes, 10, 5);

        String output = outContent.toString();
        assertTrue(output.contains("=== Aniversariantes do mês 10 e 5 ==="));
        assertTrue(output.contains("Nome: Maria"));
        assertTrue(output.contains("Nome: João"));
    }

    @Test
    void testImprimirAniversariantesSemResultados() {
        List<Funcionario> aniversariantes = Collections.emptyList();

        relatorioService.imprimirAniversariantes(aniversariantes, 1, 2);

        String output = outContent.toString();
        assertTrue(output.contains("=== Aniversariantes do mês 1 e 2 ==="));
        assertTrue(output.contains("Nenhum aniversariante encontrado para o(s) mês(es) selecionado(s)"));
    }

    @Test
    void testImprimirFuncionarioMaiorIdade() {
        when(funcionarioService.calcularIdade(caio)).thenReturn(62);

        relatorioService.imprimirFuncionarioMaiorIdade(caio);

        String output = outContent.toString();
        assertTrue(output.contains("=== Funcionário com Maior Idade ==="));
        assertTrue(output.contains("Nome: Caio, Idade: 62 anos"));

        verify(funcionarioService).calcularIdade(caio);
    }

    @Test
    void testImprimirTotalSalarios() {
        when(funcionarioService.calcularTotalSalarios()).thenReturn(new BigDecimal("33249.84"));

        relatorioService.imprimirTotalSalarios();

        String output = outContent.toString();
        assertTrue(output.contains("=== Total dos Salários ==="));
        assertTrue(output.contains("Total: R$ " + FormatadorUtil.formatarValor(new BigDecimal("33249.84"))));

        verify(funcionarioService).calcularTotalSalarios();
    }

    @Test
    void testImprimirSalariosMinimos() {
        when(funcionarioService.listarTodos()).thenReturn(funcionarios);
        when(funcionarioService.calcularSalariosMinimos(maria)).thenReturn(new BigDecimal("1.66"));
        when(funcionarioService.calcularSalariosMinimos(joao)).thenReturn(new BigDecimal("1.88"));
        when(funcionarioService.calcularSalariosMinimos(caio)).thenReturn(new BigDecimal("8.12"));
        when(funcionarioService.calcularSalariosMinimos(helena)).thenReturn(new BigDecimal("15.78"));

        relatorioService.imprimirSalariosMinimos();

        String output = outContent.toString();
        assertTrue(output.contains("=== Quantidade de Salários Mínimos por Funcionário ==="));
        assertTrue(output.contains("Nome: Maria, Salários Mínimos: " +
                FormatadorUtil.formatarValor(new BigDecimal("1.66"))));
        assertTrue(output.contains("Nome: João, Salários Mínimos: " +
                FormatadorUtil.formatarValor(new BigDecimal("1.88"))));
        assertTrue(output.contains("Nome: Caio, Salários Mínimos: " +
                FormatadorUtil.formatarValor(new BigDecimal("8.12"))));
        assertTrue(output.contains("Nome: Helena, Salários Mínimos: " +
                FormatadorUtil.formatarValor(new BigDecimal("15.78"))));

        verify(funcionarioService).listarTodos();
        verify(funcionarioService).calcularSalariosMinimos(maria);
        verify(funcionarioService).calcularSalariosMinimos(joao);
        verify(funcionarioService).calcularSalariosMinimos(caio);
        verify(funcionarioService).calcularSalariosMinimos(helena);
    }
}
