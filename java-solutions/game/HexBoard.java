package game;

public class HexBoard extends TicTacToeBoard {
    public HexBoard(int side, int k) {
        super(side, side, k);
    }

    private boolean checkWin(Move move) {
        return checkRow(move) == GameResult.WIN ||
                checkCol(move) == GameResult.WIN ||
                checkSideDiag(move) == GameResult.WIN;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = -1; i < m - 1; i++) {
            sb.append(" ".repeat(Math.max(0, n - i - Integer.toString(i + 2).length() + 1)));
            sb.append(i + 2).append(" ");
            int k = i;
            int j = 0;
            while (k >= 0 && j < m) {
                sb.append(super.CELL_TO_STRING.get(super.field[k][j])).append(" ");
                --k;
                ++j;
            }
            sb.append(i + 2);
            sb.append("\n");
        }

        for (int i = 0; i < m; ++i) {
            sb.append(" ".repeat(Math.max(0, i + 3)));
            int k = m - 1;
            int j = i;
            while (k >= 0 && j < m) {
                sb.append(CELL_TO_STRING.get(super.field[k][j])).append(" ");
                k--;
                j++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
