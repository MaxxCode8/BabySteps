import java.io.*;
import java.util.Scanner;

public class AdminLogin {
    public static void main(String[] args) throws IOException {
        // Store the login ID and password in a text file
        storeLogin("admin", "password");

        // Read the login ID and password from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your login ID: ");
        String loginId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        if (checkLogin(loginId, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed.");
        }
    }

    // This method stores the login ID and password in a text file
    public static void storeLogin(String loginId, String password) throws IOException {
        // Create a PrintWriter object to write to the text file
        FileWriter writer = new FileWriter("login.txt",true);

        // Write the login ID and password to the text file, separated by a colon
        writer.write(loginId + ":" + password);

        // Close the PrintWriter
        writer.close();
    }

    // This method reads the login ID and password from the user and checks them against the stored values in the text file
    public static boolean checkLogin(String loginId, String password) throws IOException {
        // Create a BufferedReader object to read from the text file
        BufferedReader reader = new BufferedReader(new FileReader("login.txt"));

        // Creating a boolean variable that will store the access result ...
        boolean loginSuccess = false;

        String line;
        while ((line = reader.readLine()) != null) {
            // Split the line into login ID and password
            String[] parts = line.split(":");
            String fileLoginID = parts[0];
            String filePassword = parts[1];

            // Check if the login ID and password match the provided values
            if (loginId.equals(fileLoginID) && password.equals(filePassword)) {
                loginSuccess = true;
                break;
            }
        }
        // Close the BufferedReader
        reader.close();

        // Compare the login ID and password from the user with the stored values
        return loginSuccess;
    }
}
