package expression.parser;

public class BaseParser {
    private static final char END = '\0';
    protected StringSource source = null;
    protected char ch = 0xffff;
    protected char pr = 0xffff;

    public BaseParser(final StringSource source) {
        start(source);
    }

    protected void start(final StringSource source) {
        this.source = source;
        if (source != null) {
            take();
        }
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            pr = ch;
            take();
        }
    }

    protected char take() {
        final char result = ch;
        pr = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean end() {
        return take(END);
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    protected boolean isDigit() {
        return '0' <= ch && ch <= '9';
    }

    protected void back() {
        source.back();
        ch = pr;
    }
}
