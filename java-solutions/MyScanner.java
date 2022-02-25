import java.io.*;

public class MyScanner {
	private Reader in;

	private char[] buffer;
	private final int size = 300;
	private int index;
	private int lenBuffer;
	private final String lineSeparator = System.lineSeparator();

	private final int[] wordTypes = new int[]{Character.LOWERCASE_LETTER,
			Character.UPPERCASE_LETTER,
			Character.MODIFIER_LETTER,
			Character.OTHER_LETTER,
			Character.TITLECASE_LETTER,
			Character.DASH_PUNCTUATION};
	private final int[] wordSym = new int[]{'\''};

	private final int[] numHexTypes = new int[]{Character.DECIMAL_DIGIT_NUMBER,
			Character.UPPERCASE_LETTER,
			Character.LOWERCASE_LETTER};

	private final int[] numTypes = new int[]{Character.DECIMAL_DIGIT_NUMBER};

	private final int[] numSym = new int[]{'-'};

	public MyScanner() {
		this.buffer = new char[size];
		this.index = 0;
		this.lenBuffer = 0;
	}

	public MyScanner(String str) {
		this();
		this.in = new StringReader(str);
	}

	public MyScanner(InputStream in) {
		this();
		this.in = new InputStreamReader(in);
	}

	public MyScanner(FileInputStream file, String code) throws UnsupportedEncodingException {
		this();
		this.in = new InputStreamReader(file, code);
	}

	private void read() {
		if (lenBuffer != 0) {
			return;
		}
		try {
			lenBuffer = in.read(buffer);
		} catch (IOException e) {
			close();
			System.out.println("Cannot read in buffer : " + e.getMessage());
		}
	}

	private boolean isGoodSymbol(char ch, int[] types, int[] symbol) {
		if (Character.isWhitespace(ch)) {
			return false;
		}
		if (symbol.length == 0 && types.length  == 0) {
			return true;
		}
		for (int sym : symbol) {
			if ((int) ch == sym) {
				return true;
			}
		}
		for (int type : types) {
			if (Character.getType(ch) == type) {
				return true;
			}
		}
		return false;
	}

	private boolean isLineSep(char ch, int it) {
		if (lineSeparator.length() <= it) {
			return false;
		}
		return lineSeparator.charAt(it) == ch;
	}

	private void skipSep() {
		int i = 0;
		while (true) {
			while (index < lenBuffer && isLineSep(buffer[index], i)) {
				index++;
				i++;
			}
			update();
			if (lenBuffer != 0) {
				return;
			}
			read();
		}
	}

	private void update() {
		if (index == lenBuffer) {
			index = lenBuffer = 0;
		}
	}

	private void skipWhitespaces() {
		skipWhitespaces(new int[0], new int[0]);
	}

	private void skipWhitespaces(int[] types, int[] symbols) {
		while (true) {
			while (index < lenBuffer && !isGoodSymbol(buffer[index], types, symbols)) {
				index++;
			}
			update();
			if (lenBuffer != 0) {
				return;
			}
			read();
			if (lenBuffer <= -1) {	
				return;
			}
		}
	}

	private boolean skipWhitespacesLine(int[] types, int[] symbols) {
		while (true) {
			while (index < lenBuffer && !isGoodSymbol(buffer[index], types, symbols) && !isLineSep(buffer[index], 0)) {
				index++;
			}
			update();
			if (lenBuffer > 0 && isLineSep(buffer[index], 0)) {
				skipSep();
				return true;
			}
			if (lenBuffer != 0) {
				return false;
			}
			read();
		}
	}

	public boolean hasNext(int[] types, int[] symbols) {
		skipWhitespaces(types, symbols);
		return lenBuffer > 0;
	}

	public boolean hasNextWord() {
		return hasNext(wordTypes, wordSym);
	}

	public String next(int[] types, int[] symbols) {
		StringBuilder str = new StringBuilder();
		skipWhitespaces();
		if (lenBuffer <= -1) {
			return null;
		}
		while (true) {
			while (index < lenBuffer && isGoodSymbol(buffer[index], types, symbols)) {
				str.append(buffer[index++]);
			}
			update();
			if (lenBuffer != 0) {
				return str.toString();
			}
			read();
		}
	}

	public boolean finished() {
		return lenBuffer <= -1;
	}	

	public String nextInLine(int[] types, int[] symbols) {
		if (lenBuffer <= -1 || skipWhitespacesLine(types, symbols)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		while (true) {
			while (index < lenBuffer && isGoodSymbol(buffer[index], types, symbols)) {
				sb.append(buffer[index++]);
			}
			update();
			if (lenBuffer > 0) {
				return sb.toString();
			}
			if (lenBuffer < 0) {
				if (sb.length() == 0) {
					return null;
				} 
				return sb.toString();
			}
			read();
		}
	}
	public String nextInLine() {
		return nextInLine(numTypes, numSym);
	}

	public String nextWord(){
		return next(wordTypes, wordSym);
	}

	public String nextWordToLine(){
		return nextInLine(wordTypes, wordSym);
	}

	public String nextHexInt(){
		return nextInLine(numHexTypes, numSym);
	}

	public void close() {
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
}
