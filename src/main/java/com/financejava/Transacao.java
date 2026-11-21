package com.financejava;

import java.math.BigDecimal;

public class Transacao {

    // Os atributos privados guardam os dados; os métodos controlam seu acesso.
    private String descricao;
    private BigDecimal valor;
    private String tipo;

    // Valida a descrição, o valor e o tipo antes de preencher os atributos.
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

    // Getter: consulta o valor sem modificá-lo.
    public BigDecimal getValor() {
        return valor;
    }

    // Aceita um valor não nulo e maior que zero. Retorna true quando aceita o valor.
    public boolean setValor(BigDecimal valor) {

    if (valor != null && valor.compareTo(BigDecimal.ZERO) > 0) {
        this.valor = valor;
        return true;
    }

    return false;
}

    // Retorna o texto que identifica a movimentação.
    public String getDescricao() {
        return descricao;
    }

    // Rejeita null, texto vazio ou apenas espaços antes de atualizar a descrição.
    public boolean setDescricao(String descricao) {
        if (descricao != null && !descricao.trim().isEmpty()) {
            this.descricao = descricao;
            return true;
        }
        // A validação falhou: mantém o dado anterior e informa a rejeição.
        return false;
    }

    // Retorna o tipo usado no cálculo do saldo.
    public String getTipo() {
        return tipo;
    }

    // Atualiza o tipo somente se for Receita ou Despesa, sem distinguir maiúsculas de minúsculas.
    public boolean setTipo(String tipo) {

        if (tipoValido(tipo)) {
            this.tipo = tipo;
            return true;
        }

        return false;
    }

    // Compara sem diferenciar maiúsculas e minúsculas; a constante evita erro se tipo for null.
    public boolean isReceita() {
        return "Receita".equalsIgnoreCase(tipo);
    }

    // Indica se a movimentação deve ser subtraída do saldo.
    public boolean isDespesa() {
        return "Despesa".equalsIgnoreCase(tipo);
    }
}
