import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class QuickChat {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- PART 1: REGISTRATION ---
        System.out.println("=== Register ===");
        System.out.print("Enter username (must contain _ and <=5 chars): ");
        String username = scanner.nextLine();
        System.out.print("Enter password (min 8 chars, capital, number, special char): ");
        String password = scanner.nextLine();
        System.out.print("Enter cell number (e.g. +2712345678): ");
        String cell = scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        User user = new User(firstName, lastName, username, password, cell);
        System.out.println(user.registerUser());

        // --- LOGIN ---
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();

        System.out.println(user.returnLoginStatus(loginUser, loginPass));

        if (!user.loginUser(loginUser, loginPass)) {
            System.out.println("Cannot proceed to QuickChat.");
            return;
        }

        // --- PART 2: QUICKCHAT ---
        ArrayList<Message> sentMessages = new ArrayList<>();
        int totalSent = 0;

        System.out.println("\nWelcome to QuickChat.");

        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("Option 1) Send Messages");
            System.out.println("Option 2) Show recently sent messages");
            System.out.println("Option 3) Quit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("How many messages do you wish to enter? ");
                    int numMessages = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 1; i <= numMessages; i++) {
                        System.out.println("\n--- Entering message " + i + " of " + numMessages + " ---");
                        System.out.print("Enter recipient cell number (e.g. +2712345678): ");
                        String recipient = scanner.nextLine();
                        System.out.print("Enter your message (less than 250 characters): ");
                        String text = scanner.nextLine();

                        Message msg = new Message(recipient, text);

                        if (text.length() > 250) {
                            System.out.println(msg.checkMessageLength());
                            i--;
                            continue;
                        }

                        System.out.println(msg.checkRecipientCell());

                        System.out.println("\nChoose what to do:");
                        System.out.println("1. Send Message");
                        System.out.println("2. Disregard Message");
                        System.out.println("3. Store Message to send later");
                        System.out.print("Choice: ");
                        int sendChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (sendChoice == 1) {
                            System.out.println("Message successfully sent");
                            sentMessages.add(msg);
                            totalSent++;
                            System.out.println("\n--- Full Message Details ---");
                            msg.displayFullDetails();
                            saveToJson(msg);
                        } else if (sendChoice == 2) {
                            System.out.println("Message disregarded.");
                        } else if (sendChoice == 3) {
                            System.out.println("Message successfully stored");
                            saveToJson(msg);
                        }
                    }
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    running = false;
                    System.out.println("Total messages sent: " + totalSent);
                    System.out.println("Exiting QuickChat. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }

    private static void saveToJson(Message msg) {
        String json = "{\n" +
                " \"messageID\": \"" + msg.getMessageID() + "\",\n" +
                " \"messageHash\": \"" + msg.getMessageHash() + "\",\n" +
                " \"recipient\": \"" + msg.getRecipient() + "\",\n" +
                " \"message\": \"" + msg.getMessageText() + "\"\n" +
                "}\n";
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }
}