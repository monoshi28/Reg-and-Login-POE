import java.util.Random;

public class Message {
    private static int totalMessages = 0;
    private static int messageCounter = 0;
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    public Message(String recipient, String messageText) {
        messageCounter++;
        this.messageNumber = messageCounter;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        StringBuilder sb = new
                StringBuilder();
        for (int i = 0; i < 10; i++) {
        sb.append(random.nextInt(10));
    }
        return sb.toString();
    }

    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() == 10;
    }

    public String checkMessageLength() {
        if (this.messageText.length() <= 250) {
            return "Message ready to send";
        } else {
            int excess = this.messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
    }

    public String checkRecipientCell() {
        if (this.recipient.startsWith("+") && this.recipient.length() >= 10 && this.recipient.length() <= 12) {
            return "Cell phone number successfully captured";
        } else {
            return "Cell phone number is incorrectly formatted, please correct the number and try again";
        }
    }

    public String createMessageHash() {
        String firstTwo = this.messageID.substring(0, 2);
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        this.messageHash = (firstTwo + ":" + this.messageNumber + ":" + firstWord + "_" + lastWord).toUpperCase();
        return this.messageHash;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public int getMessageNumber() { return messageNumber; }

    public void displayFullDetails() {
        System.out.println("Message ID: " + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageText);
    }
}
