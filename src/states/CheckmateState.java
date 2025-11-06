package main.src.states;

import main.src.main.ChessGame;

/**
 * @author Qingyuan Wan
 * @version 11/05/2025
 *
 * state when a checkmate has been declared.
 * commands: none, go to game over
 */
public class CheckmateState extends State {

    private final String winner; // "WHITE" or "BLACK"


    /**
     * @param game game context
     * @param winner "WHITE" or "BLACK"
     * @return none
     *
     * constructor
     */
    public CheckmateState(ChessGame game, String winner) {
        super(game);
        this.winner = winner;
    }


    /**
     * @return none
     *
     * print reslut and goes gameover
     */
    @Override
    public void enter() {
        String loser = "WHITE".equalsIgnoreCase(winner) ? "BLACK" : "WHITE";
        System.out.println("[CHECKMATE] " + winner + " wins. " + loser + " loses.");
        game.setState(new GameOverState(game, "checkmate by " + winner));
    }


    /**
     * @param line raw input line
     * @return none
     *
     * we should skip this, because it goes to game over
     */
    @Override
    public void handleCommand(String line) {
        System.out.println("Here we should go game over state");
    }


}
