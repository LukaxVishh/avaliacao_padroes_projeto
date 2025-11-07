package avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear;

import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain.PlantState;
import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain.Telemetry;
import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.facade.NuclearController;

import java.time.Instant;

/**
 * Demonstração simples (cálculos/tempos simulados).
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        NuclearController plant = new NuclearController();

        // Boot: DESLIGADA -> OPERACAO_NORMAL
        plant.updateTelemetry(new Telemetry(Instant.now(), 290, 50, 0.1, false));
        System.out.println("Estado: " + plant.state());
        System.out.println("Start->NORMAL: " + plant.request(PlantState.OPERACAO_NORMAL));
        System.out.println("Estado: " + plant.state());

        // Sobe temperatura > 300 -> AMARELO
        plant.updateTelemetry(new Telemetry(Instant.now(), 320, 55, 0.1, false));
        System.out.println("NORMAL->AMARELO: " + plant.request(PlantState.ALERTA_AMARELO));
        System.out.println("Estado: " + plant.state());

        // Mantém > 400 por 31s para ir a VERMELHO
        Instant t0 = Instant.now();
        plant.updateTelemetry(new Telemetry(t0, 410, 60, 0.2, false));
        // Simula passagem de 31s (na vida real, viria de leituras periódicas)
        plant.updateTelemetry(new Telemetry(t0.plusSeconds(31), 415, 60, 0.2, false));
        System.out.println("AMARELO->VERMELHO: " + plant.request(PlantState.ALERTA_VERMELHO));
        System.out.println("Estado: " + plant.state());

        // Falha no resfriamento -> EMERGENCIA (permitido pois passou por VERMELHO)
        plant.updateTelemetry(new Telemetry(Instant.now(), 420, 65, 0.3, true));
        System.out.println("VERMELHO->EMERGENCIA: " + plant.request(PlantState.EMERGENCIA));
        System.out.println("Estado: " + plant.state());

        // Modo manutenção (overlay)
        plant.enterMaintenance();
        System.out.println(">> Manutenção ON. Estado reportado: " + plant.state());
        // Tentativa de mudar estado real é bloqueada enquanto manutenção ativa
        System.out.println("Tentativa de mudar durante manutenção: " +
                plant.request(PlantState.ALERTA_AMARELO));
        System.out.println("Estado reportado: " + plant.state());
        plant.exitMaintenance();
        System.out.println(">> Manutenção OFF. Estado reportado: " + plant.state());
    }
}
