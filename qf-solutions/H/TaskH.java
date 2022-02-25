import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class TaskH {
    public static void main(String[] args) {
        MyScanner in = new MyScanner(System.in);
        int n = Integer.parseInt(in.next());
        int[] trans = new int[n+1];
        int[] prefSum = new int[n+1];
        int maxtrans = 0;
        int summ = 0;
        for (int i = 1; i <= n; i++) {
            trans[i] = Integer.parseInt(in.next());
            summ += trans[i];
            prefSum[i] = summ;
            if (trans[i] > maxtrans) {
                maxtrans = trans[i];
            }
        }
        int i = 1;
        int cursumm = 0;
        int[] transOfquery = new int[summ+1];
        for (int j = 1; j <= summ; j++) {
            if (j <= cursumm + trans[i]) {
                transOfquery[j] = i;
            } else {
                cursumm += trans[i];
                transOfquery[j] = i + 1;
                i++;
            }
        }
        int q = Integer.parseInt(in.next());
        Map<Integer, Integer> repeat = new LinkedHashMap<>();
        int query;
        for (int j = 0; j < q; j++) {
            int ans = 0;
            query = Integer.parseInt(in.next());
            if (query < maxtrans) {
                System.out.println("Impossible");
            } else if (repeat.containsKey(query)) {
                System.out.println(repeat.get(query));
            } else {
                int curindex = 0;
                while (curindex != n) {
                    curindex = (prefSum[curindex] + query + 1) > summ ? n :
                            transOfquery[prefSum[curindex] + query + 1] - 1;
                    ans++;
                }
                repeat.put(query, ans);
                System.out.println(ans);
            }
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