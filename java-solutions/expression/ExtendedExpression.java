package expression;

public interface ExtendedExpression extends Expression, TripleExpression, BigIntegerExpression {
    int getPriority();
}