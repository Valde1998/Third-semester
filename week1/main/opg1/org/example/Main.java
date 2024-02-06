package org.example;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        ArithmeticOperation addition = (a, b) -> a + b;
        System.out.println("add " + addition.perform(3, 5));

        ArithmeticOperation subtraction = (a, b) -> a - b;
        System.out.println("sub " + subtraction.perform(7, 5));

        ArithmeticOperation multiplication = (a, b) -> a * b;
        System.out.println("multiply " + multiplication.perform(2, 8));

        ArithmeticOperation division = (a, b) -> a / b;
        System.out.println("division " + division.perform(20, 5));

        ArithmeticOperation modulus = (a, b) -> a % b;
        System.out.println("modulus " + modulus.perform(10, 4));

        ArithmeticOperation power = (a, b) -> (int) Math.pow(a, b);
        System.out.println("pow " + power.perform(10, 4));

        int[] arrayA = {1, 2, 3};
        int[] arrayB = {4, 5, 6};


        int[] resultArray = operate(arrayA, arrayB, multiplication);
        System.out.println("Multiplication of arrays: " + Arrays.toString(resultArray));
    }

    public static int[] operate(int[] a, int[] b, ArithmeticOperation op) {
        if (a.length != b.length) {
        }

        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = op.perform(a[i], b[i]);
        }

        return result;
    }
}
