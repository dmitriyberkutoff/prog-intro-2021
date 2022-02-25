package expression;

import java.math.BigInteger;

public class Variable extends AbstractExpression implements Expression {
    public String variable;

    public Variable(String name) {
        variable = name;
        priority = 1;
    }

    public String getVar() {
        return variable;
    }

    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object ex) {
        if (ex == null) {
            return false;
        }
        return this.getClass() == ex.getClass() && variable.equals(((Variable) ex).getVar());
    }

    @Override
    public int evaluate(int x) {
        if (variable.equals("x")) {
            return x;
        } else {
            throw new UnsupportedOperationException("There is more than 1 variables");
        }
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (variable) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> 0;
        };
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        if (variable.equals("x")) {
            return x;
        } else {
            throw new UnsupportedOperationException("There is more than 1 variables");
        }
    }

    @Override
    public int hashCode() {
        return 199 * variable.hashCode();
    }
}