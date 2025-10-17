package social.core;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Resposta unificada com generics. */
public final class Resultado<T> {
    private final String correlationId = UUID.randomUUID().toString();
    private final T dado;
    private final List<ErroPlataforma> erros; // granular
    private final List<String> avisos;

    private Resultado(T dado, List<ErroPlataforma> erros, List<String> avisos) {
        this.dado = dado;
        this.erros = erros == null ? List.of() : List.copyOf(erros);
        this.avisos = avisos == null ? List.of() : List.copyOf(avisos);
    }

    public static <T> Resultado<T> ok(T dado, List<String> avisos) {
        return new Resultado<>(dado, List.of(), avisos);
    }
    public static <T> Resultado<T> erro(List<ErroPlataforma> erros) {
        return new Resultado<>(null, erros, List.of());
    }

    public String correlationId() { return correlationId; }
    public Optional<T> dado() { return Optional.ofNullable(dado); }
    public List<ErroPlataforma> erros() { return erros; }
    public List<String> avisos() { return avisos; }
    public boolean sucesso() { return dado != null && erros.isEmpty(); }
}
