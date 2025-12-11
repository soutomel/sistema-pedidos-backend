package br.com.pedidos.sistema_pedidos.application.usecase;

import java.time.Duration;

public class ConsultarTempoMedioUseCase {

    public record TempoMedio(Duration tempoPreparo, Duration tempoEspera) {}

    public ConsultarTempoMedioUseCase() {
        }

    public TempoMedio executar() {
        Duration preparo = Duration.ofMinutes(15);
        Duration espera = Duration.ofMinutes(5);

        return new TempoMedio(preparo, espera);
    }
}