package expression;

public abstract class AbstractExpression implements ExtendedExpression {
    public int priority;

    public abstract String toString();

    public int getPriority() {
        return priority;
    }

    public abstract int hashCode();
}