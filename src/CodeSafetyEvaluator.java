import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CodeSafetyEvaluator {
    public String evaluateSafety(List<String> codeSnippets, String codeSnippet) {
        StringBuilder safetyReport = new StringBuilder();

        // Prepare the prompt for Llama 3.2
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Hello. I know these codes are dangerous: \n");

        for (String snippet : codeSnippets) {
            promptBuilder.append(snippet).append("\n");
        }

        promptBuilder.append("at first just tell me what is the input code that i have gave you. I have this code and I want to know whether it is dangerous or not. Just based on the knowledge I gave you, tell me this and then explain yourself what the purpose of the code is and tell me after you told the code is dangerous or not\n");
        promptBuilder.append(codeSnippet);

        // Call Llama 3.2 to evaluate the safety of the code snippet
        String response = callLlamaService(promptBuilder.toString());

        // Append the response to the safety report
        safetyReport.append(response);

        return safetyReport.toString();
    }

    private String callLlamaService(String prompt) {
        String llamaUrl = "http://localhost:11434/api/generate"; // Adjusted URL for the Docker container
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(llamaUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Create JSON input string
            String jsonInputString = "{\"model\": \"llama3.2\", \"prompt\": \"" + escapeJson(prompt) + "\"}";

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
                    return response.toString(); // Return the response from Llama
                }
            } else {
                System.out.println("Error: " + responseCode + "codesafty");
                return "Error: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException: " + e.getMessage();
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