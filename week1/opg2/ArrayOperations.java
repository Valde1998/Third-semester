package week1.opg2;

import java.util.Arrays;

interface MyTransformingType {
    int transform(int value);
}

interface MyValidatingType {
    boolean isValid(int value);
}

public class ArrayOperations {
    public static int[] map(int[] a, MyTransformingType op) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = op.transform(a[i]);
        }
        return result;
    }

    public static int[] filter(int[] a, MyValidatingType op) {
        int count = 0;
        for (int value : a) {
            if (op.isValid(value)) {
                count++;
            }
        }

        int[] result = new int[count];
        int index = 0;
        for (int value : a) {
            if (op.isValid(value)) {
                result[index++] = value;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};

        int[] mappedArray = map(array, (value) -> value * 2);
        System.out.println("Mapped Array: " + Arrays.toString(mappedArray));

        int[] filteredArray = filter(array, (value) -> value % 2 == 0);
        System.out.println("Filtered Array: " + Arrays.toString(filteredArray));
    }
}
