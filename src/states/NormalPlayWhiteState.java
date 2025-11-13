package states;

import main.ChessGame;
import pieces.Logic;

/**
 * @author Qingyuan Wan
 * @version 11/05/2025
 *
 *          normal play when it is WHITE's turn.
 *          parses simple terminal commands and transitions to other states.
 */
public class NormalPlayWhiteState extends State {

    /**
     * @param game
     * @return none
     *
     *         constructor
     */
    public NormalPlayWhiteState(ChessGame game) {
        super(game);
    }

    /**
     * @return string
     *         prompt this state
     */
    @Override
    public String prompt() {
        return "[Play] WHITE to move > ";
    }

    /**
     *
     * @param line raw input line from terminal
     * @return none
     *
     *         Handle terminal commands for WHITE's turn.
     */
    @Override
    public void handleCommand(String line) {
        var cmd = line.trim().toLowerCase();

        if (cmd.startsWith("move ")) {
            // for move, take first 5 index
            if (game.tryMove(cmd.substring(5))) {
                // Toggle to opponent and decide next state based on check/checkmate
                game.toggleTurn();
                String toMove = game.isWhiteToMove() ? "WHITE" : "BLACK";
                if (Logic.isCheckmated(game.getBoard(), toMove)) {
                    String winner = game.isWhiteToMove() ? "BLACK" : "WHITE";
                    game.setState(new CheckmateState(game, winner));
                } else if (Logic.isInCheck(game.getBoard(), toMove)) {
                    game.setState(new CheckState(game));
                } else {
                    game.setState(new NormalPlayBlackState(game));
                }
            }
        } else if (cmd.equals("check")) {
            System.out.println("WHITE king placed in CHECK.");
            game.setState(new CheckState(game));
        } else if (cmd.equals("resign")) {
            System.out.println("WHITE resigns.");
            game.setState(new GameOverState(game, cmd));
        } else if (cmd.equals("help")) {
            System.out.println("Commands: move xx xx | check | resign | show | reset | help");
        } else if (cmd.equals("show")) {
            game.printBoard();
        } else if (cmd.equals("reset")) {
            game.setState(new GameStartState(game));
        } else {
            System.out.println("Unknown command. Type 'help'.");
        }
    }
}
