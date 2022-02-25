package expression.parser;

import expression.*;

import java.util.NoSuchElementException;

public class ExpressionParser extends BaseParser implements Parser {
    public ExpressionParser() {
        // :NOTE: ??
        super(null);
    }

    public AbstractExpression parse(final String exp) {
        start(new StringSource(exp));
        final AbstractExpression expression = parseMinPriority();
        if (!end()) {
            throw new IllegalStateException("Error");
        }
        return expression;
    }

    private AbstractExpression parseUnaryPriority() {
        skipWhitespace();
        if (take('(')) {
            final AbstractExpression exp = parseMinPriority();
            skipWhitespace();
            expect(')');
            return exp;
        } else if (take('-')) {
            if (isDigit()) {
                return new Const(parseInt(true));
            }
            return new UnaryMinus(parseUnaryPriority());
        } else if (isDigit()) {
            return new Const(parseInt(false));
        } else if (take('t')) {
            expect('0');
            return new YoungerBit(parseUnaryPriority());
        } else if (take('l')) {
            expect('0');
            return new OlderBit(parseUnaryPriority());
        } else if (take('x')) {
            return new Variable("x");
        } else if (take('y')) {
            return new Variable("y");
        } else if (take('z')) {
            return new Variable("z");
        } else {
            throw new NoSuchElementException("No such element");
        }
    }

    private AbstractExpression parseMidPriority() {
        AbstractExpression exp = parseUnaryPriority();
        while (true) {
            skipWhitespace();
            if (take('*')) {
                exp = new Multiply(exp, parseUnaryPriority());
            } else if (take('/')) {
                exp = new Divide(exp, parseUnaryPriority());
            } else {
                return exp;
            }
        }
    }

    private AbstractExpression parseLowPriority() {
        AbstractExpression exp = parseMidPriority();
        while (true) {
            skipWhitespace();
            if (take('+')) {
                exp = new Add(exp, parseMidPriority());
            } else if (take('-')) {
                exp = new Subtract(exp, parseMidPriority());
            } else {
                return exp;
            }
        }
    }

    private AbstractExpression parseMinPriority() {
        AbstractExpression exp = parseLowPriority();
        while (true) {
            skipWhitespace();
            // :NOTE: Идентификатор
            if (take('m')) {
                if (take('i')) {
                    expect("n");
                    exp = new Min(exp, parseLowPriority());
                } else {
                    expect("ax");
                    exp = new Max(exp, parseLowPriority());
                }
            } else {
                return exp;
            }
        }
    }

    private int parseInt(final boolean minus) {
        final StringBuilder sb = new StringBuilder();
        if (minus) {
            sb.append("-");
        }
        while (isDigit()) {
            sb.append(take());
        }
        return Integer.parseInt(sb.toString());
    }
}