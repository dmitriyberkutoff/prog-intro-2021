package expression.exceptions;

import expression.*;

public class CheckedNegate extends UnaryMinus {
    public CheckedNegate(AbstractExpression exp) {
        super(exp);
    }

    @Override
    public int count(int val) {
        if (val != Integer.MIN_VALUE) {
            return -val;
        } else {
            throw new ArithmeticException("overflow");
        }
    }
}