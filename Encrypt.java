import java.util.Scanner;
import java.lang.Math;

public class Encrypt {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        //prompt for user input
        System.out.print("Enter the public key: ");
        long pubKey = kb.nextInt();
        System.out.print("Enter the word to encrypt: ");
        String message = kb.next();

        //we cannot encrypt with negative or zero integers
        if (pubKey < 1) {
            throw new IllegalArgumentException();
        }

        //if all good, convert original message to lowercase and then to
        //numerical form
        message = message.toLowerCase();
        System.out.println(message);
        long numMessage = toNum(message);

        //encrypt and print results to screen
        long encrypted = (numMessage * numMessage) % pubKey;
        System.out.println("The encrypted message is " + encrypted);
    }

    private static long toNum(String message) {
        long length = message.length();
        long result = 0;
        long charValue;

        for (int i = 0; i < length; i++) {
            char toConvert = message.charAt(i);

            switch (toConvert) {
            case 'a':
                charValue = 1;
                break;
            case 'b':
                charValue = 2;
                break;
            case 'c':
                charValue = 3;
                break;
            case 'd':
                charValue = 4;
                break;
            case 'e':
                charValue = 5;
                break;
            case 'f':
                charValue = 6;
                break;
            case 'g':
                charValue = 7;
                break;
            case 'h':
                charValue = 8;
                break;
            case 'i':
                charValue = 9;
                break;
            case 'j':
                charValue = 10;
                break;
            case 'k':
                charValue = 11;
                break;
            case 'l':
                charValue = 12;
                break;
            case 'm':
                charValue = 13;
                break;
            case 'n':
                charValue = 14;
                break;
            case 'o':
                charValue = 15;
                break;
            case 'p':
                charValue = 16;
                break;
            case 'q':
                charValue = 17;
                break;
            case 'r':
                charValue = 18;
                break;
            case 's':
                charValue = 19;
                break;
            case 't':
                charValue = 20;
                break;
            case 'u':
                charValue = 21;
                break;
            case 'v':
                charValue = 22;
                break;
            case 'w':
                charValue = 23;
                break;
            case 'x':
                charValue = 24;
                break;
            case 'y':
                charValue = 25;
                break;
            case 'z':
                charValue = 26;
                break;
            default:
                throw new IllegalArgumentException();
            }

            long toAdd = charValue * ((long) Math.pow(10, (length - i - 1) * 2));
            result = result + toAdd;
        }

        System.out.println(result);
        return result;
    }
}
