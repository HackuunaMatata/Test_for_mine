import static org.apache.commons.math3.special.Erf.*;
/**
 * Implementation of the test for the sequence of identical bits
 */
public class IdenticalBitsTest {

    private static int[] intArray = {1242173892, 131833885, 1395879035, 1332519137, 988707863, 618207570, 259305147, 1023143573, 1061271351, 726395468, 2010157505, 268950226, 651818429, 423711305, 1435120875, 1083712105, 507235135, 473584014, 2113545703, 755729261, 892185512, 344520278, 1410031019, 689199306, 1394448310};
// result of work of method: window.crypto.getRandomValues(Uint32Array(25))

    public static void main(String[] args) {
        int[] bitArray = generateBits();
        double partOfOnes = findPartOfOnes(bitArray); // the first step of the test
        if (Math.abs(partOfOnes - 0.5) > 2 / Math.sqrt(bitArray.length)) { // check of the ration from step one
            System.out.println("Тест на последовательность одинаковых битов не пройден");
            return;
        }
        int numberOfLines = findNumberOfLines(bitArray); // the second step of the test
        double numerator = Math.abs(numberOfLines - 2 * partOfOnes * bitArray.length * (1 - partOfOnes)); // count the numerator for argument of pValue
        double denominator = 2 * Math.sqrt(2 * bitArray.length) * partOfOnes * (1 - partOfOnes); // count the denominator for argument of pValue
        double pValue = erfc(numerator / denominator); // the third step of the test: search of the pValue through additional function of errors
        if (pValue < 0.1) System.out.println("Тест на последовательность одинаковых битов не пройден"); // final step of the test
        else System.out.println("Тест на последовательность одинаковых битов пройден"); // Decision-making: whether the sequence is accidental
    }

    private static int[] generateBits() {//generates the array consisting from 0 and 1
        StringBuilder stringArray = new StringBuilder();
        for (int i : intArray) { // a binary line creates from intArray
            stringArray.append(Integer.toBinaryString(i));
        }
        char[] charArray = stringArray.toString().toCharArray(); // conversion of a line to an array of characters
        int[] bitArray = new int[charArray.length]; // creation of a new array
        for (int i = 0; i < charArray.length; i++) { // conversion of the characters' array to an integers' array from 0 and 1
            bitArray[i] = Integer.parseInt((String.valueOf(charArray[i])));
        }
        return bitArray;
    }

    private static double findPartOfOnes(int[] bitArray) { // find part of ones in bit flow
        double pi = 0;
        for (int x : bitArray) { // find sum of bits
            pi += x;
        }
        pi /= bitArray.length; // count part of ones
        return pi;
    }

    private static int findNumberOfLines(int[] bitArray) { // count quantity of the series consisting of identical values of bits
        int vn = 0;
        int r;
        for (int k = 0; k < bitArray.length - 1; k++) { // count sum from the formula
            r = (bitArray[k] == bitArray[k+1]) ? 0 : 1; // if the following bit is same, then we appropriate 0, differently - 1
            vn += r;
        }
        vn++;
        return vn;
    }
}
