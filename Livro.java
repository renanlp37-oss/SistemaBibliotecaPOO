package biblioteca;

public class Livro {
    private final int codigo;
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(int codigo, String titulo, String autor) {
        if (codigo <= 0) throw new IllegalArgumentException("O código deve ser positivo.");
        if (vazio(titulo) || vazio(autor)) throw new IllegalArgumentException("Título e autor são obrigatórios.");
        this.codigo = codigo;
        this.titulo = titulo.trim();
        this.autor = autor.trim();
        this.disponivel = true;
    }

    private boolean vazio(String s) { return s == null || s.trim().isEmpty(); }
    public int getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public boolean isDisponivel() { return disponivel; }

    public void atualizarDados(String titulo, String autor) {
        if (vazio(titulo) || vazio(autor)) throw new IllegalArgumentException("Título e autor são obrigatórios.");
        this.titulo = titulo.trim();
        this.autor = autor.trim();
    }

    public boolean emprestar() {
        if (!disponivel) return false;
        disponivel = false;
        return true;
    }

    public void devolver() { disponivel = true; }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + autor + ") - " + (disponivel ? "disponível" : "indisponível");
    }
}
