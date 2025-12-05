import java.util.Scanner;

public class Login {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWORD = "0000";

    public enum Role {
        ADMIN,
        USER,
        NONE
    }

    public static Role login(Scanner scanner) {
        System.out.println("=== Login ===");
        System.out.print("Username: ");
        String user = scanner.nextLine().trim();

        System.out.print("Password: ");
        String pass = scanner.nextLine().trim();

        if (user.equals(ADMIN_USERNAME) && pass.equals(ADMIN_PASSWORD)) {
            System.out.println("Admin login successful.");
            return Role.ADMIN;
        }

        if (user.equals(USER_USERNAME) && pass.equals(USER_PASSWORD)) {
            System.out.println("User login successful.");
            return Role.USER;
        }

        System.out.println("Invalid username or password.");
        return Role.NONE;
    }
}

