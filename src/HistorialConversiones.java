import java.util.ArrayList;
import java.util.List;

public class HistorialConversiones {
    private final List<ConversionRecord> historial;

    public HistorialConversiones() {
        this.historial = new ArrayList<>();
    }

    public void agregarConversion(String monedaOrigen, String monedaDestino, double cantidad, double resultado) {
        ConversionRecord record = new ConversionRecord(monedaOrigen, monedaDestino, cantidad, resultado);
        this.historial.add(record);
    }

    public void mostrarHistorial() {
        System.out.println("\n--- Historial de Conversiones ---");
        if (historial.isEmpty()) {
            System.out.println("No hay conversiones registradas todavía.");
        } else {
            for (ConversionRecord record : historial) {
                System.out.println(record);
            }
        }
        System.out.println("**********************************");
    }
}