public class ResponseFormatter {
    public void formatAndDisplayResponse(String json) {

        json = json.trim();

        if (json == null || json.isEmpty()) {

            System.out.println("Empty JSON string.");

            return;

        }

//        System.out.println("Parsed JSON:");

        String[] keyValuePairs = json.replaceAll("[{}]", "").split(",");


        for (String pair : keyValuePairs) {

            String[] keyValue = pair.split(":");

            if (keyValue.length == 2) {

                String key = keyValue[0].trim().replaceAll("\"", ""); // Remove quotes

                String value = keyValue[1].trim().replaceAll("\"", ""); // Remove quotes

                System.out.print(value);
                if (value.contains(".")) {
                    System.out.println();
                }

            }

        }

    }
    public StringBuilder formatResponse(String json) {
        StringBuilder output = new StringBuilder();
        json = json.trim();

        if (json == null || json.isEmpty()) {

            System.out.println("Empty JSON string.");

            return null;

        }

//        System.out.println("Parsed JSON:");

        String[] keyValuePairs = json.replaceAll("[{}]", "").split(",");


        for (String pair : keyValuePairs) {

            String[] keyValue = pair.split(":");

            if (keyValue.length == 2) {

                String key = keyValue[0].trim().replaceAll("\"", ""); // Remove quotes

                String value = keyValue[1].trim().replaceAll("\"", ""); // Remove quotes

                output.append(value);
//                if (value.contains(".")){
//                    System.out.println();
//                }

            }


        }
        return output;

    }
}