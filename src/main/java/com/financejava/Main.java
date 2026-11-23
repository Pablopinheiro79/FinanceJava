package com.financejava;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    // Exibe as operações disponíveis no terminal.
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

    // Valida a descrição e o valor digitados antes de cadastrar uma receita ou despesa.
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
        BigDecimal valor;
        try {
            String entrada = scanner.nextLine();

            // Converte a vírgula decimal em ponto para criar o BigDecimal.
            entrada = entrada.replace(",", ".");
            valor = new BigDecimal(entrada);

        } catch (NumberFormatException e) {
            System.out.println("ERRO! Digite um valor numérico válido.");
            return;
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("ERRO! O valor deve ser maior que zero.");
            return;
        }

        // Cria o objeto e o adiciona à conta somente depois das validações.
        Transacao novaTransacao = new Transacao(descricao, valor, tipo);
        conta.adicionarTransacao(novaTransacao);
        System.out.println(tipo + " cadastrada com sucesso!");
    }

    // Lê o número exibido no menu e delega à conta a validação do índice e a remoção.
    public static void excluirTransacao(Conta conta, Scanner scanner) {
        System.out.println("EXCLUIR TRANSAÇÃO");
        if (conta.getQuantidadeTransacoes() == 0) {
            System.out.println("Não existem transações para excluir.");
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
            return;
        }

        // Subtrai 1 do número exibido para obter o índice da lista.
        int indice = numero - 1;

        conta.excluirTransacao(indice);
    }

    // Seleciona uma transação pelo número exibido e permite editar seu valor ou sua descrição.
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
            // Consome o restante da linha após a leitura do inteiro.
            scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: digite um número inteiro!");
            // Descarta a linha com a entrada inválida.
            scanner.nextLine();
            return;
        }

        // Subtrai 1 do número exibido para obter o índice da lista.
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
            tipoEdicao = scanner.nextInt();
            // Consome o restante da linha após a leitura do inteiro.
            scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: digite um número inteiro!");
            // Descarta a linha com a entrada inválida.
            scanner.nextLine();
            return;
        }

        if (tipoEdicao == 1) {
            System.out.print("Digite o novo valor da transação: ");
            BigDecimal novoValor;

            try {
                String entrada = scanner.nextLine();

                entrada = entrada.replace(",", ".");

                novoValor = new BigDecimal(entrada);

            } catch (NumberFormatException e) {
                System.out.println("Erro: digite um valor numérico válido!");
                return;
            }

            if (novoValor.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Erro: o valor deve ser maior que zero!");
                return;
            }
            // A conta aplica a alteração e informa o resultado no terminal.
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

    // Carrega as transações do CSV e executa o menu até a opção de saída.
    public static void main(String[] args) {

        // Apresenta o sistema antes de iniciar o menu.
        System.out.println("--------------------------------");
        System.out.println("Bem-vindo ao FinanceJava!");
        System.out.println("Sistema de Controle de Finanças Pessoais de Pablo");
        System.out.println("Desenvolvido em Java com POO");
        System.out.println("--------------------------------");

        // Carrega o CSV na conta compartilhada pelas operações do menu.
        Conta minhaConta = new Conta();
        boolean carregamentoOK
                = ArquivoTransacoes.carregarTransacoes(minhaConta);

        // Encerra antes do menu se a leitura falhar, evitando sobrescrever o CSV na saída.
        if (!carregamentoOK) {
            System.out.println(
                    "Erro no carregamento. Programa encerrado para proteger os dados."
            );
            return;
        }

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
                    // O break devolve o controle ao laço do menu após a edição.
                    break;
                }

                // Tenta salvar as transações no CSV antes de encerrar o programa.
                case 0:
                    ArquivoTransacoes.salvarTransacoes(minhaConta);
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
