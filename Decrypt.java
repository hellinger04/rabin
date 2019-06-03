import java.util.Scanner;

public class Decrypt {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        //prompt for user input
        System.out.print("Enter the two positive integers to calculate GCD for: ");
        int int1 = kb.nextInt();
        int int2 = kb.nextInt();

        //we cannot calculate the GCD with negative or zero integers
        if (int1 < 1 || int2 < 1) {
            throw new IllegalArgumentException();
        }

        //if all good, calculate GCD and print results to screen
        int gcd = euclid(int1, int2);
        System.out.println("The GCD of " + int1 + " and " + int2 + " is " + gcd);
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
