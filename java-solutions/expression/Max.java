package expression;

import java.math.BigInteger;

public class Max extends BinaryOperation {
    public Max(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2, 40);
        num = 331;
        sym = "max";
        associative = true;
    }

    public int solve(int x, int y) {
        return Math.max(x, y);
    }

    public BigInteger solve(BigInteger x, BigInteger y) {
        throw new IllegalArgumentException("Can not solve for BigInteger");
    }
}