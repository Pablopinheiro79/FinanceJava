
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

// Agrupa as transações e oferece operações de cadastro, alteração e consulta.
public class Conta {

    // private restringe o acesso à lista. final impede trocar a lista, mas permite alterar seus itens.
    private final ArrayList<Transacao> transacoes;

    // O construtor inicia cada nova conta com uma lista vazia.
    public Conta() {
        transacoes = new ArrayList<>();
    }

    // Recebe uma transação pronta; rejeita null para evitar erros nas consultas.
    public void adicionarTransacao(Transacao t) {
        if (t == null) {
            throw new IllegalArgumentException("A transação não pode ser nula.");
        }
        transacoes.add(t);
    }

    // Remove pela posição interna da lista; as posições seguintes são reorganizadas.
    public void excluirTransacao(int indice) {
        // Uma posição válida vai de 0 até size() - 1.
        if (indice >= 0 && indice < transacoes.size()) {
            transacoes.remove(indice);
            System.out.println("Transação excluída com sucesso!");
        } else {
            System.out.println("Índice inválido!");
        }
    }

    // Altera o valor de uma transação existente e informa se a validação permitiu a mudança.
    public void editarTransacao(int indice, double novoValor) {
        // Uma posição válida vai de 0 até size() - 1.
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

    // Altera somente a descrição da transação selecionada.
    public void editarDescricao(int indice, String novaDescricao) {
        // Uma posição válida vai de 0 até size() - 1.
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

    // Recalcula o saldo: soma receitas e subtrai despesas.
    public double calcularSaldo() {
        double saldo = 0;
        // Percorre cada objeto da lista; t representa a transação da vez.
        for (Transacao t : transacoes) {
            if (t.isReceita()) {
                saldo += t.getValor();
            } else if (t.isDespesa()) {
                saldo -= t.getValor();
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

}
