
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Conta {

    private ArrayList<Transacao> transacoes;

    public Conta() {
        transacoes = new ArrayList<>();
    }

    // adicionar transação
    public void adicionarTransacao(Transacao t) {
        transacoes.add(t);
    }

    public void excluirTransacao(int indice) {
        if (indice >= 0 && indice < transacoes.size()) {
            transacoes.remove(indice);
            System.out.println("Transação excluída com sucesso!");
        } else {
            System.out.println("Índice inválido!");
        }
    }

    public double calcularSaldo() {
        double saldo = 0;
        for (Transacao t : transacoes) {
            if (t.isReceita()) {
                saldo += t.getValor();
            } else if (t.isDespesa()) {
                saldo -= t.getValor();
            }
        }
        return saldo;
    }

    public void listarTransacoes() {
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada.");
            return;
        }
        NumberFormat moeda = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
        );
        for (int i = 0; i < transacoes.size(); i++) {
            Transacao t = transacoes.get(i);
            String sinal;
            if (t.isReceita()) {
                sinal = "+";
            } else {
                sinal = "-";
            }
            System.out.println((i + 1) + " - " +
                    t.getDescricao() + " - "
                    + t.getTipo() + " - "
                    + sinal + " " + moeda.format(t.getValor())
            );
        }
    }

    public int getQuantidadeTransacoes() {
        return transacoes.size();
    }

}
