import java.util.Scanner;

public class Decrypt {

    // private long encrypted;
    // private long pubKey;
    // private long factor1;
    // private long factor2;

    //inner factors class, each holds a pair of factors
    private class Roots {
        long root1;
        long root2;

        //constructor for class
        Roots(long num1, long num2) {
            // left and right default to null
            this.root1 = num1;
            this.root2 = num2;
        }
    }


    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        //prompt for user input
        System.out.print("Enter the encrypted message: ");
        long encrypted = kb.nextInt();
        System.out.print("Enter the public key: ");
        long pubKey = kb.nextInt();
        System.out.print("Enter the factorization of the public key: ");
        long factor1 = kb.nextInt();
        long factor2 = kb.nextInt();

        Decrypt decryptor = new Decrypt();

        //we cannot decrypt with negative or zero integers
        if (pubKey < 1 || factor1 < 1 || factor2 < 1) {
            throw new IllegalArgumentException();
        }

        //if all good, begin decrpytion by finding the square roots of the
        //encrypted message

        //we must ensure the factors of the public key are prime and that
        //if we add one to them, they are then divisible by four
        //TODO check for primality
        if ((factor1 + 1) % 4 != 0 || (factor2 + 1) % 4 != 0) {
            throw new IllegalArgumentException();
        }

        Roots roots1 = decryptor.roots(pubKey, encrypted, factor1);
        Roots roots2 = decryptor.roots(pubKey, encrypted, factor2);

    }

    private long chineseRemainder(long root1, long factor1,
        long root2, long factor2) {
      
    }

    private Roots roots(long pubKey, long encrypted, long factor) {
        //determine what the power is
        long power = (factor + 1) / 4;

        long root1 = power(encrypted, power, factor);
        long root2 = power(encrypted * -1, power, factor);

        //construct and return result
        Roots result = new Roots(root1, root2);
        return result;
    }

    private static long power(long x, long y, long p) {
        //initialize result
        long result = 1;

        //make x positive
        while (x < 0) {
            x = x + p;
        }

        //ensure x is less than p
        x = x % p;

        //while y is positive
        while (y > 0) {
            //if y is odd, multiply x with result
            if (y % 2 == 1) {
                result = (result * x) % p;
            }

            //y must be even now
            y = y / 2;
            x = (x * x) % p;
        }

        //after while loop is complete, no more exponentiation is required
        return result;
 }

    private static int euclid(int num1, int num2) {
        do {
            //Euclid's algorithm for calculating GCD
            int remainder = num1 % num2;
            num1 = num2;
            num2 = remainder;
        } while (num2 != 0); //run until we find a common divisor

        //return gcd
        return num1;
    }
}
