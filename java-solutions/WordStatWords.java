import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.Locale;
import java.util.TreeMap;
import java.util.Map;

public class WordStatWords {
    public static void main(String[] args) {
        try {
            MyScanner in = new MyScanner(new FileInputStream(args[0]), "utf8");
            Map<String, Integer> words = new TreeMap<>();
            while(!in.finished()) {
                String word = in.nextWord();
                while (word!=null) {
                    word = word.toLowerCase();
                    if (words.containsKey(word)) {
                        words.put(word, words.get(word) + 1);
                    } else {
                        words.put(word, 1);
                    }
                    word = in.nextWord();
                }
            }
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new
                        FileOutputStream(args[1]), "utf8"));
                try {
                    for(Map.Entry<String,Integer> entry : words.entrySet()) {
                        String key = entry.getKey();
                        Integer value = entry.getValue();
                        if (!key.isEmpty()){
                            out.write(key + " " + value);
                            out.write(System.lineSeparator());
                        }
                    }
                } finally{
                    out.close();
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