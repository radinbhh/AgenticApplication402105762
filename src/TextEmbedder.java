import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TextEmbedder {
    public String embedText(String codeSnippet) {
        // Call Nomic to embed text
        String nomicUrl = "http://localhost:11434/api/embeddings"; // Adjusted URL for the Docker container
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(nomicUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Escape the codeSnippet to ensure valid JSON
            String escapedCodeSnippet = escapeJson(codeSnippet);

            // Create JSON input string
            String jsonInputString = "{\"model\": \"nomic-embed-text\", \"prompt\": \"" + escapedCodeSnippet + "\"}";

            // Send the request
            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonInputString.getBytes(StandardCharsets.UTF_8));
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response from the input stream
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    return response.toString(); // Placeholder for actual embedded text extraction
                }
            } else {
                System.out.println("Error: " + responseCode + " text embedder");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to escape JSON special characters
    private String escapeJson(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}