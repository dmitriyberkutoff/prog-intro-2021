package expression;

import java.math.BigInteger;

public class YoungerBit extends AbstractUnary {
    public YoungerBit(AbstractExpression exp) {
        super(exp);
        sym = "t0";
        num = 659;
    }

    public int count(int val) {
        int cnt = 0;
        if (val == 0) {
            return 32;
        }
        while (val % 2 == 0) {
            val /= 2;
            cnt++;
        }
        return cnt;
    }
}