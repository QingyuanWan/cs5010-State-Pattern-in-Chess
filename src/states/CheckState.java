package states;

import main.ChessGame;

/**
 * Check: restricts allowable actions. Demonstrates different behavior vs
 * Normal.
 * 
 * @since 1.0
 */
public class CheckState extends State {

    public CheckState(ChessGame game) {
        super(game);
    }

    @Override
    public String prompt() {
        return "[CHECK] " + (game.isWhiteToMove() ? "WHITE" : "BLACK") + " must respond > ";
    }

    @Override
    public void handleCommand(String line) {
        var cmd = line.trim().toLowerCase();
        switch (cmd) {
            case "show" -> game.printBoard();
            case "help" -> System.out.println("Commands: move <...> | checkmate | resign | show | reset | help");
            case "resign" -> {
                System.out.println((game.isWhiteToMove() ? "WHITE" : "BLACK") + " resigns while in check.");
                game.setState(new GameOverState(game, cmd));
            }
            case "checkmate" -> {
                System.out.println("CHECKMATE declared.");
                game.setState(new CheckmateState(game, cmd));
            }
            case "reset" -> game.setState(new GameStartState(game));
            default -> {
                if (cmd.startsWith("move ")) {
                    // Accept any move string to demo; real legality not required
                    if (game.tryMove(cmd.substring(5))) {
                        System.out.println("Check resolved (for demo).");
                        game.setState(new NormalPlayState(game));
                        game.toggleTurn();
                    }
                } else {
                    System.out.println("In CHECK: move to resolve, 'checkmate' to end, or 'resign'.");
                }
            }
        }
    }
}