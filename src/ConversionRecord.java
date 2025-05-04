import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionRecord {
    private final LocalDateTime timestamp;
    private final String monedaOrigen;
    private final String monedaDestino;
    private final double cantidad;
    private final double resultado;

    public ConversionRecord(String monedaOrigen, String monedaDestino, double cantidad, double resultado) {
        this.timestamp = LocalDateTime.now();
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.cantidad = cantidad;
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %.2f %s -> %.2f %s",
                timestamp.format(formatter),
                cantidad,
                monedaOrigen,
                resultado,
                monedaDestino);
    }
}