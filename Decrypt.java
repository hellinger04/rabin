import java.util.Scanner;

public class Decrypt {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        //prompt for user input
        System.out.print("Enter the encrypted message: ");
        long encrypted = kb.nextLong();
        System.out.print("Enter the public key: ");
        long pubKey = kb.nextLong();
        System.out.print("Enter the first factor of the public key: ");
        long factor1 = kb.nextLong();
        System.out.print("Enter the second factor of the public key: ");
        long factor2 = kb.nextLong();
        //System.out.println("Factor 1: " + factor1);
        //System.out.println("Factor 2: " + factor2);


        //we cannot decrypt with negative or zero integers
        if (pubKey < 1 || factor1 < 1 || factor2 < 1) {
            throw new IllegalArgumentException();
        }

        //if all good, begin decrpytion by finding the square roots of the
        //encrypted message

        //we must ensure the factors of the public key are prime and that
        //if we add one to them, they are then divisible by four
        //TODO check for primality
        if ((factor1 + 1) % 4 != 0 || (factor2 + 1) % 4 != 0
            || factor1 < 100000 || factor2 < 100000) {
            System.out.println("ERROR: Each private key must be a prime number "
                               + "that is greater than 100,000 and which has a "
                               + "remainder of 3\nwhen divided by 4.");
            throw new IllegalArgumentException();
        }

        long root1 = roots(encrypted, factor1);
        long root2 = roots(encrypted * -1, factor1);
        long root3 = roots(encrypted, factor2);
        long root4 = roots(encrypted * -1, factor2);

        //System.out.println("root 1: " + root1);
        //System.out.println("root 2: " + root2);
        //System.out.println("root 3: " + root3);
        //System.out.println("root 4: " + root4);

        //use the Chinese Remainder theorem to identify four possible
        //solutions to the encrypted message
        long[] solutions = new long[4];
        solutions[0] = chineseRemainder(root1, factor1, root3, factor2);
        solutions[1] = chineseRemainder(root2, factor1, root3, factor2);
        solutions[2] = chineseRemainder(root1, factor1, root4, factor2);
        solutions[3] = chineseRemainder(root2, factor1, root4, factor2);

        //print solutions to screen
        boolean success = false;
        for (int i = 0; i < 4; i++) {
            try {
                //System.out.println("Solution: " + solutions[i]);
                System.out.println("Message: " + convert(solutions[i]));
                success = true;
            } catch (IllegalArgumentException e) {}
        }

        //if no solution has been found, try subtracting the solution from the
        //public key to see if this generates a viable solution
        if (!success) {
            for (int i = 0; i < 4; i++) {
                try {
                    System.out.println("Message: " + convert(pubKey - solutions[i]));
                    success = true;
                } catch (IllegalArgumentException e) {}
            }
        }

        //if the solution still has not been found, print a message and end
        //program
        if (!success) {
            System.out.println("Decryption failed. Please ensure you entered the "
                               + "correct numbers.");
        }
    }


    private static long roots(long encrypted, long factor) {
        //determine what the power is
        long power = (factor + 1) / 4;

        long result = power(encrypted, power, factor);
        if (result < 0) {
            result += factor;
        }

        return result;
    }

    private static long power(long x, long y, long p) {
        //initialize result
        long result = 1;

        //make x positive
        if (x < 0) {
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

    private static long chineseRemainder(long root1, long factor1,
        long root2, long factor2) {

        //ensure root1 is less than root2, for simplified calculation
        if (root1 > root2) {
            long tempRoot = root1;
            long tempFactor = factor1;
            root1 = root2;
            factor1 = factor2;
            root2 = tempRoot;
            factor2 = tempFactor;
        }

        //calculate the difference between the two roots
        long difference = root2 - root1;
        // System.out.println("Difference:" + difference);
        //calculate the inverse of the first factor mod the second factor
        long inverse = extendedEuclid(factor1, factor2);
        // System.out.println("Inverse: " + inverse + " in Z" + factor2);
        //multiply the difference and the inverse in Z(factor2)
        long remainder = multiply(difference, inverse, factor2);
        // System.out.println("Remainder: " + remainder);

        //return the remainder when we plug back into original equation
        return (factor1 * remainder) + root1;
    }


    private static long multiply(long x, long y, long mod) {
        //initialize result
        long result = 0;

        x = x % mod;

        while (y > 0) {
            //if y is odd, add x to result
            if (y % 2 == 1) {
                result = (result + x) % mod;
            }

            //multiply x by 2
            x = (x * 2) % mod;

            //divide y by 2
            y /= 2;
        }

        //return result
        return result % mod;
    }

    private static long extendedEuclid(long a, long m) {
        long m0 = m;
        long y = 0, x = 1;

        if (m == 1) {
            return 0;
        }

        while (a > 1) {
            //q is quotient
            long q = a / m;

            long t = m;

            //m is remainder now, process
            //same as Euclid's algo
            m = a % m;
            a = t;
            t = y;

            //update x and y
            y = x - q * y;
            x = t;
        }

        //make x positive
        if (x < 0) {
            x += m0;
        }

        return x;
    }

    private static String convert(long message) {
        //base case - return nothing
        if (message == 0) {
            return "";
        }

        char charValue;
        int toConvert = (int) message % 100;

        switch (toConvert) {
        case 1:
            charValue = 'a';
            break;
        case 2:
            charValue = 'b';
            break;
        case 3:
            charValue = 'c';
            break;
        case 4:
            charValue = 'd';
            break;
        case 5:
            charValue = 'e';
            break;
        case 6:
            charValue = 'f';
            break;
        case 7:
            charValue = 'g';
            break;
        case 8:
            charValue = 'h';
            break;
        case 9:
            charValue = 'i';
            break;
        case 10:
            charValue = 'j';
            break;
        case 11:
            charValue = 'k';
            break;
        case 12:
            charValue = 'l';
            break;
        case 13:
            charValue = 'm';
            break;
        case 14:
            charValue = 'n';
            break;
        case 15:
            charValue = 'o';
            break;
        case 16:
            charValue = 'p';
            break;
        case 17:
            charValue = 'q';
            break;
        case 18:
            charValue = 'r';
            break;
        case 19:
            charValue = 's';
            break;
        case 20:
            charValue = 't';
            break;
        case 21:
            charValue = 'u';
            break;
        case 22:
            charValue = 'v';
            break;
        case 23:
            charValue = 'w';
            break;
        case 24:
            charValue = 'x';
            break;
        case 25:
            charValue = 'y';
            break;
        case 26:
            charValue = 'z';
            break;
        default:
            throw new IllegalArgumentException();
        }

        return convert(message / 100) + charValue;
    }
}
