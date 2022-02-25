package expression;

import java.math.BigInteger;

public class Min extends BinaryOperation {
    public Min(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2, 40);
        num = 599;
        sym = "min";
        associative = true;
    }

    public int solve(int x, int y) {
        return Math.min(x, y);
    }

    public BigInteger solve(BigInteger x, BigInteger y) {
        throw new IllegalArgumentException("Can not solve for BigInteger");
    }
}