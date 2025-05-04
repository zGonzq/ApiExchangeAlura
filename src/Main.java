// src/Main.java
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final HistorialConversiones historial = new HistorialConversiones();

    public static void main(String[] args) {
        Conversor conversor = new Conversor();
        Scanner scanner = new Scanner(System.in);
        Map<String, Double> tasas;

        System.out.println("***************************************************");
        System.out.println("Bienvenido al Conversor de Moneda");
        System.out.println("***************************************************");

        String monedaBase = "USD";
        System.out.println("\nObteniendo tasas de cambio iniciales (base " + monedaBase + ")...");
        tasas = conversor.obtenerTasasDeCambio(monedaBase);

        if (tasas == null || tasas.isEmpty()) {
            System.err.println("Error crítico: No se pudieron obtener las tasas de cambio iniciales. Saliendo.");
            scanner.close();
            return;
        } else {
            System.out.println("Tasas obtenidas correctamente.");
            System.out.println("\nValor de 1 USD en otras monedas:");
            System.out.printf("  Peso Argentino (ARS): %.2f%n", tasas.getOrDefault("ARS", 0.0));
            System.out.printf("  Boliviano (BOB):      %.2f%n", tasas.getOrDefault("BOB", 0.0));
            System.out.printf("  Real Brasileño (BRL): %.2f%n", tasas.getOrDefault("BRL", 0.0));
            System.out.printf("  Peso Chileno (CLP):   %.2f%n", tasas.getOrDefault("CLP", 0.0));
            System.out.printf("  Peso Colombiano (COP):%.2f%n", tasas.getOrDefault("COP", 0.0));
            System.out.println("---------------------------------------------------");
        }

        int opcion = 0;
        Set<String> monedasDisponibles = tasas.keySet();

        while (opcion != 3) {
            System.out.println("\n** Menú Principal **");
            System.out.println("1) Realizar Conversión");
            System.out.println("2) Mostrar Historial");
            System.out.println("3) Salir");
            System.out.println("***************************************************");
            System.out.print("Elija una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("\nMonedas disponibles: " + String.join(", ", monedasDisponibles));
                        System.out.print("Ingrese el código de la moneda de origen (ej. USD): ");
                        String origen = scanner.nextLine().toUpperCase();

                        System.out.print("Ingrese el código de la moneda de destino (ej. ARS): ");
                        String destino = scanner.nextLine().toUpperCase();

                        if (monedasDisponibles.contains(origen) && monedasDisponibles.contains(destino)) {
                            realizarConversion(scanner, tasas, origen, destino);
                        } else {
                            System.out.println("Error: Una o ambas monedas ingresadas no son válidas o no están disponibles.");
                        }
                        break;
                    case 2:
                        historial.mostrarHistorial();
                        break;
                    case 3:
                        System.out.println("Saliendo del programa. ¡Gracias por usar el conversor!");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente de nuevo.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número entero para la opción.");
                scanner.nextLine();
                opcion = 0;
            } catch (Exception e) {
                System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            }
            if (opcion != 3) {
                System.out.println("---------------------------------------------------");
            }
        }

        scanner.close();
    }

    public static void realizarConversion(Scanner scanner, Map<String, Double> tasas, String monedaOrigen, String monedaDestino) {
        try {
            System.out.printf("Ingrese la cantidad en %s a convertir a %s: ", monedaOrigen, monedaDestino);
            double cantidad = scanner.nextDouble();
            scanner.nextLine();

            if (cantidad < 0) {
                System.out.println("La cantidad no puede ser negativa.");
                return;
            }

            double tasaOrigenRelativaUSD = tasas.get(monedaOrigen);
            double tasaDestinoRelativaUSD = tasas.get(monedaDestino);

            double resultado = (cantidad / tasaOrigenRelativaUSD) * tasaDestinoRelativaUSD;

            System.out.println("***************************************************");
            System.out.printf("%.2f %s equivale a %.2f %s%n", cantidad, monedaOrigen, resultado, monedaDestino);
            System.out.println("***************************************************");
            historial.agregarConversion(monedaOrigen, monedaDestino, cantidad, resultado);

        } catch (InputMismatchException e) {
            System.err.println("Error: Debe ingresar un valor numérico para la cantidad.");
            scanner.nextLine();
        } catch (Exception e) {
            System.err.println("Error durante la conversión: " + e.getMessage());
        }
    }
}