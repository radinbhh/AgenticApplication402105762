import java.util.Scanner;

public class UserInputHandler {
    public String getUserQuery() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your query: ");
        return scanner.nextLine();
    }
}