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
//            System.out.println(snippet + "999999999999");
        }

        promptBuilder.append(" and I have this code and I want to know whether " +
                "it is dangerous or not. i dont know if it is dangerous. " +
                " based on the knowledge I gave you, tell me this and then explain yourself " +
                "what the purpose of the code is and tell me after you told the code is dangerous " +
                "or not\n");
        promptBuilder.append(codeSnippet);
        String response = callLlamaService(promptBuilder.toString());
        safetyReport.append(response);

        return safetyReport.toString();
    }

    private String callLlamaService(String prompt) {
        String llamaUrl = "http://localhost:11434/api/generate";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(llamaUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonInputString = "{\"model\": \"llama3.2\", \"prompt\": \"" + escapeJson(prompt) + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonInputString.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    return response.toString();
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