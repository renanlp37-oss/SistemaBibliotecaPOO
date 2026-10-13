package biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacao {
    private final Scanner scanner = new Scanner(System.in);
    private final Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        new Aplicacao().executar();
    }

    public void executar() {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            try {
                switch (opcao) {
                    case 1: cadastrarLeitor(); break;
                    case 2: consultarLeitor(); break;
                    case 3: biblioteca.listarLeitores(); break;
                    case 4: atualizarLeitor(); break;
                    case 5: removerLeitor(); break;
                    case 6: cadastrarLivro(); break;
                    case 7: consultarLivro(); break;
                    case 8: biblioteca.listarLivros(); break;
                    case 9: atualizarLivro(); break;
                    case 10: removerLivro(); break;
                    case 11: registrarEmprestimo(); break;
                    case 12: biblioteca.listarEmprestimos(); break;
                    case 13: registrarDevolucao(); break;
                    case 0: System.out.println("Aplicação encerrada."); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Dados inválidos: " + e.getMessage());
            }
            System.out.println();
        } while (opcao != 0);
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("===== SISTEMA DE BIBLIOTECA =====");
        System.out.println("1 - Cadastrar leitor");
        System.out.println("2 - Consultar leitor");
        System.out.println("3 - Listar leitores");
        System.out.println("4 - Atualizar leitor");
        System.out.println("5 - Remover leitor");
        System.out.println("6 - Cadastrar livro");
        System.out.println("7 - Consultar livro");
        System.out.println("8 - Listar livros");
        System.out.println("9 - Atualizar livro");
        System.out.println("10 - Remover livro");
        System.out.println("11 - Registrar empréstimo");
        System.out.println("12 - Listar empréstimos");
        System.out.println("13 - Registrar devolução");
        System.out.println("0 - Sair");
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try { return Integer.parseInt(entrada); }
            catch (NumberFormatException e) { System.out.println("Digite um número inteiro válido."); }
        }
    }

    private String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            System.out.println("O campo não pode ficar vazio.");
        }
    }

    private void cadastrarLeitor() {
        int codigo = lerInteiro("Código do leitor: ");
        Leitor leitor = new Leitor(codigo, lerTexto("Nome: "), lerTexto("E-mail: "));
        System.out.println(biblioteca.cadastrarLeitor(leitor) ? "Leitor cadastrado." : "Não foi possível cadastrar o leitor (código repetido).");
    }

    private void consultarLeitor() {
        Leitor leitor = biblioteca.consultarLeitor(lerInteiro("Código do leitor: "));
        System.out.println(leitor == null ? "Registro não encontrado." : leitor);
    }

    private void atualizarLeitor() {
        Leitor leitor = biblioteca.consultarLeitor(lerInteiro("Código do leitor: "));
        if (leitor == null) { System.out.println("Registro não encontrado."); return; }
        leitor.atualizarDados(lerTexto("Novo nome: "), lerTexto("Novo e-mail: "));
        System.out.println("Dados do leitor atualizados.");
    }

    private void removerLeitor() {
        Leitor leitor = biblioteca.consultarLeitor(lerInteiro("Código do leitor: "));
        if (leitor == null) { System.out.println("Registro não encontrado."); return; }
        System.out.println(biblioteca.removerLeitor(leitor) ? "Leitor removido." : "Leitor não removido. Há empréstimo aberto.");
    }

    private void cadastrarLivro() {
        int codigo = lerInteiro("Código do livro: ");
        Livro livro = new Livro(codigo, lerTexto("Título: "), lerTexto("Autor: "));
        System.out.println(biblioteca.cadastrarLivro(livro) ? "Livro cadastrado." : "Não foi possível cadastrar o livro (código repetido).");
    }

    private void consultarLivro() {
        Livro livro = biblioteca.consultarLivro(lerInteiro("Código do livro: "));
        System.out.println(livro == null ? "Registro não encontrado." : livro);
    }

    private void atualizarLivro() {
        Livro livro = biblioteca.consultarLivro(lerInteiro("Código do livro: "));
        if (livro == null) { System.out.println("Registro não encontrado."); return; }
        livro.atualizarDados(lerTexto("Novo título: "), lerTexto("Novo autor: "));
        System.out.println("Dados do livro atualizados.");
    }

    private void removerLivro() {
        Livro livro = biblioteca.consultarLivro(lerInteiro("Código do livro: "));
        if (livro == null) { System.out.println("Registro não encontrado."); return; }
        System.out.println(biblioteca.removerLivro(livro) ? "Livro removido." : "Livro não removido. Verifique se está emprestado.");
    }

    private void registrarEmprestimo() {
        int codigoEmprestimo = lerInteiro("Código do empréstimo: ");
        Leitor leitor = biblioteca.consultarLeitor(lerInteiro("Código do leitor: "));
        if (leitor == null) { System.out.println("Leitor não encontrado."); return; }

        ArrayList<Livro> selecionados = new ArrayList<>();
        System.out.println("Digite os códigos dos livros. Digite 0 para concluir.");
        while (true) {
            int codigoLivro = lerInteiro("Código do livro (0 para concluir): ");
            if (codigoLivro == 0) break;
            Livro livro = biblioteca.consultarLivro(codigoLivro);
            if (livro == null) System.out.println("Livro não encontrado.");
            else if (selecionados.contains(livro)) System.out.println("Esse livro já foi selecionado.");
            else if (!livro.isDisponivel()) System.out.println("Livro indisponível.");
            else selecionados.add(livro);
        }
        System.out.println(biblioteca.registrarEmprestimo(codigoEmprestimo, leitor, selecionados)
                ? "Empréstimo registrado." : "Não foi possível registrar o empréstimo. Confira código e livros disponíveis.");
    }

    private void registrarDevolucao() {
        int codigo = lerInteiro("Código do empréstimo: ");
        System.out.println(biblioteca.registrarDevolucao(codigo) ? "Devolução registrada." : "Empréstimo não encontrado ou já finalizado.");
    }
}
