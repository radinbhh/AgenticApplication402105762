import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

import java.util.ArrayList;
import java.util.List;

public class Neo4jDatabaseHandler {
    private final String uri = "bolt://localhost:7687"; // Use bolt protocol for Neo4j
    private final String user = "neo4j";
    private final String password = "12345678";

    public List<String> searchSimilarSnippets(String embeddedText) {
        List<String> similarSnippets = new ArrayList<>();
        try (var driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
             Session session = driver.session()) {
            String query = "MATCH (c:CodeSnippet) WHERE c.embeddedText = $embeddedText RETURN c.codeSnippet";
            Result result = session.run(query, org.neo4j.driver.Values.parameters("embeddedText", embeddedText));
            while (result.hasNext()) {
                similarSnippets.add(result.next().get("c.codeSnippet").asString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return similarSnippets;
    }
}