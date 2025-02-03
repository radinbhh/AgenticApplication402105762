import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CodeSnippetExtractor {
    public String extractCodeSnippet(String query) {
        // Call Llama 3.2 to extract code snippet
        String llamaUrl = "http://localhost:11434/api/generate"; // Use localhost for host machine access
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(llamaUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Create JSON input string
            String jsonInputString = "{\"model\": \"llama3.2\", \"prompt\": \"" + query + " !!!extract the code part for me please and give it back. don't write anything else." + "\"}";

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
//                    System.out.println("this stage was ok");
                    return response.toString(); // Placeholder for actual code snippet extraction
                }
            } else {
                System.out.println("Error: " + responseCode + " codeSnippet");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}