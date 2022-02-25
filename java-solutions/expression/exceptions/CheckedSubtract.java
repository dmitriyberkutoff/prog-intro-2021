package expression.exceptions;

import expression.*;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2);
    }

    @Override
    public int solve(int x, int y) {
        if (x >= 0 && y < 0 ? y != Integer.MIN_VALUE && Integer.MAX_VALUE - x >= -y :
                y < 0 || x >= 0 || Integer.MIN_VALUE - x <= -y) {
            return x - y;
        } else {
            throw new ArithmeticException("overflow");
        }
    }
}