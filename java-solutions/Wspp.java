import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Wspp {
    public static void main(String[] args) {
        try {
            int indexWord = 1;
            MyScanner in = new MyScanner(new FileInputStream(args[0]), "utf8");
            try {
                Map<String, IntList> words = new LinkedHashMap<>();
                while (in.hasNextWord()) {
                    String word = in.nextWord().toLowerCase();
                    if (words.containsKey(word)) {
                        words.get(word).appendGlobal(indexWord);
                    } else {
                        words.put(word, new IntList());
                        words.get(word).appendGlobal(indexWord);
                    }
                    indexWord++;
                }
                try {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new
                            FileOutputStream(args[1]), "utf8"));
                    try {
                        for (Map.Entry<String, IntList> entry : words.entrySet()) {
                            String key = entry.getKey();
                            int[] globalIndex = entry.getValue().getGlobal();
                            out.write(key + " " + globalIndex.length);
                            for (int index : globalIndex) {
                                out.write(" " + index);
                            }
                            out.write('\n');
                        }
                    } finally {
                        out.close();
                    }
                } finally {
                    in.close();
                }
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Cannot read or write file " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException " + e.getMessage());
        }
    }
}