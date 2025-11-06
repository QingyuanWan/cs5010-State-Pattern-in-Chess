package main.src.states;

import main.src.main.ChessGame;

/**
 * @version 11/05/2025
 *
 * Compatibility shim. Immediately forwards to the correct side-specific play state.
 * Prefer using NormalPlayWhiteState / NormalPlayBlackState directly.
 */
public class NormalPlayState extends State {

    /**
     * @param game game context
     * @return none
     */
    public NormalPlayState(ChessGame game) { super(game); }


    /**
     * Immediately route to WHITE or BLACK play state on activation.
     * @return none
     */
    @Override
    public void enter() {
        if (game.isWhiteToMove()) {
            game.setState(new NormalPlayWhiteState(game));
        } else {
            game.setState(new NormalPlayBlackState(game));
        }
    }

    /**
     * Normally unreachable because enter() forwards right away.
     * For safety, route again on any input.
     *
     * @param line raw input line
     * @return none
     */
    @Override
    public void handleCommand(String line) {
        if (game.isWhiteToMove()) {
            game.setState(new NormalPlayWhiteState(game));
        } else {
            game.setState(new NormalPlayBlackState(game));
        }
    }
}

