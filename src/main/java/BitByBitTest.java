import static org.apache.commons.math3.special.Erf.*;
/**
 * Implementation of the frequency bit-by-bit test
 */
public class BitByBitTest {

    private static int[] intArray = {1242173892, 131833885, 1395879035, 1332519137, 988707863, 618207570, 259305147, 1023143573, 1061271351, 726395468, 2010157505, 268950226, 651818429, 423711305, 1435120875, 1083712105, 507235135, 473584014, 2113545703, 755729261, 892185512, 344520278, 1410031019, 689199306, 1394448310};
// result of work of method: window.crypto.getRandomValues(Uint32Array(25))

    public static void main(String[] args) {
        int[] bitArray = generateBits();
        int sum = findSumOfBits(bitArray); // the first step of the test
        double pValue = erfc(Math.abs(sum)/Math.sqrt(2*bitArray.length)); // the second step of the test: search of the pValue through additional function of errors
        if (pValue < 0.1) System.out.println("Частотный побитовый тест не пройден"); // final step of the test
        else System.out.println("Частотный побитовый тест пройден"); // Decision-making: whether the sequence is accidental
    }

    private static int[] generateBits() {//generates the array consisting from 0 and 1
        StringBuilder stringArray = new StringBuilder();
        for (int i : intArray) { // a binary line creates from intArray
            stringArray.append(Integer.toBinaryString(i));
        }
        char[] charArray = stringArray.toString().toCharArray(); // conversion of a line to an array of characters
        int[] bitArray = new int[charArray.length]; // creation of a new array
        for (int i = 0; i < charArray.length; i++) { // conversion of the characters' array to an integers' array consisting from 0 and 1
            bitArray[i] = Integer.parseInt((String.valueOf(charArray[i])));
        }
        return bitArray;
    }

    private static int findSumOfBits(int[] bitArray) {  // search of the amount of all bits of the sequence
        int sum = 0;
        for (int x : bitArray) {
            sum += 2*x - 1;
        }
        return sum;
    }
}
