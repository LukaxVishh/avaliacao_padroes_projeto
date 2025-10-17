package social.adapter;

import social.core.*;

public interface SocialAdapter {
    Plataforma plataforma();

    /** Publica e retorna Publicacao OU lança exceções específicas do vendor. */
    Publicacao publicar(Conteudo c) throws Exception;

    /** Consulta estatísticas do post. */
    Estatisticas estatisticas(String postId) throws Exception;
}
