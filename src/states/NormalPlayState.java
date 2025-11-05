package main.src.states;

import main.src.main.ChessGame;

/**
 * Here is a single state class. You will need to add
 * more for this assignment. The states are arguably the most
 * important part of the assignment to understand, so please
 * work on implementing the states as reliably as possible.
 */

public class NormalPlayState extends State {

    public NormalPlayState(ChessGame game) {
        super(game);
    }

    @Override
    public String prompt() {
        return "[Normal] " + (game.isWhiteToMove() ? "WHITE" : "BLACK") + " to move > ";
    }

    @Override
    public void handleCommand(String line) {
        var cmd = line.trim().toLowerCase();
        if (cmd.startsWith("move ")) {
            // We don't validate chess rules; just demonstrate behavior
            if (game.tryMove(cmd.substring(5))) {
                game.toggleTurn();
            }
        } else if (cmd.equals("check")) {
            System.out.println("King placed in CHECK.");
            game.setState(new CheckState(game));
        } else if (cmd.equals("resign")) {
            System.out.println((game.isWhiteToMove() ? "WHITE" : "BLACK") + " resigns.");
            game.setState(new GameOverState(game));
        } else if (cmd.equals("help")) {
            System.out.println("Commands: move e2 e4 | check | resign | show | reset | help");
        } else if (cmd.equals("show")) {
            game.printBoard();
        } else if (cmd.equals("reset")) {
            game.setState(new GameStartState(game));
        } else {
            System.out.println("Unknown command. Type 'help'.");
        }
    }
}
