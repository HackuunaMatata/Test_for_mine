import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.apache.commons.math3.special.Gamma.*;
/**
 * Implementation of the frequency block test
 */
public class BlocksTest {

    private static int[] intArray = {1242173892, 131833885, 1395879035, 1332519137, 988707863, 618207570, 259305147, 1023143573, 1061271351, 726395468, 2010157505, 268950226, 651818429, 423711305, 1435120875, 1083712105, 507235135, 473584014, 2113545703, 755729261, 892185512, 344520278, 1410031019, 689199306, 1394448310};
    // result of work of method: window.crypto.getRandomValues(Uint32Array(25))
    private static final int M = 10; // the length of one block
    private static int N;

    public static void main(String[] args) {
        int[] bitArray = generateBits();
        N = bitArray.length / M; // the number of blocks
        List<int[]> blocks = generateBlocks(bitArray); // the first step of the test
        double[] partOfOnes = partOfOnesInBlocks(blocks); // the second step of the test
        double chi = chiSquare(partOfOnes); // the third step of the test
        double pValue = regularizedGammaQ(N/2, chi/2); // the fourth step of the test: search of the pValue through incomplete gamma-function
        if (pValue < 0.1) System.out.println("Частотный блочный тест не пройден");
// final step of the test
        else System.out.println("Частотный блочный тест пройден"); // Decision-making: whether the sequence is accidental
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

    private static List<int[]> generateBlocks(int[] bitArray) { // the bit flow breaks into N blocks on the M bit everyone
        List<int[]> blocks = new ArrayList<int[]>();
        for (int i = 0; i < M * N; i += M) { // generation of blocks with length M
            blocks.add(Arrays.copyOfRange(bitArray, i, i+M));
        }
        return blocks;
    }

    private static double[] partOfOnesInBlocks(List<int[]> blocks) { // find part of ones in blocks
        double[] partOfOnes = new double[blocks.size()];
        double pi; // sum of ones in one block
        for (int[] block : blocks) { // round of all blocks
            pi = 0;
            for (int i : block) { // find sum of ones in one block
                pi += i;
            }
            pi /= M; // find part of ones in one block
            partOfOnes[blocks.indexOf(block)] = pi;
        }
        return partOfOnes;
    }

    private static double chiSquare(double[] partOfOnes) { // count of statistics on the chi-square method with N levels of freedom
        double chi = 0;
        for (double pi : partOfOnes) { // find sum for this statistics
            chi += Math.pow((pi - 0.5), 2);
        }
        chi = chi * 4 * M; // count the statistic
        return chi;
    }
}
