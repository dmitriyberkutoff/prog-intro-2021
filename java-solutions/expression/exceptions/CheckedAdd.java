package expression.exceptions;

import expression.AbstractExpression;
import expression.Add;

public class CheckedAdd extends Add {
    public CheckedAdd(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2);
    }

    @Override
    public int solve(int x, int y) {
        if (x >= 0 && y >= 0 ?
                Integer.MAX_VALUE - x >= y :
                x >= 0 || y >= 0 || Integer.MIN_VALUE - x <= y) {
            return x + y;
        } else {
            throw new ArithmeticException("overflow");
        }
    }
}