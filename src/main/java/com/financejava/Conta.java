package com.financejava;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

// Mantém as transações da conta em memória e calcula o saldo a partir delas.
public class Conta {

    

    // A lista mantém a ordem de inclusão usada na exibição e na exportação.
    private final ArrayList<Transacao> transacoes;

    // O construtor inicia cada nova conta com uma lista vazia.
    public Conta() {
        transacoes = new ArrayList<>();
    }

    // Adiciona uma transação ao fim da lista e lança uma exceção se ela for nula.
    public void adicionarTransacao(Transacao t) {
        if (t == null) {
            throw new IllegalArgumentException("A transação não pode ser nula.");
        }
        transacoes.add(t);
    }

    // Remove a transação pelo índice baseado em zero. Os itens seguintes recuam uma posição.
    public void excluirTransacao(int indice) {
        if (indice >= 0 && indice < transacoes.size()) {
            transacoes.remove(indice);
            System.out.println("Transação excluída com sucesso!");
        } else {
            System.out.println("Índice inválido!");
        }
    }

    // Solicita a alteração do valor e informa o resultado no terminal.
    public void editarTransacao(int indice, BigDecimal novoValor) {
        if (indice >= 0 && indice < transacoes.size()) {
            Transacao t = transacoes.get(indice);
            // O setter retorna true quando atualiza e false quando rejeita o valor.
            boolean alterou = t.setValor(novoValor);
            if (alterou) {
                System.out.println("Transação editada com sucesso!");
            } else {
                System.out.println("Erro: o valor informado é inválido!");
            }
        } else {
            System.out.println("Transação não encontrada!");
        }
    }

    // Solicita a alteração da descrição e informa o resultado no terminal.
    public void editarDescricao(int indice, String novaDescricao) {
        if (indice >= 0 && indice < transacoes.size()) {
            Transacao t = transacoes.get(indice);
            // A própria transação verifica se a nova descrição é válida.
            boolean alterou = t.setDescricao(novaDescricao);
            if (alterou) {
                System.out.println("Descrição editada com sucesso!");
            } else {
                System.out.println("Erro: a descrição não pode ser vazia.");
            }
        } else {
            System.out.println("Transação não encontrada!");
        }
    }

    // Soma os valores das receitas e subtrai os das despesas a cada consulta.
    public BigDecimal calcularSaldo() {
        BigDecimal saldo = BigDecimal.ZERO;
        // Cada soma ou subtração com BigDecimal retorna um novo saldo.
        for (Transacao t : transacoes) {
            if (t.isReceita()) {
                saldo = saldo.add(t.getValor());
            } else if (t.isDespesa()) {
                saldo = saldo.subtract(t.getValor());
            }
        }
        return saldo;
    }

    // Exibe número, descrição, tipo e valor de cada transação.
    public void listarTransacoes() {
        // Evita percorrer a lista vazia; return encerra esta consulta.
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada.");
            return;
        }
        // Aplica o padrão monetário brasileiro aos valores exibidos.
        NumberFormat moeda = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR"));
        // Usa o índice para exibir i + 1, que é o número escolhido pelo usuário no menu.
        for (int i = 0; i < transacoes.size(); i++) {
            Transacao t = transacoes.get(i);
            // O sinal indica entrada (+) ou saída (-); o valor armazenado é positivo.
            String sinal;
            if (t.isReceita()) {
                sinal = "+";
            } else {
                sinal = "-";
            }
            System.out.println((i + 1) + " - "
                    + t.getDescricao() + " - "
                    + t.getTipo() + " - "
                    + sinal + " " + moeda.format(t.getValor()));
        }
    }

    // Permite consultar o tamanho da lista sem expor a lista diretamente.
    public int getQuantidadeTransacoes() {
        return transacoes.size();
    }

    // Retorna uma cópia da lista com referências às mesmas transações.
    public ArrayList<Transacao> getTransacoes() {
    return new ArrayList<>(transacoes);
}

}
