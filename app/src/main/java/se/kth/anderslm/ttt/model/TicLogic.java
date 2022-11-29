package se.kth.anderslm.ttt.model;

public class TicLogic {

    /*
     Game logic part
     */
    public static final int SIZE = 3;

    public enum Player {CROSS, NONE}

    private Player[][] board;
    private int moves;
    private Player currentPlayer;
    private boolean isDecided;

    public void reset() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                board[r][c] = Player.NONE;
            }
        }
        moves = 0;
        currentPlayer = Player.CROSS;
        isDecided = false;
    }



    public Player[][] getCopyOfBoard() {
        Player[][] copy = new Player[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                copy[r][c] = board[r][c];
            }
        }
        return copy;
    }


    /*
    Singleton part
     */
    public static TicLogic getInstance() {
        if (ticLogic == null) {
            ticLogic = new TicLogic();
        }
        return ticLogic;
    }

    private static TicLogic ticLogic = null;

    private TicLogic() { // NB! Must be private - Singleton implementation
        board = new Player[SIZE][SIZE];
        reset();
    }
}
