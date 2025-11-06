package main.src.states;

import main.src.main.ChessGame;
/**
 * @author Qingyuan Wan
 * @version 11/05/2025
 *
 * normal play when it is WHITE's turn.
 * parses simple terminal commands and transitions to other states.
 */
public class NormalPlayWhiteState extends State {

    /**
     * @param game
     * @return none
     *
     * constructor
     */
    public NormalPlayWhiteState(ChessGame game) {
        super(game);
    }

    /**
     * @return string
     * prompt this state
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
     * Handle terminal commands for WHITE's turn.
     */
    @Override
    public void handleCommand(String line) {
        var cmd = line.trim().toLowerCase();

        if (cmd.startsWith("move ")) {
            // plrease implement tryMove and toggleTurn, tryMove use termina input string for move, take first 5 index
            if (game.tryMove(cmd.substring(5))) {
                game.toggleTurn();
                game.setState(new NormalPlayBlackState(game));
            }
        } else if (cmd.equals("check")) {
            System.out.println("WHITE king placed in CHECK.");
            game.setState(new CheckState(game));
        } else if (cmd.equals("resign")) {
            System.out.println("WHITE resigns.");
            game.setState(new GameOverState(game));
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
