package expression.exceptions;

import expression.*;
import java.math.BigInteger;

public class CheckedDivide extends Divide {

    public CheckedDivide(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2);
    }

    @Override
    public int solve(int x, int y) {
        if (y == 0 || x == Integer.MIN_VALUE && y == -1) {
            throw new ArithmeticException("division by zero");
        } else {
            return x / y;
        }
    }

    @Override
    public BigInteger solve(BigInteger x, BigInteger y) {
        if (y.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("division by zero");
        } else {
            return x.divide(y);
        }
    }
}