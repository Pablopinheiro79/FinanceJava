package com.financejava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

// Lê e grava transações em CSV UTF-8 com campos separados por ponto e vírgula.
public class ArquivoTransacoes {

    private static final Path ARQUIVO_EXEMPLO = Path.of("exemplo.csv");
    private static final Path ARQUIVO_TRANSACOES = Path.of("transacoes.csv");
    private static final Path ARQUIVO_TEMPORARIO
            = Path.of("transacoes_temp.csv");
    private static final String CABECALHO = "descricao;valor;tipo";

    // Separa os campos fora das aspas e converte pares de aspas em uma aspa literal.
    private static String[] separarCamposCSV(String linha) {

        List<String> campos = new ArrayList<>();
        StringBuilder campo = new StringBuilder();

        boolean dentroDeAspas = false;

        for (int i = 0; i < linha.length(); i++) {

            char caractere = linha.charAt(i);

            if (caractere == '"') {

                if (dentroDeAspas
                        && i + 1 < linha.length()
                        && linha.charAt(i + 1) == '"') {

                    campo.append('"');
                    i++;

                } else {
                    dentroDeAspas = !dentroDeAspas;
                }

            } else if (caractere == ';' && !dentroDeAspas) {

                campos.add(campo.toString());
                campo.setLength(0);

            } else {
                campo.append(caractere);
            }
        }

        if (dentroDeAspas) {
            throw new IllegalArgumentException(
                    "Campo CSV com aspas não fechadas."
            );
        }

        campos.add(campo.toString());

        return campos.toArray(new String[0]);
    }

    // Um campo entre aspas pode continuar nas próximas linhas do arquivo.
    private static boolean registroCSVCompleto(String texto) {

        boolean dentroDeAspas = false;

        for (int i = 0; i < texto.length(); i++) {

            if (texto.charAt(i) == '"') {

                if (dentroDeAspas
                        && i + 1 < texto.length()
                        && texto.charAt(i + 1) == '"') {

                    i++;

                } else {
                    dentroDeAspas = !dentroDeAspas;
                }
            }
        }

        return !dentroDeAspas;
    }

    // Cria ou sobrescreve o CSV de exemplo com duas transações fictícias.
    public static void salvarExemplo() {
        // Usa UTF-8 para preservar os acentos e fecha o escritor automaticamente.
        try (BufferedWriter escritor = Files.newBufferedWriter(ARQUIVO_EXEMPLO, StandardCharsets.UTF_8)) {
            // Grava o cabeçalho e os registros com campos separados por ponto e vírgula.
            escritor.write(CABECALHO);
            escritor.newLine();
            escritor.write("Salário;5000.00;Receita");
            escritor.newLine();
            escritor.write("Mercado;250.50;Despesa");
            escritor.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
            // Encerra o método para evitar a mensagem de sucesso após uma falha.
            return;
        }
        System.out.println("Arquivo de exemplo salvo com sucesso!");
    }

    // Grava a conta em um arquivo temporário e tenta substituir o CSV com um movimento atômico.
    public static void salvarTransacoes(Conta conta) {
        try (BufferedWriter escritor = Files.newBufferedWriter(ARQUIVO_TEMPORARIO, StandardCharsets.UTF_8)) {
            escritor.write(CABECALHO);
            escritor.newLine();

            for (Transacao transacao : conta.getTransacoes()) {
                // Grava o BigDecimal com ponto decimal e sem notação científica.
                escritor.write(escaparDescricao(transacao.getDescricao()) + ";"
                        + transacao.getValor().toPlainString() + ";"
                        + transacao.getTipo());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
            return;
        }
        try {
            Files.move(
                    ARQUIVO_TEMPORARIO,
                    ARQUIVO_TRANSACOES,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {
            System.out.println(
                    "Erro ao substituir o arquivo: " + e.getMessage()
            );
            return;
        }
        System.out.println("Transações salvas com sucesso!");
    }

    // Protege descrições com separador ou quebra de linha e duplica aspas internas.
    private static String escaparDescricao(String descricao) {
        if (descricao.contains(";") || descricao.contains("\"")
                || descricao.contains("\n") || descricao.contains("\r")) {
            return "\"" + descricao.replace("\"", "\"\"") + "\"";
        }
        return descricao;
    }

    // Exibe o conteúdo de exemplo.csv linha por linha no terminal.
    public static void lerExemplo() {
        try (BufferedReader leitor = Files.newBufferedReader(ARQUIVO_EXEMPLO, StandardCharsets.UTF_8)) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    // Acrescenta os registros à conta depois de ler e validar todos eles.
    // Um arquivo ausente é aceito. Erros de leitura ou de conteúdo retornam false.
    public static boolean carregarTransacoes(Conta conta) {

        try (BufferedReader leitor
                = Files.newBufferedReader(
                        ARQUIVO_TRANSACOES,
                        StandardCharsets.UTF_8
                )) {

                    // Ignora a primeira linha, que contém o cabeçalho.
                    // Lê e valida o cabeçalho do arquivo CSV.
                    String cabecalhoLido = leitor.readLine();

                    if (!CABECALHO.equals(cabecalhoLido)) {
                        System.out.println("Erro: cabeçalho CSV inválido.");
                        return false;
                    }

                    String linha;
                    List<Transacao> transacoesLidas = new ArrayList<>();

                    while ((linha = leitor.readLine()) != null) {

                        StringBuilder registro = new StringBuilder(linha);

                        while (!registroCSVCompleto(registro.toString())) {

                            String proximaLinha = leitor.readLine();

                            if (proximaLinha == null) {
                                System.out.println("Registro CSV incompleto.");
                                return false;
                            }

                            registro.append("\n").append(proximaLinha);
                        }

                        // O registro pode reunir várias linhas e deve conter descrição, valor e tipo.
                        String[] dados;

                        try {
                            dados = separarCamposCSV(registro.toString());

                        } catch (IllegalArgumentException e) {
                            System.out.println(
                                    "Linha CSV inválida: " + e.getMessage()
                            );
                            return false;
                        }

                        if (dados.length != 3) {
                            System.out.println("Linha inválida ignorada.");
                            return false;
                        }

                        try {
                            String descricao = dados[0];
                            BigDecimal valor = new BigDecimal(dados[1]);
                            String tipo = dados[2];

                            Transacao transacao
                                    = new Transacao(descricao, valor, tipo);

                            transacoesLidas.add(transacao);

                        } catch (IllegalArgumentException e) {
                            System.out.println(
                                    "Erro ao carregar transação: " + e.getMessage()
                            );
                            return false;
                        }
                    }

                    for (Transacao transacao : transacoesLidas) {
                        conta.adicionarTransacao(transacao);
                    }
                    System.out.println("Transações carregadas com sucesso!");

                } catch (java.nio.file.NoSuchFileException e) {

                    System.out.println(
                            "Arquivo ainda não existe. A conta começará vazia."
                    );
                    return true;

                } catch (IOException e) {

                    System.out.println(
                            "Erro ao carregar transações: " + e.getMessage()
                    );
                    return false;
                }
                return true;
    }
}
