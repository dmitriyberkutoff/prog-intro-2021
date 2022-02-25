package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(BoardPosition position) {
        while (true) {
            System.out.println();
            System.out.println("Current position");
            System.out.println(position);
            System.out.println("Enter you move for " + position.getTurn());
            try {
                Move move = new Move(in.nextInt() - 1, in.nextInt() - 1, position.getTurn());
                if (position.isValid(move))
                    return move;
                else {
                    System.out.println("Not valid move, try again");
                }
            } catch (InputMismatchException e) {
                in.next();
                in.next();
                System.out.println("Not valid arguments, try again");
            }
        }
    }
}
