package expression;

import java.math.BigInteger;

public class Add extends BinaryOperation {
    public Add(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2, 30);
        num = 109;
        sym = "+";
        associative = true;
    }

    public int solve(int x, int y) {
        return x + y;
    }

    public BigInteger solve(BigInteger x, BigInteger y) {
        return x.add(y);
    }
}