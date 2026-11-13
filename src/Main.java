
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

// Controla a interação pelo terminal e encaminha as operações para a conta.
public class Main {

    // Mostra as opções disponíveis; a leitura da escolha acontece no main.
    public static void exibirMenu() {
        System.out.println("====== MENU PRINCIPAL ======");
        System.out.println("1 - Cadastrar receita");
        System.out.println("2 - Cadastrar despesa");
        System.out.println("3 - Listar transações");
        System.out.println("4 - Consultar saldo");
        System.out.println("5 - Excluir transação");
        System.out.println("6 - Editar transação");
        System.out.println("0 - Sair");
    }

    // Reutiliza o mesmo cadastro: tipo recebe "Receita" ou "Despesa" conforme o menu.
    public static void cadastrarTransacao(Conta conta, Scanner scanner, String tipo) {
        System.out.println("=== CADASTRAR " + tipo.toUpperCase(Locale.ROOT) + " ===");

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        // trim() permite identificar uma descrição formada apenas por espaços.
        if (descricao.trim().isEmpty()) {
            System.out.println("ERRO! A descrição não pode ser vazia.");
            return;
        }

        System.out.print("Valor: ");
        double valor;
        // Tenta ler o número; o catch trata uma entrada incompatível, como letras.
        try {
            valor = scanner.nextDouble();
            // Consome o restante da linha após o número ou descarta a entrada inválida.
            scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("ERRO! Digite um valor numérico.");
            // Consome o restante da linha após o número ou descarta a entrada inválida.
            scanner.nextLine();
            return;
        }

        // Rejeita NaN, infinito, zero e negativos; return encerra apenas este cadastro.
        if (!Double.isFinite(valor) || valor <= 0) {
            System.out.println("ERRO! O valor deve ser finito e maior que zero.");
            return;
        }

        // Cria o objeto e o adiciona à conta somente depois das validações.
        Transacao novaTransacao = new Transacao(descricao, valor, tipo);
        conta.adicionarTransacao(novaTransacao);
        System.out.println(tipo + " cadastrada com sucesso!");
    }

    // Lê a escolha do usuário; a classe Conta realiza a remoção e valida o índice.
    public static void excluirTransacao(Conta conta, Scanner scanner) {
        System.out.println("EXCLUIR TRANSAÇÃO");
        if (conta.getQuantidadeTransacoes() == 0) {
            System.out.println("Não existem transações para excluir.");
            // Encerra este método e devolve a execução ao case 5 do menu.
            return;
        }
        conta.listarTransacoes();

        System.out.print("Digite o número da transação que deseja excluir: ");
        int numero;

        try {
            numero = scanner.nextInt();
            scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: digite um número inteiro!");
            scanner.nextLine();
            // Sem um número válido, não tenta calcular o índice nem remover um item.
            return;
        }

        // A lista exibida começa em 1; os índices do ArrayList começam em 0.
        int indice = numero - 1;

        conta.excluirTransacao(indice);
    }

    // Usa a conta recebida como parâmetro; return cancela a edição se os dados forem inválidos.
    // conta recebe a mesma instância chamada minhaConta no main; scanner reutiliza o leitor do terminal.
    public static void editarTransacao(Conta conta, Scanner scanner) {
        System.out.println("EDITAR TRANSAÇÃO");
        // Sem registros, retorna ao chamador sem pedir um número de transação.
        if (conta.getQuantidadeTransacoes() == 0) {
            System.out.println("Não existem transações para editar.");
            return;
        }
        // Mostra os números que o usuário pode escolher para identificar a transação.
        conta.listarTransacoes();

        System.out.print("Digite o número da transação que deseja editar: ");
        int numero;

        try {
            // nextInt() exige um inteiro; letras ou números decimais são tratados no catch.
            numero = scanner.nextInt();
            // Consome o restante da linha, inclusive uma entrada inválida ao passar pelo catch.
            scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: digite um número inteiro!");
            // Consome o restante da linha, inclusive uma entrada inválida ao passar pelo catch.
            scanner.nextLine();
            return;
        }

        // A lista exibida começa em 1; os índices do ArrayList começam em 0.
        int indice = numero - 1;

        // Confirma que a posição existe antes de pedir os novos dados.
        if (indice < 0 || indice >= conta.getQuantidadeTransacoes()) {
            System.out.println("Transação não encontrada!");
            return;
        }

        System.out.println("O que deseja editar?");
        System.out.println("1 - Valor");
        System.out.println("2 - Descrição");
        System.out.print("Escolha uma opção: ");

        int tipoEdicao;

        try {
            // Guarda a escolha do campo: 1 altera o valor; 2 altera a descrição.
            tipoEdicao = scanner.nextInt();
            // Consome o restante da linha, inclusive uma entrada inválida ao passar pelo catch.
            scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: digite um número inteiro!");
            // Consome o restante da linha, inclusive uma entrada inválida ao passar pelo catch.
            scanner.nextLine();
            return;
        }

        // O valor e a descrição têm leituras e validações diferentes.
        if (tipoEdicao == 1) {
            System.out.print("Digite o novo valor da transação: ");
            double novoValor;

            try {
                // nextDouble() lê o novo valor numérico da movimentação.
                novoValor = scanner.nextDouble();
                // Consome o restante da linha, inclusive uma entrada inválida ao passar pelo catch.
                scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Erro: digite um valor numérico!");
                // Consome o restante da linha, inclusive uma entrada inválida ao passar pelo catch.
                scanner.nextLine();
                return;
            }

            // Impede substituir o valor atual por um número inválido.
            if (!Double.isFinite(novoValor) || novoValor <= 0) {
                System.out.println("ERRO! O valor deve ser um número positivo e válido.");
                return;
            }
            // A Conta localiza a transação; o setter valida o valor e a Conta informa o resultado.
            conta.editarTransacao(indice, novoValor);
        } else if (tipoEdicao == 2) {
            System.out.print("Digite a nova descrição: ");
            String novaDescricao = scanner.nextLine();

            // Mantém a descrição atual se o usuário não informar um texto válido.
            if (novaDescricao.trim().isEmpty()) {
                System.out.println("Erro: a descrição não pode ser vazia.");
                return;
            }

            // Altera apenas a descrição, preservando o valor e o tipo da movimentação.
            conta.editarDescricao(indice, novaDescricao);
        } else {
            // Uma escolha diferente de 1 ou 2 termina o método sem modificar a transação.
            System.out.println("Opção de edição inválida!");
        }
    }

    // Ponto de entrada: o Java começa a execução do programa por este método.
    public static void main(String[] args) {

        // Apresenta o sistema antes de iniciar o menu.
        System.out.println("--------------------------------");
        System.out.println("Bem-vindo ao FinanceJava!");
        System.out.println("Sistema de Controle de Finanças Pessoais de Pablo");
        System.out.println("Desenvolvido em Java com POO");
        System.out.println("--------------------------------");

        // A mesma conta é usada em todas as opções; os dados ficam apenas na memória.
        Conta minhaConta = new Conta();

        // Configura a apresentação do saldo em reais, com o padrão brasileiro.
        NumberFormat moeda = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR"));

        // Scanner é o objeto que lê os dados digitados no terminal.
        Scanner scanner = new Scanner(System.in);
        // O valor inicial permite entrar no laço; a opção 0 encerra o menu.
        int opcao = -1;

        // Repete o menu após cada operação até o usuário escolher sair.
        while (opcao != 0) {
            exibirMenu();

            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

            } catch (java.util.InputMismatchException e) {
                System.out.println("ERRO! Digite apenas números.");
                scanner.nextLine();
                continue;
            }

            // Executa o case escolhido; break encerra o switch e o laço pode continuar.
            switch (opcao) {
                // Receita e despesa compartilham o método, mudando apenas o tipo.
                case 1: {
                    cadastrarTransacao(minhaConta, scanner, "Receita");
                    break;
                }

                case 2: {
                    cadastrarTransacao(minhaConta, scanner, "Despesa");
                    break;
                }

                // Consulta as transações sem alterar os dados.
                case 3:
                    System.out.println("LISTA DE TRANSAÇÕES:");
                    minhaConta.listarTransacoes();
                    break;

                // Calcula o saldo e aplica a formatação monetária antes de exibi-lo.
                case 4:
                    System.out.println("SALDO DA CONTA:");
                    System.out.println(moeda.format(minhaConta.calcularSaldo()));
                    break;

                // Delega a leitura e a exclusão ao método criado para essa operação.
                case 5: {
                    excluirTransacao(minhaConta, scanner);
                    break;
                }

                // Edição: seleciona uma transação e depois escolhe o campo a alterar.
                case 6: {
                    // Passa a conta e o leitor para o método responsável pela edição.
                    editarTransacao(minhaConta, scanner);
                    // Ao voltar do método, encerra este case sem executar o case 0.
                    break;
                }

                // Após este case, opcao vale 0 e a condição do while encerra o laço.
                case 0:
                    System.out.println("Encerrando FinanceJava...");
                    break;

                // Trata números que não correspondem a nenhuma opção do menu.
                default:
                    System.out.println("Opção inválida!");
            }

        }
        // Libera o leitor ao terminar o programa; também fecha System.in.
        scanner.close();
    }
}
