
// Representa uma movimentação financeira individual: receita ou despesa.
public class Transacao {

    // Os atributos privados guardam os dados; os métodos controlam seu acesso.
    private String descricao;
    private double valor;
    private String tipo;

    // Construtor: recebe os dados usados para criar um novo objeto com new.
    public Transacao(String descricao, double valor, String tipo) {
        // this.descricao é o atributo do objeto; descricao é o parâmetro recebido.
        this.descricao = descricao;
        // Uma exceção interrompe a criação do objeto se o valor for inválido.
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new IllegalArgumentException("O valor deve ser finito e maior que zero.");
        }
        this.valor = valor;
        this.tipo = tipo;
    }

    // Getter: consulta o valor sem modificá-lo.
    public double getValor() {
        return valor;
    }

    // Setter: atualiza somente um valor finito e positivo; retorna se houve alteração.
    public boolean setValor(double valor) {
        if (Double.isFinite(valor) && valor > 0) {
            this.valor = valor;
            return true;
        }
        // A validação falhou: mantém o dado anterior e informa a rejeição.
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

    // Substitui o tipo diretamente; este método não valida o texto recebido.
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
