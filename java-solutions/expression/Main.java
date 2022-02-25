package expression;

import expression.exceptions.ExpressionParser;
import expression.parser.*;
import expression.exceptions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        AbstractExpression exp = new ExpressionParser().parse("10 / x");
        System.out.println(exp.evaluate(5));
        try {
            System.out.println(exp.evaluate(0));
        } catch (ArithmeticException e) {
            System.out.println(e.toString());
            System.out.println("Caught!!!");
        }
        //System.out.println(exp.toMiniString());
    }
}