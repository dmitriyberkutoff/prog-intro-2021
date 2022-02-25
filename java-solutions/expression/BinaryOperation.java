package expression;

import java.beans.Expression;
import java.math.BigInteger;

public abstract class BinaryOperation extends AbstractExpression {
    private AbstractExpression ex1;
    private AbstractExpression ex2;

    // :NOTE: убрать
    public String toMini = null;
    protected String sym;
    protected int num;
    protected boolean associative;
    protected boolean always = false;

    public BinaryOperation(AbstractExpression ex1, AbstractExpression ex2, int pr) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        priority = pr;
    }

    public boolean isAlways() {
        return always;
    }

    @Override
    public boolean equals(Object ex) {
        if (ex == null || this.getClass() != ex.getClass()) {
            return false;
        }
        final BinaryOperation that = (BinaryOperation) ex;
        return ex1.equals(that.ex1) && ex2.equals(that.ex2);
    }

    public abstract int solve(int x, int y);

    public abstract BigInteger solve(BigInteger x, BigInteger y);

    public int evaluate(int x) {
        return solve(ex1.evaluate(x), ex2.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return solve(ex1.evaluate(x, y, z), ex2.evaluate(x, y, z));
    }

    public BigInteger evaluate(BigInteger x) {

        return solve(ex1.evaluate(x), ex2.evaluate(x));
    }

    @Override
    public int hashCode() {
        return ex1.hashCode() * num + ex2.hashCode();
    }

    @Override
    public String toString() {
        return "(" + ex1.toString() + " " + sym + " " + ex2.toString() + ")";
    }

    @Override
    public String toMiniString() {
        if (toMini == null) {
            String miniExp1 = ex1.toMiniString();
            String miniExp2 = ex2.toMiniString();
            if (ex1.getPriority() > priority) {
                miniExp1 = "(" + miniExp1 + ")";
            }
            if (priority <= ex2.getPriority() && (always || priority < ex2.getPriority() || !associative
                    || ((BinaryOperation) ex2).isAlways())) {
                miniExp2 = "(" + miniExp2 + ")";
            }
            toMini = miniExp1 + " " + sym + " " + miniExp2;
        }
        return toMini;
    }
}
