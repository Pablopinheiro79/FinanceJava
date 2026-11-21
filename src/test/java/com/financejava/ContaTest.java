package com.financejava;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaTest {

    private Conta conta;
    private PrintStream originalOut;
    private ByteArrayOutputStream output;

    @BeforeEach
    void configurarConta() {
        conta = new Conta();
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));
    }

    @AfterEach
    void restaurarSaida() {
        System.setOut(originalOut);
    }

    @Test
    void deveIniciarSemTransacoes() {
        assertEquals(0, conta.getQuantidadeTransacoes());
        assertEquals(BigDecimal.ZERO, conta.calcularSaldo());
    }

    @Test
    void deveAdicionarUmaTransacao() {
        conta.adicionarTransacao(
                new Transacao("Salário", new BigDecimal("2500.00"), "Receita"));

        assertEquals(1, conta.getQuantidadeTransacoes());
    }

    @Test
    void deveRejeitarTransacaoNula() {
        assertThrows(IllegalArgumentException.class,
                () -> conta.adicionarTransacao(null));
        assertEquals(0, conta.getQuantidadeTransacoes());
    }

    @Test
    void deveCalcularSaldoSomandoReceitasESubtraindoDespesas() {
        conta.adicionarTransacao(
                new Transacao("Salário", new BigDecimal("3000"), "Receita"));
        conta.adicionarTransacao(
                new Transacao("Aluguel", new BigDecimal("1200"), "Despesa"));
        conta.adicionarTransacao(
                new Transacao("Mercado", new BigDecimal("350.50"), "Despesa"));

        assertEquals(new BigDecimal("1449.50"), conta.calcularSaldo());
    }

    @Test
    void deveExcluirTransacaoEmIndiceValido() {
        conta.adicionarTransacao(
                new Transacao("Internet", new BigDecimal("100"), "Despesa"));
        conta.adicionarTransacao(
                new Transacao("Bônus", new BigDecimal("500"), "Receita"));

        conta.excluirTransacao(0);

        assertEquals(1, conta.getQuantidadeTransacoes());
        assertEquals(new BigDecimal("500"), conta.calcularSaldo());
    }

    @Test
    void naoDeveExcluirTransacaoEmIndiceInvalido() {
        conta.adicionarTransacao(
                new Transacao("Internet", new BigDecimal("100"), "Despesa"));

        conta.excluirTransacao(-1);
        conta.excluirTransacao(1);

        assertEquals(1, conta.getQuantidadeTransacoes());
        assertEquals(new BigDecimal("-100"), conta.calcularSaldo());
    }

    @Test
    void deveEditarValorDeTransacaoValida() {
        Transacao transacao = new Transacao(
                "Freelance", new BigDecimal("500"), "Receita");
        conta.adicionarTransacao(transacao);

        conta.editarTransacao(0, new BigDecimal("750"));

        assertEquals(new BigDecimal("750"), transacao.getValor());
        assertEquals(new BigDecimal("750"), conta.calcularSaldo());
    }

    @Test
    void naoDeveEditarValorInvalido() {
        Transacao transacao = new Transacao(
                "Freelance", new BigDecimal("500"), "Receita");
        conta.adicionarTransacao(transacao);

        conta.editarTransacao(0, BigDecimal.ZERO);
        conta.editarTransacao(0, null);

        assertEquals(new BigDecimal("500"), transacao.getValor());
    }

    @Test
    void deveEditarDescricaoDeTransacaoValida() {
        Transacao transacao = new Transacao(
                "Antiga", new BigDecimal("100"), "Despesa");
        conta.adicionarTransacao(transacao);

        conta.editarDescricao(0, "Nova descrição");

        assertEquals("Nova descrição", transacao.getDescricao());
    }

    @Test
    void naoDeveEditarDescricaoInvalida() {
        Transacao transacao = new Transacao(
                "Original", new BigDecimal("100"), "Despesa");
        conta.adicionarTransacao(transacao);

        conta.editarDescricao(0, " ");
        conta.editarDescricao(0, null);

        assertEquals("Original", transacao.getDescricao());
    }

    @Test
    void naoDeveEditarOuExcluirIndiceInexistente() {
        conta.editarTransacao(0, new BigDecimal("100"));
        conta.editarDescricao(0, "Nova");
        conta.excluirTransacao(0);

        assertEquals(0, conta.getQuantidadeTransacoes());
    }

    @Test
    void deveListarMensagemQuandoNaoExistemTransacoes() {
        conta.listarTransacoes();

        assertEquals("Nenhuma transação cadastrada.",
                output.toString().trim());
    }

    @Test
    void deveListarDescricaoTipoESinalDasTransacoes() {
        conta.adicionarTransacao(
                new Transacao("Salário", new BigDecimal("100"), "Receita"));
        conta.adicionarTransacao(
                new Transacao("Aluguel", new BigDecimal("50"), "Despesa"));
        output.reset();

        conta.listarTransacoes();

        String saida = output.toString();
        assertEquals(true, saida.contains("1 - Salário - Receita - +"));
        assertEquals(true, saida.contains("2 - Aluguel - Despesa - -"));
    }
}
