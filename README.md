# FinanceJava

**Sistema de Controle de Finanças Pessoais desenvolvido em Java, utilizando Programação Orientada a Objetos (POO).**

O FinanceJava é uma aplicação de terminal que permite gerenciar receitas e despesas, consultar saldos e armazenar movimentações financeiras em arquivos CSV.

O projeto foi desenvolvido durante a graduação em Engenharia de Software, com o objetivo de aplicar conceitos de programação, organização de código e desenvolvimento de software.

## Funcionalidades

- Cadastrar receitas.
- Cadastrar despesas.
- Listar transações financeiras.
- Consultar o saldo da conta.
- Editar valores e descrições.
- Excluir transações.
- Validar os dados informados pelo usuário.
- Salvar transações em arquivos CSV.
- Recuperar automaticamente os dados ao iniciar.
- Tratar erros durante a leitura e gravação.
- Utilizar arquivos temporários para maior segurança no salvamento.

## Tecnologias utilizadas

- **Java:** linguagem principal.
- **POO:** classes, objetos, encapsulamento e métodos.
- **BigDecimal:** cálculos financeiros com precisão decimal.
- **ArrayList:** armazenamento das transações.
- **Java NIO:** leitura, gravação e gerenciamento de arquivos.
- **Maven:** organização e gerenciamento do projeto.
- **JUnit 5:** testes automatizados.
- **Git e GitHub:** versionamento e publicação do código.
- **Visual Studio Code:** ambiente de desenvolvimento.

## Estrutura do projeto

**Main.java**

Responsável pelo menu principal e pela interação com o usuário.

**Conta.java**

Gerencia as transações, calcula o saldo e executa operações de edição e exclusão.

**Transacao.java**

Representa uma movimentação financeira, contendo descrição, valor e tipo (Receita ou Despesa).

**ArquivoTransacoes.java**

Responsável por salvar, recuperar e validar os dados armazenados em CSV.

**ContaTest.java**

Contém os testes automatizados das operações da classe Conta.

## Armazenamento dos dados

As movimentações financeiras são armazenadas no arquivo `transacoes.csv`.

O programa carrega os registros ao iniciar e salva as alterações quando o usuário escolhe a opção de encerramento.

O salvamento utiliza um arquivo temporário para reduzir o risco de perda de dados durante a gravação.

Os arquivos de transações são ignorados pelo Git para evitar a publicação acidental de informações financeiras.

## Como executar

1. Instale um JDK compatível com Java 17 ou superior.
2. Abra o projeto no Visual Studio Code com as extensões de Java.
3. Mantenha a pasta principal FinanceJava como diretório de execução.
4. Execute a classe `Main.java`.
5. Utilize o menu exibido no terminal para gerenciar as transações.

## Testes automatizados

O projeto utiliza JUnit 5 para verificar operações da classe Conta.

**Resultado da última execução:**

- Testes executados: 13
- Testes aprovados: 13
- Falhas: 0
- Erros: 0
- Resultado: BUILD SUCCESS

## Conceitos aplicados

- Programação Orientada a Objetos.
- Encapsulamento.
- Construtores e métodos.
- Estruturas condicionais e de repetição.
- Coleções com ArrayList.
- Tratamento de exceções.
- Validação de entradas.
- Persistência de dados.
- Manipulação de arquivos.
- Testes automatizados.
- Controle de versão.

## Objetivo acadêmico

Projeto desenvolvido para consolidar os conhecimentos adquiridos na graduação em Engenharia de Software e construir experiência prática no desenvolvimento de aplicações Java.

O FinanceJava também representa o início de um portfólio de projetos voltados ao desenvolvimento de software.

## Possíveis evoluções

- Implementar banco de dados SQL.
- Criar categorias de receitas e despesas.
- Desenvolver relatórios financeiros.
- Implementar filtros por período.
- Criar uma interface gráfica.
- Desenvolver uma API REST.

## Status

**FinanceJava 1.0 — Implementação básica concluída.**

Projeto pessoal em evolução.