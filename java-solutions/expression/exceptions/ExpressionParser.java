package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringSource;

import java.util.NoSuchElementException;

public class ExpressionParser extends BaseParser implements Parser {
    public ExpressionParser() {
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
            return new CheckedNegate(parseUnaryPriority());
        } else if (isDigit()) {
            return new Const(parseInt(false));
        } else if (take('t')) {
            expect('0');
            return new YoungerBit(parseUnaryPriority());
        } else if (take('l')) {
            expect('0');
            return new OlderBit(parseUnaryPriority());
        } else if (take('a')) {
            expect("bs");
            if (!take(' ')) {
                expect('(');
                back();
                return new Abs(parseUnaryPriority());
            } else {
                return new Abs(parseUnaryPriority());
            }
        } else if (take('x')) {
            return new Variable("x");
        } else if (take('y')) {
            return new Variable("y");
        } else if (take('z')) {
            return new Variable("z");
        } else {
            throw new NoSuchElementException("No argument");
        }
    }

    private AbstractExpression parseMaxPriority() {
        AbstractExpression exp = parseUnaryPriority();
        while (true) {
            skipWhitespace();
            if (take('*')) {
                if (take('*')) {
                    try {
                        exp = new Pow(exp, parseUnaryPriority());
                    } catch (NoSuchElementException e) {
                        throw new NoSuchElementException("Expected second argument in power");
                    }
                } else {
                    back();
                    return exp;
                }
            } else if (take('/')) {
                if (take('/')) {
                    try {
                        exp = new Log(exp, parseUnaryPriority());
                    } catch (NoSuchElementException e){
                        throw new NoSuchElementException("Expected second argument in logarithm");
                    }
                } else {
                    back();
                    return exp;
                }
            } else {
                return exp;
            }
        }
    }

    private AbstractExpression parseMidPriority() {
        AbstractExpression exp = parseMaxPriority();
        while (true) {
            skipWhitespace();
            if (take('*')) {
                try {
                    exp = new CheckedMultiply(exp, parseMaxPriority());
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Expected second argument in multiplying");
                }
            } else if (take('/')) {
                try {
                    exp = new CheckedDivide(exp, parseMaxPriority());
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Expected second argument in division");
                }
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
                try {
                    exp = new CheckedAdd(exp, parseMidPriority());
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Expected second argument in adding");
                }
            } else if (take('-')) {
                try {
                    exp = new CheckedSubtract(exp, parseMidPriority());
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Expected second argument in subtracting");
                }
            } else {
                return exp;
            }
        }
    }

    private AbstractExpression parseMinPriority() {
        AbstractExpression exp = parseLowPriority();
        while (true) {
            skipWhitespace();
            if (take('m')) {
                if (take('i')) {
                    expect("n");
                    if (!isDigit()) {
                        try {
                            exp = new Min(exp, parseLowPriority());
                        } catch (NoSuchElementException e) {
                            throw new NoSuchElementException("Expected second argument for min");
                        }
                    } else {
                        expect(' ');
                    }
                } else {
                    expect("ax");
                    if (!isDigit()) {
                        try {
                            exp = new Max(exp, parseLowPriority());
                        } catch (NoSuchElementException e) {
                            throw new NoSuchElementException("Expected second argument for max");
                        }
                    } else {
                        expect(" ");
                    }
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