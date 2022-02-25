package game;

import java.util.*;

public abstract class AbstractBoard implements Board {
	protected final Map<Cell, String> CELL_TO_STRING = Map.of(
			Cell.X, "X",
			Cell.O, "O",
			Cell.E, "."
	);
	protected int m, n, k, countE;
	protected Cell turn;
	protected Cell[][] field;

	public AbstractBoard(int m, int n, int k) {
		if (notPositive(m, n, k)) {
			throw new IllegalArgumentException("Not positive numbers");
		}
		if (biggerThan(m, n, k)) {
			throw new IllegalArgumentException("k must be less than m and n");
		}
		this.m = m;
		this.n = n;
		this.k = k;
		countE = m*n;
		turn = Cell.X;
		field = new Cell[m][n];
		for (Cell[] row : field) {
			Arrays.fill(row, Cell.E);
		}
	}

	private boolean notPositive(int m, int n, int k) {
		return m <= 0 || n <= 0 || k < 0;
	}

	private boolean biggerThan(int m, int n, int k) {
		return k > Math.max(m, n);
	}

	@Override
	public BoardPosition getPosition() {
		return new BoardPosition(this);
	}
	
	public Cell getTurn() {
		return turn;
	}

	public int getM() {
		return m;
	}

	public int getN() {
		return n;
	}

	public int getK() {
		return k;
	}

	@Override
	public abstract GameResult makeMove(Move move);

	public void clear() {
		for (Cell[] row : field) {
			Arrays.fill(row, Cell.E);
		}
		countE = m*n;
	}

	@Override
	public abstract String toString();
	
	public Cell getCell(int row, int column) {
		return field[row][column];
	}

	protected boolean checkDraw() {
		return countE <= 0;
	}

	public boolean isNotValid(final Move move) {
		return 0 > move.getRow() || move.getRow() >= n
				|| 0 > move.getCol() || move.getCol() >= m
				|| field[move.getRow()][move.getCol()] != Cell.E
				|| turn != move.getValue();
	}

	protected GameResult checkRow(Move move) {
		int counter = 1;
		int row = move.getRow();
		int col = move.getCol();
		for (int i = col - 1; i >= 0; i--) {
			if (field[row][i] == turn) {
				counter++;
			} else {
				break;
			}
			if (counter == k) {
				return GameResult.WIN;
			}
		}
		for (int i = col + 1; i < n; i++) {
			if (field[row][i] == turn) {
				counter++;
			} else {
				break;
			}
			if (counter == k) {
				return GameResult.WIN;
			}
		}
		return GameResult.UNKNOWN;
	}

	protected GameResult checkCol(Move move) {
		int counter = 1;
		int row = move.getRow();
		int col = move.getCol();
		for (int j = row - 1; j >= 0; j--) {
			if (field[j][col] == turn) {
				counter++;
			} else {
				break;
			}
			if (counter == k) {
				return GameResult.WIN;
			}
		}

		for (int j = row + 1; j < m; j++) {
			if (field[j][col] == turn) {
				counter++;
			} else {
				break;
			}
			if (counter  == k) {
				return GameResult.WIN;
			}
		}
		return GameResult.UNKNOWN;
	}

	protected GameResult checkSideDiag(Move move) {
		int counter = 1;
		int row = move.getRow();
		int col = move.getCol();
		int i = row - 1;
		int j = col + 1;
		while (i >= 0 && j < n) {
			if (field[i][j] == turn) {
				counter++;
			} else {
				break;
			}
			if (counter == k) {
				return GameResult.WIN;
			}
			i--;
			j++;
		}
		i = row + 1;
		j = col - 1;
		while (i < m && j >= 0) {
			if (field[i][j] == turn) {
				counter++;
			} else {
				break;
			}
			if (counter == k) {
				return GameResult.WIN;
			}
			i++;
			j--;
		}
		return GameResult.UNKNOWN;
	}

	protected GameResult checkMainDiag(Move move) {
		int counter = 1;
		int row = move.getRow();
		int col = move.getCol();
		int i = row - 1;
		int j = col - 1;
		while (i >= 0 && j >= 0) {
			if (field[i][j] == turn) {
				counter++;
			} else {
				break;
			}
			if (counter == k) {
				return GameResult.WIN;
			}
			i--;
			j--;
		}
		i = row + 1;
		j = col + 1;
		while (i < m && j < n) {
			if (field[i][j] == turn) {
				counter++;
			} else {
				break;
			}
			if (counter == k) {
				return GameResult.WIN;
			}
			i++;
			j++;
		}
		return GameResult.UNKNOWN;
	}

}