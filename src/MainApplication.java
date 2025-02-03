import java.util.List;

public class MainApplication {
    public static void main(String[] args) {
        // Initialize the User Input Handler
        UserInputHandler inputHandler = new UserInputHandler();
        // Get the user query
        String userQuery = inputHandler.getUserQuery();
        // System.out.println(userQuery + "got it");

        // Initialize the Code Snippet Extractor
        CodeSnippetExtractor extractor = new CodeSnippetExtractor();
        // Extract the code snippet from the user query
        String codeSnippet = extractor.extractCodeSnippet(userQuery);

        // Initialize the Text Embedder
        TextEmbedder embedder = new TextEmbedder();
        // Embed the extracted code snippet
        String embeddedText = embedder.embedText(codeSnippet);

        // Initialize the Neo4j Database Handler
        Neo4jDatabaseHandler databaseHandler = new Neo4jDatabaseHandler();
        // Search for similar code snippets in the database
        List<String> similarSnippets = databaseHandler.searchSimilarSnippets(embeddedText);

        // Initialize the Code Safety Evaluator
        CodeSafetyEvaluator safetyEvaluator = new CodeSafetyEvaluator();
        // Evaluate the safety of the similar code snippets
        String safetyResponse = safetyEvaluator.evaluateSafety(similarSnippets,codeSnippet);

        // Initialize the Response Formatter
        ResponseFormatter responseFormatter = new ResponseFormatter();
        // Format and display the final response to the user
        responseFormatter.formatAndDisplayResponse(safetyResponse);
    }
}