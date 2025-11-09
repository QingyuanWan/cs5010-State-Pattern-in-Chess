package states;

import main.ChessGame;

/**
 * Initializes a fresh game (pieces, board, turn) and moves to NormalPlayState.
 * 
 * @since 1.0
 */
public class GameStartState extends State {

    public GameStartState(ChessGame game) {
        super(game);
    }

    @Override
    public void enter() {
        game.resetPosition(); // sets up board + pieces + whiteToMove = true
        System.out.println("New game initialized. White moves first.");
        game.setState(new NormalPlayState(game));
    }

    @Override
    public void handleCommand(String line) {
        // Usually we auto-transition on enter(); ignore commands here.
        System.out.println("Starting... transitioning to NormalPlay.");
    }
}