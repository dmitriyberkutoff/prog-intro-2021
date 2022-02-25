package expression;

import java.math.BigInteger;

public class Subtract extends BinaryOperation {
    public Subtract(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2, 30);
        num = 71;
        sym = "-";
        associative = false;
    }

    public int solve(int x, int y) {
        return x - y;
    }

    public BigInteger solve(BigInteger x, BigInteger y) {
        return x.add(y.negate());
    }
}