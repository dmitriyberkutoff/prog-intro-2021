package game;

public class BoardPosition implements Position{
    AbstractBoard board;

    public BoardPosition(AbstractBoard board) {
        this.board = board;
    }

    public Cell getTurn() {
        return board.getTurn();
    }

    public Cell getCell(int row, int col) {
        return board.getCell(row, col);
    }

    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < board.getN()
                && 0 <= move.getCol() && move.getCol() < board.getM()
                && board.getCell(move.getRow(), move.getCol()) == Cell.E
                && board.getTurn() == move.getValue();
    }

    public String toString() {
        return board.toString();
    }
}