package main.src.states;

import main.src.main.ChessGame;

public abstract class State {

    /** The game context whose behavior is being varied by this State. */
    protected final ChessGame game;

    /**
     * Constructs a State bound to a game context.
     * @param game the ChessGame context
     */
    protected State(ChessGame game) {
        this.game = game;
    }

    /** Called once when the state becomes active. */
    public void enter() { }

    /** Called once when the state is about to be replaced. */
    public void exit() { }

    /**
     * A short message indicating what the player should do in this state.
     * @return prompt text
     */
    public String prompt() {
        return "[" + name() + "] Enter a command: ";
    }

    /**
     * Human-readable state name, used in prompts and logs.
     * @return state name
     */
    public String name() {
        return getClass().getSimpleName();
    }

    /**
     * Handle a single line command. Concrete states override this
     * to implement state-specific behavior and transitions.
     *
     * @param line raw command line from user
     */
    public abstract void handleCommand(String line);
}
