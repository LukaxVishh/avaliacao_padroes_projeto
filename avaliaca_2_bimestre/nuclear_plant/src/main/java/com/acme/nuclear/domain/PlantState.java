package avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain;

/**
 * Estados operacionais "reais" da planta.
 * Observação: o modo MANUTENCAO é tratado como sobreposição (overlay),
 * não como estado persistente — por design do requisito.
 */
public enum PlantState {
    DESLIGADA,
    OPERACAO_NORMAL,
    ALERTA_AMARELO,
    ALERTA_VERMELHO,
    EMERGENCIA;

    public boolean isTerminal() {
        return this == EMERGENCIA;
    }
}
