package game;

import java.lang.Math;

public class TicTacToeBoard extends AbstractBoard {
    public TicTacToeBoard(int m, int n, int k) {
        super(m, n, k);
    }

    @Override
    public GameResult makeMove(Move move) {
        if (isNotValid(move)) {
            return GameResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        countE--;
        if (checkWin(move)) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return GameResult.UNKNOWN;
    }

    private boolean checkWin(Move move) {
        return checkRow(move) == GameResult.WIN ||
                checkCol(move) == GameResult.WIN ||
                checkMainDiag(move) == GameResult.WIN ||
                checkSideDiag(move) == GameResult.WIN;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Board").append(System.lineSeparator());
        int maxLen = Integer.toString(Math.max(m, n)).length();
        sb.append(" ".repeat(maxLen));
        for (int i = 1; i <= n; i++) {
            sb.append(" ".repeat(Math.max(0, (maxLen - Integer.toString(i).length()) + 1)));
            sb.append(Integer.toString(i));
        }
        sb.append('\n');
        for (int r = 0; r < m; r++) {
            sb.append(" ".repeat(Math.max(0, maxLen - Integer.toString(r + 1).length())));
            sb.append(r + 1);
            for (Cell cell : field[r]) {
                sb.append(" ".repeat(maxLen));
                sb.append(CELL_TO_STRING.get(cell));
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
