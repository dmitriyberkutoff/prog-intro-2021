package expression;

import java.math.BigInteger;

public abstract class AbstractUnary extends AbstractExpression {
    public AbstractExpression exp;
    protected String sym;
    protected int num;
    private String toMini = null;

    public AbstractUnary(AbstractExpression exp) {
        this.exp = exp;
    }

    public AbstractExpression getExp() {
        return exp;
    }

    @Override
    public String toString() {
        return sym + "(" + exp.toString() + ")";
    }

    @Override
    public String toMiniString() {
        if (toMini == null) {
            if (exp.getPriority() <= 1) {
                toMini = sym + " " + exp.toMiniString();
            } else {
                toMini = sym + "(" + exp.toMiniString() + ")";
            }
        }
        return toMini;
    }

    public abstract int count(int x);

    public int evaluate(int x) {
        int val = exp.evaluate(x);
        return count(val);
    }

    public int evaluate(int x, int y, int z) {
        int val = exp.evaluate(x, y, z);
        return count(val);
    }

    public BigInteger evaluate(BigInteger num) {
        throw new IllegalArgumentException("Can not solve for BigInteger");
    }

    @Override
    public boolean equals(Object ex) {
        if (ex == null) {
            return false;
        }
        return this.getClass() == ex.getClass() && exp.equals(((UnaryMinus) ex).getExp());
    }

    @Override
    public int hashCode() {
        return num * exp.hashCode();
    }
}