
package com.main.currency_converter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
public class CurrencyController {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/api/convert")
    public ResponseEntity<Map<String, Object>> convert(
            @RequestParam double amount,
            @RequestParam String from,
            @RequestParam String to) {

        from = from.toUpperCase();
        to = to.toUpperCase();

        // Validate input
        if (!Double.isFinite(amount) || amount <= 0
                || !from.matches("[A-Z]{3}")
                || !to.matches("[A-Z]{3}")) {

            return ResponseEntity.badRequest().body(
                    Map.of("error", "Invalid amount or currency."));
        }

        // Same currency
        if (from.equals(to)) {
            return ResponseEntity.ok(Map.of(
                    "amount", amount,
                    "from", from,
                    "to", to,
                    "rate", 1.0,
                    "result", amount));
        }

        try {
            // Get exchange rates
            String url =
                    "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/"
                    + from.toLowerCase() + ".json";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return ResponseEntity.status(502).body(
                        Map.of("error",
                                "Exchange-rate service unavailable."));
            }

            // Read JSON response
            JsonNode data = mapper.readTree(response.body());

            JsonNode rateNode = data.path(from.toLowerCase())
                    .path(to.toLowerCase());

            if (!rateNode.isNumber() || rateNode.asDouble() <= 0) {
                return ResponseEntity.badRequest().body(
                        Map.of("error",
                                "Currency conversion unavailable."));
            }

            // Calculate converted amount
            double rate = rateNode.asDouble();
            double result = Math.round(amount * rate * 100.0) / 100.0;

            return ResponseEntity.ok(Map.of(
                    "amount", amount,
                    "from", from,
                    "to", to,
                    "rate", rate,
                    "result", result));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            return ResponseEntity.status(502).body(
                    Map.of("error", "Request interrupted."));

        } catch (Exception e) {
            e.printStackTrace(); // Show the actual error in the terminal

            return ResponseEntity.status(502).body(
                    Map.of("error",
                            "Unable to fetch exchange rates."));
        }
    }
}
