package expression;

import java.math.BigInteger;

public class Const extends AbstractExpression implements Expression {
    public int cons;
    public BigInteger bigConst = null;

    public Const(int num) {
        cons = num;
        priority = 1;
    }

    public Const(BigInteger num) {
        bigConst = num;
        priority = 1;
    }

    public BigInteger getConst() {
        if (bigConst == null) {
            return new BigInteger(Integer.toString(cons));
        }
        return bigConst;
    }

    public String toString() {
        if (bigConst == null) {
            return Integer.toString(cons);
        }
        return bigConst.toString();
    }

    @Override
    public boolean equals(Object ex) {
        if (ex == null || this.getClass() != ex.getClass()) {
            return false;
        }
        if (bigConst == null) {
            return new BigInteger(Integer.toString(cons)).equals(((Const) ex).getConst());
        }
        return bigConst.equals(((Const) ex).getConst());
    }

    @Override
    public int evaluate(int x) {
        return cons;
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return bigConst;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return cons;
    }

    @Override
    public int hashCode() {
        return bigConst == null ? 137 * cons : 139 * bigConst.hashCode();
    }
}