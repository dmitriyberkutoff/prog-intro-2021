package expression.exceptions;

import expression.*;

import java.math.BigInteger;

public class Abs extends AbstractUnary {
    public Abs(AbstractExpression exp) {
        super(exp);
        sym = "abs";
        num = 431;
    }

    public int count(int val) {
        if (val == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return val < 0 ? -val : val;
    }

    @Override
    public BigInteger evaluate(BigInteger num) {
        BigInteger temp = exp.evaluate(num);
        return temp.compareTo(BigInteger.ZERO) < 0 ? temp.negate() : temp;
    }
}