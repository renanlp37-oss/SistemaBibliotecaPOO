package biblioteca;

public class ItemEmprestimo {
    private final Livro livro;
    private boolean devolvido;

    public ItemEmprestimo(Livro livro) {
        if (livro == null) throw new IllegalArgumentException("Livro inválido.");
        this.livro = livro;
        this.devolvido = false;
    }

    public Livro getLivro() { return livro; }
    public boolean isDevolvido() { return devolvido; }
    public void registrarDevolucao() { devolvido = true; }
}
