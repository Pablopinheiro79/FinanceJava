
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Apresentação
        System.out.println("--------------------------------");
        System.out.println("Bem-vindo ao FinanceJava!");
        System.out.println("Sistema de Controle de Finanças Pessoais de Pablo");
        System.out.println("Desenvolvido em Java com POO");
        System.out.println("--------------------------------");

        Conta minhaConta = new Conta(); // criando a conta do usuário

        NumberFormat moeda = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
        );

        // criando a função scanner para o menu interativo
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("====== MENU PRINCIPAL ======");
            System.out.println("1 - Cadastrar receita");
            System.out.println("2 - Cadastrar despesa");
            System.out.println("3 - Listar transações");
            System.out.println("4 - Consultar saldo");
            System.out.println("5 - Excluir transação");
            System.out.println("0 - Sair");

            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

            } catch (java.util.InputMismatchException e) {
                System.out.println("ERRO! Digite apenas números.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1: {
                    System.out.println("=== CADASTRAR RECEITA ===");

                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();
                    if (descricao.trim().isEmpty()) {
                        System.out.println("ERRO! A descrição não pode ser vazia");
                        break;
                    }
                    System.out.print("Valor: ");
                    double valor;
                    try {
                        valor = scanner.nextDouble();
                        scanner.nextLine();

                    } catch (java.util.InputMismatchException e) {
                        System.out.println("ERRO! Digite um valor numérico.");
                        scanner.nextLine();
                        break;
                    }
                    if (valor <= 0) {
                        System.out.println("ERRO! O valor deve ser maior que zero");
                        break;
                    }

                    Transacao novaReceita = new Transacao(
                            descricao, valor, "Receita"
                    );

                    minhaConta.adicionarTransacao(novaReceita);

                    System.out.println("Receita cadastrada com sucesso!");
                    break;
                }

                case 2: {
                    System.out.println("=== CADASTRAR DESPESA ===");

                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();
                    if (descricao.trim().isEmpty()) {
                        System.out.println("ERRO! A descrição não pode ser vazia");
                        break;
                    }

                    System.out.print("Valor: ");
                    double valor;
                    try {
                        valor = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("ERRO! Digite um valor numérico.");
                        scanner.nextLine();
                        break;
                    }
                    if (valor <= 0) {
                        System.out.println("ERRO! O valor deve ser maior que zero");
                        break;
                    }

                    Transacao novaDespesa = new Transacao(
                            descricao, valor, "Despesa"
                    );

                    minhaConta.adicionarTransacao(novaDespesa);

                    System.out.println("Despesa cadastrada com sucesso!");
                    break;
                }

                case 3:
                    System.out.println("LISTA DE TRANSAÇÕES:");
                    minhaConta.listarTransacoes();
                    break;

                case 4:
                    System.out.println("SALDO DA CONTA:");
                    System.out.println("R$ " + minhaConta.calcularSaldo());
                    break;

                case 5: {
                    System.out.println("EXCLUIR TRANSAÇÃO");
                    if (minhaConta.getQuantidadeTransacoes() == 0) {
                        System.out.println("Não existem transações para excluir.");
                        break;
                    }

                    minhaConta.listarTransacoes();

                    System.out.print("Digite o número da transação que deseja excluir: ");
                    int numero;

                    try {
                        numero = scanner.nextInt();
                        scanner.nextLine();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Erro: digite um número inteiro!");
                        scanner.nextLine();
                        break;
                    }

                    int indice = numero - 1;

                    minhaConta.excluirTransacao(indice);

                    break;
                }

                case 0:
                    System.out.println("Encerrando FinanceJava...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        }
        scanner.close();
    }
}
