import java.util.List;

public class MainApplication {
    public static void main(String[] args) {
        // Initialize the User Input Handler
        UserInputHandler inputHandler = new UserInputHandler();
        ResponseFormatter responseFormatter = new ResponseFormatter();
        // Get the user query
        String userQuery = inputHandler.getUserQuery();
        System.out.println("The query you gave is : \n" + userQuery + "\n________________");

        // Initialize the Code Snippet Extractor
        CodeSnippetExtractor extractor = new CodeSnippetExtractor();
        // Extract the code snippet from the user query
        String codeSnippet = extractor.extractCodeSnippet(userQuery);
        StringBuilder test = responseFormatter.formatResponse(codeSnippet).delete(0,8);
        int index = test.indexOf("truestop");
        test.setLength(index);
        codeSnippet = test.toString();

        System.out.println("The code snippet you provided is : \n" + codeSnippet + "\n________________");
        System.out.println();
        // Initialize the Text Embedder
        TextEmbedder embedder = new TextEmbedder();
        // Embed the extracted code snippet
        String embeddedText = embedder.embedText(codeSnippet);
//        embeddedText = responseFormatter.formatResponse(embeddedText).toString();
        System.out.println("The embedded version of your snippet is: \n" + embeddedText + "\n________________");
        // Initialize the Neo4j Database Handler
        Neo4jDatabaseHandler databaseHandler = new Neo4jDatabaseHandler();
        // Search for similar code snippets in the database
        List<String> similarSnippets = databaseHandler.searchSimilarSnippets(embeddedText);
        for (String s : similarSnippets){
            s = responseFormatter.formatResponse(s).toString();
        }
        // Initialize the Code Safety Evaluator
        CodeSafetyEvaluator safetyEvaluator = new CodeSafetyEvaluator();
        // Evaluate the safety of the similar code snippets
        String safetyResponse = safetyEvaluator.evaluateSafety(similarSnippets,codeSnippet);

        // Initialize the Response Formatter
        // Format and display the final response to the user
        responseFormatter.formatAndDisplayResponse(safetyResponse);
    }
}