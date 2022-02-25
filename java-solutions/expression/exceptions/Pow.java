package expression.exceptions;

import expression.*;

import java.math.BigInteger;

public class Pow extends BinaryOperation {
    public Pow(AbstractExpression ex1, AbstractExpression ex2) {
        super(ex1, ex2, 10);
        num = 101;
        sym = "**";
        associative = false;
        always = true;
    }

    public int solve(int x, int y) {
        if (y >= 0 && (x != 0 || y != 0)) {
            if (x == 0 || x == 1) {
                return x;
            } else if (x == -1) {
                return y % 2 == 0 ? 1 : -1;
            } else if (x == Integer.MIN_VALUE) {
                if (y > 1) {
                    throw new ArithmeticException("overflow");
                } else {
                    return y == 0 ? 1 : Integer.MIN_VALUE;
                }
            }
            int temp = 1;
            int tx = x > 0 ? x : -x;
            int i = 0;
            while (i < y && temp <= Integer.MAX_VALUE / tx) {
                temp *= tx;
                i++;
            }
            if (i != y) {
                throw new ArithmeticException("overflow");
            }
            return x < 0 ? (y % 2 == 0 ? temp : -temp) : temp;
        }
        throw new IllegalArgumentException("Second argument must be positive");
    }

    public BigInteger solve(BigInteger x, BigInteger y) {
        if (y.compareTo(BigInteger.ZERO) > 0) {
            BigInteger temp = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(y) < 1; i = i.add(BigInteger.ONE)) {
                temp = temp.multiply(x);
            }
            return temp;
        }
        throw new IllegalArgumentException("Second argument must be positive");
    }
}