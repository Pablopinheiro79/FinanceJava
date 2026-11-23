package com.financejava;

import java.math.BigDecimal;

// Representa uma receita ou despesa com descrição e valor monetário positivo.
public class Transacao {

    private String descricao;
    private BigDecimal valor;
    private String tipo;

    // Lança IllegalArgumentException se a descrição, o valor ou o tipo for inválido.
    public Transacao(String descricao, BigDecimal valor, String tipo) {

        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A descrição não pode ser vazia."
            );
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor deve ser maior que zero."
            );
        }

        if (!tipoValido(tipo)) {
            throw new IllegalArgumentException(
                    "O tipo deve ser Receita ou Despesa."
            );
        }
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
    }

    private boolean tipoValido(String tipo) {
        return "Receita".equalsIgnoreCase(tipo)
                || "Despesa".equalsIgnoreCase(tipo);
    }

    public BigDecimal getValor() {
        return valor;
    }

    // Atualiza o valor se ele for positivo e retorna false quando a entrada é inválida.
    public boolean setValor(BigDecimal valor) {

    if (valor != null && valor.compareTo(BigDecimal.ZERO) > 0) {
        this.valor = valor;
        return true;
    }

    return false;
}

    public String getDescricao() {
        return descricao;
    }

    // Rejeita null, texto vazio ou apenas espaços antes de atualizar a descrição.
    public boolean setDescricao(String descricao) {
        if (descricao != null && !descricao.trim().isEmpty()) {
            this.descricao = descricao;
            return true;
        }
        return false;
    }

    public String getTipo() {
        return tipo;
    }

    // Aceita Receita ou Despesa em qualquer combinação de maiúsculas e minúsculas.
    public boolean setTipo(String tipo) {

        if (tipoValido(tipo)) {
            this.tipo = tipo;
            return true;
        }

        return false;
    }

    // Indica se o valor da movimentação deve ser somado ao saldo.
    public boolean isReceita() {
        return "Receita".equalsIgnoreCase(tipo);
    }

    // Indica se a movimentação deve ser subtraída do saldo.
    public boolean isDespesa() {
        return "Despesa".equalsIgnoreCase(tipo);
    }
}
