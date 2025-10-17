package social.exceptions;

public class PublicacaoException extends RuntimeException {
    public PublicacaoException(String msg) { super(msg); }
    public PublicacaoException(String msg, Throwable t) { super(msg, t); }
}
