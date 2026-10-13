package biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo {
    private final int codigo;
    private final LocalDate dataEmprestimo;
    private boolean aberto;
    private final Leitor leitor;
    private final ArrayList<ItemEmprestimo> itens;

    public Emprestimo(int codigo, Leitor leitor) {
        if (codigo <= 0 || leitor == null) throw new IllegalArgumentException("Empréstimo inválido.");
        this.codigo = codigo;
        this.leitor = leitor;
        this.dataEmprestimo = LocalDate.now();
        this.aberto = true;
        this.itens = new ArrayList<>();
    }

    public int getCodigo() { return codigo; }
    public boolean isAberto() { return aberto; }
    public Leitor getLeitor() { return leitor; }
    public List<ItemEmprestimo> getItens() { return new ArrayList<>(itens); }

    public boolean contemLivro(Livro livro) {
        for (ItemEmprestimo item : itens) if (item.getLivro() == livro) return true;
        return false;
    }

    public boolean adicionarLivro(Livro livro) {
        if (!aberto || livro == null || !livro.isDisponivel() || contemLivro(livro)) return false;
        itens.add(new ItemEmprestimo(livro));
        return true;
    }

    public void finalizar() {
        if (!aberto) return;
        for (ItemEmprestimo item : itens) {
            item.registrarDevolucao();
            item.getLivro().devolver();
        }
        aberto = false;
    }

    @Override
    public String toString() {
        StringBuilder nomes = new StringBuilder();
        for (ItemEmprestimo item : itens) {
            if (nomes.length() > 0) nomes.append(", ");
            nomes.append(item.getLivro().getTitulo());
        }
        return codigo + " - " + leitor.getNome() + " - "
                + dataEmprestimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " - " + (aberto ? "aberto" : "finalizado") + " - livros: " + nomes;
    }
}
