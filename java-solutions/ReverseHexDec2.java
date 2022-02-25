import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReverseHexDec2 {
    public static void main(String[] args) {
        MyScanner in = new MyScanner(System.in);
        List<int[]> arr = new ArrayList<>();
        int arrSize = 0;
        int maxLength = 0;

        while (!in.finished()) {
            arr.add(new int[1]);
            int index = 0;
            String number = in.nextHexInt();
            int numberInt;

            int[] row = new int[1];
            while (number != null) {
                number = number.toLowerCase();
                if (row.length == index) {
                    row = Arrays.copyOf(row, 2 * row.length);
                }
                if (number.startsWith("0x")) {
                    numberInt = Integer.parseUnsignedInt(number.substring(2), 16);
                } else {
                    numberInt = Integer.parseInt(number);
                }
                row[index++] = numberInt;
                number = in.nextHexInt();
            }
            arr.set(arrSize, Arrays.copyOf(row, index));

            maxLength = Math.max(maxLength, row.length);
            arrSize++;
        }

        for (int i = arrSize - 1; i >= 0; i--) {
            for (int j = arr.get(i).length - 1; j >= 0; j--) {
                System.out.print("0x" + Integer.toHexString(arr.get(i)[j]) + " ");
            }
            System.out.println();
        }
    }
}