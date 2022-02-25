package expression.exceptions;

import expression.AbstractExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2);
    }

    @Override
    public int solve(int x, int y) {
        if (x == 0 || y == 0 ||
                (x > 0 && y > 0 ? Integer.MAX_VALUE / x >= y :
                        x < 0 && y < 0 ? x != Integer.MIN_VALUE &&
                                y != Integer.MIN_VALUE &&
                                Integer.MAX_VALUE / (-x) >= (-y) :
                                x > 0 ? Integer.MIN_VALUE / x <= y :
                                        Integer.MIN_VALUE / y <= x
                )) {
            return x * y;
        } else {
            throw new ArithmeticException("overflow");
        }
    }
}