import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reverse {
    public static void main(String[] args) {
        MyScanner in = new MyScanner(System.in);
        List<String> arr = new ArrayList<>();

        while (!in.finished()) {
            String number = in.nextInLine();
            int index = 0;
            if (number == null) {
                arr.add("");
                continue;
            }

            StringBuilder line = new StringBuilder();
            int[] row = new int[1];
            while (number != null) {
                if (row.length == index) {
                    row = Arrays.copyOf(row, 2 * row.length);
                }
                row[index++] = Integer.parseInt(number, 10);
                number = in.nextInLine();
            }
            for (int j = index - 1; j >= 0; j--) {
                line.append(" " + row[j]);
            }
            arr.add(line.substring(1));
        }

        for (int i = arr.size() - 1; i >= 0; i--) {
            System.out.println(arr.get(i));
        }
    }
}
