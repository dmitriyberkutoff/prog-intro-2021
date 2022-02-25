package expression;

import java.math.BigInteger;

public class UnaryMinus extends AbstractUnary {
    public UnaryMinus(AbstractExpression exp) {
        super(exp);
        sym = "-";
        num = 907;
    }

    public int count(int val) {
        return -val;
    }

    @Override
    public BigInteger evaluate(BigInteger num) {
        return exp.evaluate(num).negate();
    }
}