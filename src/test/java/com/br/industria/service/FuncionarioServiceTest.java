package com.br.industria.service;

import com.br.industria.domain.Funcionario;
import com.br.industria.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository repository;

    private FuncionarioService service;
    private Funcionario maria;
    private Funcionario joao;

    @BeforeEach
    void setUp() {
        service = new FuncionarioService(repository);

        maria = new Funcionario("Maria",
                LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"),
                "Operador");

        joao = new Funcionario("João",
                LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"),
                "Operador");
    }

    @Test
    void deveCadastrarFuncionario() {
        service.cadastrarFuncionario(maria);

        verify(repository).adicionar(maria);
    }

    @Test
    void deveRemoverFuncionario() {
        service.removerFuncionario("João");

        verify(repository).remover("João");
    }

    @Test
    void deveListarTodos() {
        List<Funcionario> esperado = Arrays.asList(maria, joao);
        when(repository.listarTodos()).thenReturn(esperado);

        List<Funcionario> resultado = service.listarTodos();

        assertEquals(esperado, resultado);
        verify(repository).listarTodos();
    }

    @Test
    void deveAumentarSalarios() {
        List<Funcionario> funcionarios = Arrays.asList(maria, joao);
        when(repository.listarTodos()).thenReturn(funcionarios);

        BigDecimal salarioMariaEsperado = new BigDecimal("2210.38"); // 2009.44 + 10%
        BigDecimal salarioJoaoEsperado = new BigDecimal("2512.82"); // 2284.38 + 10%

        service.aumentarSalarios();

        assertEquals(salarioMariaEsperado.setScale(2, RoundingMode.HALF_UP),
                maria.getSalario().setScale(2, RoundingMode.HALF_UP));

        assertEquals(salarioJoaoEsperado.setScale(2, RoundingMode.HALF_UP),
                joao.getSalario().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void deveAgruparPorFuncao() {
        Map<String, List<Funcionario>> esperado = new HashMap<>();
        esperado.put("Operador", Arrays.asList(maria, joao));

        when(repository.agruparPorFuncao()).thenReturn(esperado);

        Map<String, List<Funcionario>> resultado = service.agruparPorFuncao();

        assertEquals(esperado, resultado);
        verify(repository).agruparPorFuncao();
    }

    @Test
    void deveCalcularIdade() {
        LocalDate hoje = LocalDate.now();
        int idadeEsperada = hoje.getYear() - maria.getDataNascimento().getYear();

        if (hoje.getMonthValue() < maria.getDataNascimento().getMonthValue() ||
                (hoje.getMonthValue() == maria.getDataNascimento().getMonthValue() &&
                        hoje.getDayOfMonth() < maria.getDataNascimento().getDayOfMonth())) {
            idadeEsperada--;
        }

        int idade = service.calcularIdade(maria);

        assertEquals(idadeEsperada, idade);
    }

    @Test
    void deveCalcularSalariosMinimos() {
        BigDecimal esperado = new BigDecimal("1.66");

        BigDecimal resultado = service.calcularSalariosMinimos(maria);

        assertEquals(esperado, resultado);
    }
}

