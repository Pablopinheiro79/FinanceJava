public class Transacao {
   private String descricao;
   private double valor;
   private String tipo;

   //Construtor
   public Transacao(String descricao, double valor, String tipo)  {
       this.descricao = descricao;
       this.valor = valor;
       this.tipo = tipo;       
   }

   //getters e setters
   public double getValor() {
        return valor;
   }
   public void setValor(double valor) {
        this.valor = valor;
   }

   public String getDescricao() {
        return descricao;
   }
   public void setDescricao(String descricao) {
        this.descricao = descricao;
   }

   public String getTipo() {
        return tipo;
   }
   public void setTipo(String tipo) {
        this.tipo = tipo;
   }
   public boolean isReceita() {
          return tipo.equalsIgnoreCase("Receita");
   }
   public boolean isDespesa() {
          return tipo.equalsIgnoreCase("Despesa");
   }
}




