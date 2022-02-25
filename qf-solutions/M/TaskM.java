import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.*;

public class TaskM {
    public static void main(String[] args) {
        MyScanner in = new MyScanner(System.in);
        int t = Integer.parseInt(in.next());
        int n;
        int[] answers = new int[t];
        for (int test = 0; test < t; test++) {
            n = Integer.parseInt(in.next());
            int[] arr = new int[n];
            int ans = 0;
            HashMap<Integer, Integer> count = new HashMap<>();
            for (int j = 0; j < n; j++) {
                arr[j] = Integer.parseInt(in.next());
            }
            int helpj;
            for (int i = 0; i < n-2; i++) {
                count.clear();
                count.put(arr[i+1], 1);
                for (int k = i+2; k < n; k++) {
                    helpj = arr[i] + arr[k];
                    if (helpj%2 == 0) {
                        if (count.containsKey(helpj/2)) {
                            ans += count.get(helpj/2);
                        }
                    }
                    if (count.containsKey(arr[k])) {
                        int countk = count.get(arr[k]) + 1;
                        count.put(arr[k], countk);
                    } else {
                        count.put(arr[k], 1);
                    }
                }
            }
            answers[test] = ans;
        }
        for (int i = 0; i < t; i++) {
            System.out.println(answers[i]);
        }
        in.myClose();
    }
}

class MyScanner {
    private Reader in;

    private char[] buffer;
    private final int SIZE_BUFFER = 300;
    private int indexBuffer;
    private int lenBuffer;

    private final String[] lineSeparators = new String[] {"\n", "\r", "\r\n", "\u2028", "\u2029", "\u0085"};

    private final int[] wordTypes = new int[]{Character.LOWERCASE_LETTER,
            Character.UPPERCASE_LETTER,
            Character.MODIFIER_LETTER,
            Character.OTHER_LETTER,
            Character.TITLECASE_LETTER,
            Character.DASH_PUNCTUATION};
    private final int[] wordSym = new int[]{'\''};

    private final int[] numTypes = new int[]{Character.DECIMAL_DIGIT_NUMBER,
            Character.UPPERCASE_LETTER,
            Character.LOWERCASE_LETTER};
    private final int[] numSym = new int[]{'-'};
    // lenBuffer > 0 , если буфер не пуст
    // lenBuffer = 0, если буфер пуст
    // lenBuffer = -1, если input закончен
    // lenBuffer = -2, если in закрыт

    public MyScanner() {
        this.buffer = new char[SIZE_BUFFER];
        this.indexBuffer = 0;
        this.lenBuffer = 0;
    }

    public MyScanner(InputStream in) {
        this();
        this.in = new InputStreamReader(in);
    }

    public MyScanner(String str) {
        this();
        this.in = new StringReader(str);
    }

    public MyScanner(FileInputStream file, String code) throws UnsupportedEncodingException {
        this();
        this.in = new InputStreamReader(file, code);
    }

    private void readInBuffer() {
        if (lenBuffer != 0) {
            return;
        }
        try {
            lenBuffer = in.read(buffer);
        } catch (IOException e) {
            myClose();
            System.out.println("Cannot read in buffer : " + e.getMessage());
        }
    }

    private boolean isGoodSymbol(char ch, int[] types, int[] symbol) {
        if (Character.isWhitespace(ch)) {
            return false;
        }
        if (types.length == 0 && symbol.length  == 0) {
            return true;
        }
        for (int i = 0; i < types.length; ++i) {
            if (Character.getType(ch) == types[i]) {
                return true;
            }
        }
        for (int i = 0; i < symbol.length; ++i) {
            if ((int)ch == symbol[i]) {
                return true;
            }
        }
        return false;
    }

    private boolean isLineSep(char ch, int it) {
        for (int i = 0; i < lineSeparators.length; ++i) {
            if (lineSeparators[i].length() <= it) {
                continue;
            }
            if (lineSeparators[i].charAt(it) == ch) {
                return true;
            }
        }
        return false;
    }

    private void skipCurLineSep() {
        int ind = 0;
        while (true) {
            while (indexBuffer < lenBuffer && isLineSep(buffer[indexBuffer], ind)) {
                indexBuffer++;
                ind++;
            }
            updateIndex();
            if (lenBuffer != 0) {
                return;
            }
            readInBuffer();
        }
    }

    private void updateIndex() {
        if (indexBuffer == lenBuffer) {
            indexBuffer = lenBuffer = 0;
        }
    }

    private void skipWhitespaces() {
        skipWhitespaces(new int[0], new int[0]);
    }

    private void skipWhitespaces(int[] types, int[] symbols) {
        while (true) {
            while (indexBuffer < lenBuffer && !isGoodSymbol(buffer[indexBuffer], types, symbols)) {
                indexBuffer++;
            }
            updateIndex();
            if (lenBuffer != 0) {
                return;
            }
            readInBuffer();
            if (lenBuffer <= -1) {
                return;
            }
        }
    }
    // true = встретили lineSep
    // false = нашли токен
    private boolean skipWhitespacesLine(int[] types, int[] symbols) {
        while (true) {
            while (indexBuffer < lenBuffer && !isGoodSymbol(buffer[indexBuffer], types, symbols) && !isLineSep(buffer[indexBuffer], 0)) {
                indexBuffer++;
            }
            updateIndex();
            if (lenBuffer > 0 && isLineSep(buffer[indexBuffer], 0)) {
                skipCurLineSep();
                return true;
            }
            if (lenBuffer != 0) {
                return false;
            }
            readInBuffer();
        }
    }

    public void myClose() {
        if (lenBuffer == -2) {
            return;
        }
        try {
            in.close();
            lenBuffer = -2;
        } catch (IOException e) {
            System.out.println("Cannot close input stream: " + e.getMessage());
        }
    }

    public boolean hasNext() {
        skipWhitespaces();
        return lenBuffer > 0;
    }

    public boolean hasNext(int[] types, int[] symbols) {
        skipWhitespaces(types, symbols);
        return lenBuffer > 0;
    }

    public String next(int[] types, int[] symbols) {
        StringBuilder str = new StringBuilder();
        skipWhitespaces();
        if (lenBuffer <= -1) {
            return null;
        }
        while (true) {
            while (indexBuffer < lenBuffer && isGoodSymbol(buffer[indexBuffer], types, symbols)) {
                str.append(buffer[indexBuffer++]);
            }
            updateIndex();
            if (lenBuffer != 0) {
                return str.toString();
            }
            readInBuffer();
        }
    }
    public String next() {
        return next(new int[0], new int[0]);
    }

    public boolean finished() {
        return lenBuffer <= -1;
    }

    public String nextToLine(int[] types, int[] symbols) {
        if (lenBuffer <= -1 || skipWhitespacesLine(types, symbols)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            while (indexBuffer < lenBuffer && isGoodSymbol(buffer[indexBuffer], types, symbols)) {
                sb.append(buffer[indexBuffer++]);
            }
            updateIndex();
            if (lenBuffer > 0) {
                return sb.toString();
            }
            if (lenBuffer < 0) {
                if (sb.length() == 0) {
                    return null;
                }
                return sb.toString();
            }
            readInBuffer();
        }
    }
    public String nextToLine() {
        return nextToLine(new int[0], new int[0]);
    }

    public String nextWord(){
        return nextToLine(wordTypes, wordSym);
    }

    public String nextInt(){
        return nextToLine(numTypes, numSym);
    }
}