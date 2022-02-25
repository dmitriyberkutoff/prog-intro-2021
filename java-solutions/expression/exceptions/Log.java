package expression.exceptions;

import expression.*;

import java.math.BigInteger;

public class Log extends BinaryOperation {
    public Log(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2, 10);
        num = 101;
        sym = "//";
        associative = false;
        always = false;
    }

    public int solve(int x, int y) {
        if (x > 0 && y > 0 && y != 1) {
            int temp = 1;
            int cnt = 0;
            while (temp <= x) {
                if (Integer.MAX_VALUE / y < temp) {
                    cnt++;
                    break;
                }
                temp *= y;
                cnt++;
            }
            return cnt - 1;
        }
        throw new IllegalArgumentException("Negative numbers in log or 1 in base");
    }

    public BigInteger solve(BigInteger x, BigInteger y) {
        if (x.compareTo(BigInteger.ZERO) > 0 &&
                y.compareTo(BigInteger.ONE) > 0) {
            BigInteger temp = BigInteger.ONE;
            BigInteger cnt = BigInteger.ZERO;
            while (temp.compareTo(x) < 1) {
                temp = temp.multiply(y);
                cnt = cnt.add(BigInteger.ONE);
            }
            return cnt.add(BigInteger.ONE.negate());
        } else {
            throw new IllegalArgumentException("Negative numbers in log or 1 in base");
        }
    }
}