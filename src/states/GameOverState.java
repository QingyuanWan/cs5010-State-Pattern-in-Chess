package main.src.states;

import main.src.main.ChessGame;

/**
 * @author Qingyuan Wan
 * @version 11/05/2025
 *
 * state after the game has ended (checkmate, resign).
 * commands: show | help | reset | quit.
 */
public class GameOverState extends State {

    private final String reason; // e.g., "checkmate by WHITE", "resigned", etc.


    /**
     * @param game game context
     * @param reason end reason text
     * @return none
     *
     * consturtor
     */
    public GameOverState(ChessGame game, String reason) {
        super(game);
        this.reason = reason;
    }


    /**
     * @return none
     * Prints final message and available commands.
     */
    @Override
    public void enter() {
        System.out.println("[END] Game over: " + reason);
        System.out.println("      Commands: show | help | reset | quit");
    }


    /**
     * @param line raw input line from terminal
     * @return none
     *
     *
     * terminal commands
     */
    @Override
    public void handleCommand(String line) {
        var cmd = line.trim().toLowerCase();

        switch (cmd) {
            case "show" -> game.printBoard();
            case "help" -> System.out.println("""
                    Commands (game over):
                      show  - print final board
                      reset - start a new game
                      quit  - exit
                    """);
            case "reset" -> {
                game.setState(new GameStartState(game));
            }
            case "quit" -> System.out.println("Bye.");
            default -> System.out.println("[END] Invalid. Try: show | help | reset | quit");
        }
    }
}
