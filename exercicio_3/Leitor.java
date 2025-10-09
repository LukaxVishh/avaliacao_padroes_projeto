package exercicio_3;

/**
 * Observer concreto. Um leitor que recebe notificações de notícias.
 */
public class Leitor implements Assinante {
    private final String nome;
    private final String email; // opcional; aqui só para ilustrar

    public Leitor(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    @Override
    public void notificar(Noticia noticia) {
        // Aqui apenas imprimimos; em produção, enviaria e-mail/push/etc.
        System.out.printf(
            "[ALERTA a %s <%s>] Nova notícia em %s: \"%s\"%n",
            nome, email, noticia.getTopico(), noticia.getTitulo()
        );
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
}
