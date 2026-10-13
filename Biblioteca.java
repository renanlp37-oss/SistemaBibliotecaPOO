package biblioteca;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private final ArrayList<Leitor> leitores = new ArrayList<>();
    private final ArrayList<Livro> livros = new ArrayList<>();
    private final ArrayList<Emprestimo> emprestimos = new ArrayList<>();

    public boolean cadastrarLeitor(Leitor leitor) {
        if (leitor == null || consultarLeitor(leitor.getCodigo()) != null) return false;
        leitores.add(leitor);
        return true;
    }

    public Leitor consultarLeitor(int codigo) {
        for (Leitor leitor : leitores) if (leitor.getCodigo() == codigo) return leitor;
        return null;
    }

    public void listarLeitores() {
        if (leitores.isEmpty()) { System.out.println("Nenhum leitor cadastrado."); return; }
        for (Leitor leitor : leitores) System.out.println(leitor);
    }

    public boolean removerLeitor(Leitor leitor) {
        if (leitor == null || !leitores.contains(leitor)) return false;
        for (Emprestimo e : emprestimos)
            if (e.isAberto() && e.getLeitor() == leitor) return false;
        return leitores.remove(leitor);
    }

    public boolean cadastrarLivro(Livro livro) {
        if (livro == null || consultarLivro(livro.getCodigo()) != null) return false;
        livros.add(livro);
        return true;
    }

    public Livro consultarLivro(int codigo) {
        for (Livro livro : livros) if (livro.getCodigo() == codigo) return livro;
        return null;
    }

    public void listarLivros() {
        if (livros.isEmpty()) { System.out.println("Nenhum livro cadastrado."); return; }
        for (Livro livro : livros) System.out.println(livro);
    }

    public boolean removerLivro(Livro livro) {
        if (livro == null || !livros.contains(livro) || !livro.isDisponivel()) return false;
        for (Emprestimo e : emprestimos)
            if (e.isAberto() && e.contemLivro(livro)) return false;
        return livros.remove(livro);
    }

    public boolean registrarEmprestimo(int codigo, Leitor leitor, ArrayList<Livro> livrosSolicitados) {
        if (codigo <= 0 || leitor == null || consultarLeitor(leitor.getCodigo()) != leitor
                || consultarEmprestimo(codigo) != null || livrosSolicitados == null || livrosSolicitados.isEmpty()) return false;

        // Valida tudo antes de alterar a disponibilidade, evitando empréstimos parciais.
        ArrayList<Livro> unicos = new ArrayList<>();
        for (Livro livro : livrosSolicitados) {
            if (livro == null || consultarLivro(livro.getCodigo()) != livro
                    || !livro.isDisponivel() || unicos.contains(livro)) return false;
            unicos.add(livro);
        }

        Emprestimo emprestimo = new Emprestimo(codigo, leitor);
        for (Livro livro : unicos) {
            if (!emprestimo.adicionarLivro(livro)) return false;
        }
        for (Livro livro : unicos) livro.emprestar();
        emprestimos.add(emprestimo);
        return true;
    }

    private Emprestimo consultarEmprestimo(int codigo) {
        for (Emprestimo e : emprestimos) if (e.getCodigo() == codigo) return e;
        return null;
    }

    public void listarEmprestimos() {
        if (emprestimos.isEmpty()) { System.out.println("Nenhum empréstimo registrado."); return; }
        for (Emprestimo e : emprestimos) System.out.println(e);
    }

    public boolean registrarDevolucao(int codigo) {
        Emprestimo e = consultarEmprestimo(codigo);
        if (e == null || !e.isAberto()) return false;
        e.finalizar();
        return true;
    }
}
