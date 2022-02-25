package game;

public class TwoPlayerGame {
    private AbstractBoard board;
    private boolean log;
    private final Player player1;
    private final Player player2;

    public TwoPlayerGame(Player player1, Player player2, boolean log) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(AbstractBoard board) {
        if (board == null) {
            throw new IllegalArgumentException("Board is null");
        }
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("One of players is null");
        }
        this.board = board;
        while (true) {
            if (board.getK() == 0) {
                return 1;
            }
            final int result1 = makeMove(player1, 1, false);
            if (result1 != -1)  {
                return result1;
            }
            final int result2 = makeMove(player2, 2, false);
            if (result2 != -1)  {
                return result2;
            }
        }
    }

    private int makeMove(Player player, int no, boolean log) {
        final Move move = player.makeMove(board.getPosition());
        final GameResult result = board.makeMove(move);
        if (log) {
            System.out.println();
            log("Player: " + no);
            log(move.toString());
            log(board.toString());
            log("Result: " + result);
        }
        return switch (result) {
            case WIN -> no;
            case LOOSE -> 3 - no;
            case DRAW -> 0;
            case UNKNOWN -> -1;
            default -> throw new AssertionError("Unknown makeMove result " + result);
        };
    }

    private void log(String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
