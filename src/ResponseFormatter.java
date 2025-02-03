public class ResponseFormatter {
    public void formatAndDisplayResponse(String json) {
        // Remove whitespace for easier parsing

        json = json.trim();


        // Check if the input is empty or null

        if (json == null || json.isEmpty()) {

            System.out.println("Empty JSON string.");

            return;

        }


        // Print the JSON in a human-readable format

        System.out.println("Parsed JSON:");

        String[] keyValuePairs = json.replaceAll("[{}]", "").split(",");


        for (String pair : keyValuePairs) {

            String[] keyValue = pair.split(":");

            if (keyValue.length == 2) {

                String key = keyValue[0].trim().replaceAll("\"", ""); // Remove quotes

                String value = keyValue[1].trim().replaceAll("\"", ""); // Remove quotes

                System.out.print( value);
                if (value.contains(".")){
                    System.out.println();
                }

            }

        }

    }
}