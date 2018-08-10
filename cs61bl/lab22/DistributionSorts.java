import java.util.Arrays;

public class DistributionSorts {

    /**
     * Modify arr to be sorted. Assume arr only contains 0, 1, ..., 9
     */
    public static void countingSort(int[] arr) {
        // TODO your code here!
        int[] counts = new int[10];
        int[] temp = arr.clone();
        for (int i = 0; i < arr.length; i++) {
            counts[arr[i]]++;
        }
        int total = 0;
        for (int i = 0; i < counts.length; i++) {
            int c = counts[i];
            counts[i] = total;
            total += c;
        }
        for (int i = 0; i < temp.length; i++) {
            arr[counts[temp[i]]] = temp[i];
            counts[temp[i]]++;
        }
    }

    /**
     * Sorts the given array using LSD radix sort.
     */
    public static void LSDRadixSort(int[] arr) {
      int maxDigit = mostDigitsIn(arr);
      for (int d = 0; d < maxDigit; d++) {
        countingSortOnDigit(arr, d);
      }
    }

    /**
     * A helper method for radix sort. Modifies arr to be sorted according to
     * digit. When digit is equal to 0, sort the numbers by the rightmost digit of each number.
     *
     * You will need to create an auxiliary array in order to put elements in the
     * correct spot before transferring them back into arr.
     *
     */ 
    private static void countingSortOnDigit(int[] arr, int digit) {
        // TODO your code here!
        int pd = (int) Math.pow(10, digit);
        int[] temp = new int[arr.length];
        int[] carr = arr.clone();
        for (int i = 0; i < temp.length; i++) {
            temp[i] = arr[i] % (pd * 10) / pd;
        }
        int[] counts = new int[10];
        for (int i = 0; i < temp.length; i++) {
            counts[temp[i]]++;
        }
        int total = 0;
        for (int i = 0; i < counts.length; i++) {
            int c = counts[i];
            counts[i] = total;
            total += c;
        }
        for (int i = 0; i < temp.length; i++) {
            arr[counts[temp[i]]] = carr[i];
            counts[temp[i]]++;
        }
    }

    /**
     * Returns the highest number of digits that any integer in arr happens to
     * have.
     */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }

    /**
     * Returns a random integer between 0 and 9999.
     */
    private static int randomInt() {
        return (int) (10000 * Math.random());
    }

    /**
     * Returns a random integer between 0 and 9.
     */
    private static int randomDigit() {
        return (int) (10 * Math.random());
    }

    /**
     * Runs some very basic tests of counting sort and radix sort.
     */
    public static void main(String[] args) {
        int[] arr1 = new int[20];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr1));
        countingSort(arr1);
        if (arr1 != null) {
            System.out.println("Should be sorted: " + Arrays.toString(arr1));
        }

        int[] arr2 = new int[3];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr2));
        LSDRadixSort(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));

        int[] arr3 = new int[30];
        for (int i = 0; i < arr3.length; i++) {
            arr3[i] = randomInt();
        }
        System.out.println("Original array: " + Arrays.toString(arr3));
        LSDRadixSort(arr3);
        System.out.println("Should be sorted: " + Arrays.toString(arr3));
    }
}
