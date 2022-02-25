package expression;

import java.math.BigInteger;

public class Divide extends BinaryOperation {
    public Divide(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2, 20);
        num = 53;
        sym = "/";
        associative = false;
        always = true;
    }

    public int solve(int x, int y) {
        return x / y;
    }

    public BigInteger solve(BigInteger x, BigInteger y) {
        return x.divide(y);
    }
}