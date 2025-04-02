package com.question1.question1.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/numbers")
public class AverageCalculatorController {
    private static final String BASE_URL = "http://20.244.56.144/evaluation-service/";
    private static final int WINDOW_SIZE = 10;
    private static final int TIMEOUT_MS = 500; // 500ms timeout

    private final LinkedHashSet<Integer> numberWindow = new LinkedHashSet<>();
    private final RestTemplate restTemplate;

    public AverageCalculatorController() {
        restTemplate = new RestTemplate(new TimeoutRequestFactory(TIMEOUT_MS));
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> getNumbers(
            @PathVariable String type,
            @RequestHeader(value = "Authorization", required = true) String authToken) { // Token is now required

        // Validate number type
        if (!Set.of("p", "f", "e", "r").contains(type)) {
            return ResponseEntity.badRequest().body("Invalid number type.");
        }

        // Check if Authorization header has Bearer token
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Authorization token is missing or invalid.");
        }

        // Construct URL based on type
        String url = switch (type) {
            case "p" -> BASE_URL + "primes";
            case "f" -> BASE_URL + "fibo";
            case "e" -> BASE_URL + "even";
            case "r" -> BASE_URL + "rand";
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        // Set Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<NumberResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    NumberResponse.class
            );

            List<Integer> newNumbers = response.getBody().numbers();
            List<Integer> prevState = List.copyOf(numberWindow);

            updateWindow(newNumbers);

            List<Integer> currState = List.copyOf(numberWindow);
            double avg = numberWindow.stream()
                    .mapToDouble(Integer::doubleValue)
                    .average()
                    .orElse(0.0);

            return ResponseEntity.ok(new Response(prevState, currState, newNumbers, avg));

        } catch (HttpStatusCodeException e) {
            return handleHttpError(e);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Timeout or other error: " + e.getMessage());
        }
    }

    // Handle specific HTTP errors
    private ResponseEntity<String> handleHttpError(HttpStatusCodeException e) {
        if (e.getStatusCode().value() == 401) {
            return ResponseEntity.status(401).body("Unauthorized: Invalid token.");
        }
        return ResponseEntity.status(500).body("Failed to fetch numbers: " + e.getResponseBodyAsString());
    }

    // Update sliding window
    private void updateWindow(List<Integer> newNumbers) {
        for (int num : newNumbers) {
            if (numberWindow.size() >= WINDOW_SIZE) {
                numberWindow.remove(numberWindow.iterator().next()); // Remove oldest
            }
            numberWindow.add(num); // Add new unique number
        }
    }

    // Response Models
    record NumberResponse(List<Integer> numbers) {}

    record Response(List<Integer> windowPrevState, List<Integer> windowCurrState, List<Integer> numbers, double avg) {}

    // Custom RequestFactory for timeouts
    static class TimeoutRequestFactory extends org.springframework.http.client.SimpleClientHttpRequestFactory {
        TimeoutRequestFactory(int timeout) {
            setConnectTimeout(timeout);
            setReadTimeout(timeout);
        }
    }
}
