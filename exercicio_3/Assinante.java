package exercicio_3;

/**
 * Observer: qualquer leitor que deseje receber notificações.
 */
public interface Assinante {
    /**
     * Método chamado pelo Subject quando há nova notícia no tópico inscrito.
     */
    void notificar(Noticia noticia);
}
