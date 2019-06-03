import java.util.Scanner;

public class Encrypt {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        //prompt for user input
        System.out.print("Enter the public key: ");
        int pubKey = kb.nextInt();
        System.out.print("Enter the message to encrypt: ");
        int message = kb.nextInt();

        //we cannot encrypt with negative or zero integers
        if (pubKey < 1 || message < 1) {
            throw new IllegalArgumentException();
        }

        //if all good, encrypt original message and print results to screen
        int encrypted = (message * message) % pubKey
        System.out.println("The encrypted message is " + encrypted);
    }
}
