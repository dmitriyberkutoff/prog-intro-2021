package expression;

import java.math.BigInteger;

public class OlderBit extends AbstractUnary {
    public OlderBit(AbstractExpression exp) {
        super(exp);
        sym = "l0";
        num = 701;
    }

    public int count(int val) {
        int cnt = 0;
        while (val != 0) {
            val >>>= 1;
            cnt++;
        }
        return 32 - cnt;
    }
}