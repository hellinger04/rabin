import java.util.Scanner;

public class Decrypt {

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

        long root1 = roots(pubKey, encrypted, factor1);
        long root2 = roots(pubKey, encrypted * -1, factor1);
        long root3 = roots(pubKey, encrypted, factor2);
        long root4 = roots(pubKey, encrypted * -1, factor2);

        long[] solutions = new long[4];
        solutions[0] = chineseRemainder(root1, factor1, root3, factor2);
        solutions[1] = chineseRemainder(root2, factor1, root3, factor2);
        solutions[2] = chineseRemainder(root1, factor1, root4, factor2);
        solutions[3] = chineseRemainder(root2, factor1, root4, factor2);

        //print solutions to screen
        for (int i = 0; i < 4; i++) {
            System.out.println("Solution " + (i + 1) + ": " + solutions[i]);
        }

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
        //calculate the inverse of the first factor mod the second factor
        long inverse = extendedEuclid(factor1, factor2);
        //multiply the difference and the inverse in Z(factor2)
        long remainder = (difference * inverse) % factor2;

        //return the remainder when we plug back into original equation
        return (factor1 * remainder) + root1;
    }

    private static long roots(long pubKey, long encrypted, long factor) {
        //determine what the power is
        long power = (factor + 1) / 4;

        return power(encrypted, power, factor);
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
