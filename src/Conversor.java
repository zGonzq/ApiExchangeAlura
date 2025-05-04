import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class Conversor {

    private static final String API_KEY = "apikey";
    private static final String API_URL_BASE = "https://v6.exchangerate-api.com/v6/";

    public Map<String, Double> obtenerTasasDeCambio(String monedaBase) {
        URI uri = URI.create(API_URL_BASE + API_KEY + "/latest/" + monedaBase);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

                if (jsonResponse.has("conversion_rates")) {
                    JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");

                    Map<String, Double> tasasFiltradas = new HashMap<>();
                    String[] monedasDeseadas = {"ARS", "BOB", "BRL", "CLP", "COP", "USD"};

                    for (String moneda : monedasDeseadas) {
                        if (rates.has(moneda)) {
                            tasasFiltradas.put(moneda, rates.get(moneda).getAsDouble());
                        }
                    }
                    return tasasFiltradas;
                } else {
                    System.err.println("Error: La respuesta JSON no contiene 'conversion_rates'.");
                    return null;
                }
            } else {
                System.err.println("Error al obtener tasas de cambio. Código de estado: " + response.statusCode());
                System.err.println("Respuesta: " + response.body());
                return null;
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error al realizar la solicitud HTTP: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            return null;
        }
    }

    public double convertir(double cantidad, String monedaOrigen, String monedaDestino, Map<String, Double> tasas) {
        if (tasas == null || !tasas.containsKey(monedaOrigen) || !tasas.containsKey(monedaDestino)) {
            throw new IllegalArgumentException("Tasas de cambio no disponibles para las monedas especificadas.");
        }

        double tasaConversion = tasas.get(monedaDestino);
        return cantidad * tasaConversion;
    }
}