package biblioteca;

public class Leitor {
    private final int codigo;
    private String nome;
    private String email;

    public Leitor(int codigo, String nome, String email) {
        if (codigo <= 0) throw new IllegalArgumentException("O código deve ser positivo.");
        if (textoVazio(nome) || textoVazio(email)) throw new IllegalArgumentException("Nome e e-mail são obrigatórios.");
        this.codigo = codigo;
        this.nome = nome.trim();
        this.email = email.trim();
    }

    private boolean textoVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    public void atualizarDados(String nome, String email) {
        if (textoVazio(nome) || textoVazio(email)) throw new IllegalArgumentException("Nome e e-mail são obrigatórios.");
        this.nome = nome.trim();
        this.email = email.trim();
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " (" + email + ")";
    }
}
