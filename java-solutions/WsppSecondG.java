import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class WsppSecondG {
    public static void main(String[] args) {
        try {
            int indexWord = 1;
            MyScanner in = new MyScanner(new FileInputStream(args[0]), "utf8");
            try {
                Map<String, IntList> words = new LinkedHashMap<>();
                while (!in.finished()) {
                    Set<String> repeat = new LinkedHashSet<>();
                    String sword = in.nextWordToLine();
                    while (sword!=null) {
                        sword = sword.toLowerCase();
                        if (sword.length() != 0) {
                            if (words.containsKey(sword)) {
                                words.get(sword).appendGlobal(indexWord);
                                if (repeat.contains(sword)) {
                                    words.get(sword).increaseCurrent();
                                } else {
                                    words.get(sword).putOne();
                                }
                            } else {
                                words.put(sword, new IntList());
                                words.get(sword).appendGlobal(indexWord);
                                words.get(sword).putOne();
                            }
                            repeat.add(sword);
                            indexWord++;
                        }
                        sword = in.nextWordToLine();
                    }
                }
                try {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new
                            FileOutputStream(args[1]), "utf8"));
                    try {
                        for(Map.Entry<String,IntList> entry : words.entrySet()) {
                            String key = entry.getKey();
                            int[] globalIndex = entry.getValue().getGlobal();
                            int[] repeatIndex = entry.getValue().getCurrent();
                            if (!key.isEmpty()) {
                                boolean flag = false;
                                for (int index : repeatIndex) {
                                    if (index % 2 == 0) {
                                        flag = true;
                                        break;
                                    }
                                }
                                out.write(key + " " + globalIndex.length);
                                if (flag) {
                                    for (int i = 0; i < globalIndex.length; i++) {
                                        if (repeatIndex[i] % 2 == 0) {
                                            out.write(" " + globalIndex[i]);
                                        }
                                    }
                                }
                                out.write('\n');
                            }
                        }
                    } finally{
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