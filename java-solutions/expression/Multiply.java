package expression;

import java.math.BigInteger;

public class Multiply extends BinaryOperation {
    public Multiply(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2, 20);
        num = 101;
        sym = "*";
        associative = true;
    }

    public int solve(int x, int y) {
        return x * y;
    }

    public BigInteger solve(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }
}