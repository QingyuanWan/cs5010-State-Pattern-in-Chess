package main;

import states.NormalPlayState;
import states.State;
import pieces.Piece; // Need this import to initialize the board

/**
 * The Context class for the State Pattern. It holds the current State
 * and the core game data (like the Board and the current turn).
 * 
 * @author Mohammad Arshan Shaikh
 * @version 11/9/2025
 */
public class ChessGame {

	// Core Game Attributes
	private State state;
	private boolean isWhiteToMove;
	private Piece[][] board;

	/**
	 * Constructs a new ChessGame and initializes the starting state.
	 * The game immediately transitions to a NormalPlayState, which will
	 * then forward to the correct side-specific play state.
	 */
	public ChessGame() {
		// Initialize the game to the starting state (this will immediately
		// transition to NormalPlayWhiteState via GameStartState's enter()
		// method in ChessDriver).
		state = new NormalPlayState(this);
	}

	/**
	 * Changes the current state of the game and automatically calls the
	 * {@code enter()} method on the new state instance.
	 *
	 * @param state The new State object to transition to.
	 */
	public void setState(State state) {
		this.state = state;
		// Automatically call enter on the new state
		// This is necessary for GameStartState to transition immediately.
		state.enter();
	}

	// -- State-Required Methods --

	/**
	 * Gets the current state object of the game.
	 *
	 * @return The current active State.
	 */
	public State getState() {
		return state;
	}

	/**
	 * Checks whose turn it is to move.
	 *
	 * @return {@code true} if it is WHITE's turn, {@code false} if it is BLACK's
	 *         turn.
	 */
	public boolean isWhiteToMove() {
		// isWhiteToMove is used by the state classes to determine the prompt.
		return isWhiteToMove;
	}

	/**
	 * Prints the current arrangement of pieces on the 8x8 board to the console.
	 * If the board has not been initialized (i.e., before the 'reset' command),
	 * it prints a message indicating the game needs to be started.
	 *
	 * @return {@code null} (kept for compatibility with placeholder State return
	 *         types).
	 */
	public Object printBoard() {
		// This is a placeholder for printing the board.
		// The current state classes call this when the user types 'show'.
		System.out.println("-------------------------");
		System.out.println("  A B C D E F G H");
		// Print board for demonstration. Note: We assume the board array is
		// initialized.
		if (board != null) {
			for (int r = 0; r < 8; r++) {
				// Determine rank number (8 down to 1)
				System.out.print((8 - r) + " ");
				for (int c = 0; c < 8; c++) {
					Piece piece = board[r][c];
					// Display piece symbol or a dot for empty square
					System.out.print((piece != null ? piece.symbol() : '.') + " ");
				}
				System.out.println(); // Newline after each rank
			}
		} else {
			System.out.println("Board not yet initialized. Use 'reset' to start a game.");
		}
		System.out.println("  A B C D E F G H");
		System.out.println("-------------------------");
		return null; // Return type of Object is unusual, but kept for compatibility.
	}

	/**
	 * Attempts to execute a move command (e.g., "e2 e4").
	 * In the current demonstration, this method is a placeholder that
	 * simulates a successful move if the format is acceptable and the board is
	 * initialized.
	 *
	 * @param moveCommand The move string provided by the user (e.g., "e2 e4").
	 * @return {@code true} if the move was successfully executed (or simulated),
	 *         {@code false} otherwise.
	 */
	public boolean tryMove(String moveCommand) {
		// This is a placeholder for move logic.
		// It should parse the moveCommand (e.g., "e2 e4") and check legality.
		System.out.println("[GAME LOG] Attempting move: " + moveCommand);
		// For the demo, we always succeed if the command has a space.
		if (moveCommand.trim().contains(" ") && board != null) {
			// In a real game, you would call your Logic.isLegal() here.
			System.out.println("[GAME LOG] Move successful (Demo Mode).");
			return true;
		} else if (board == null) {
			System.out.println("[GAME LOG] Game not started. Use 'reset' command.");
			return false;
		} else {
			System.out.println("[GAME LOG] Invalid move format. Use 'e2 e4'.");
			return false;
		}
	}

	/**
	 * Switches the current player whose turn it is to move.
	 */
	public void toggleTurn() {
		// Used by state classes after a successful move to switch sides.
		isWhiteToMove = !isWhiteToMove;
		System.out.println("[GAME LOG] Turn switched to " + (isWhiteToMove ? "WHITE" : "BLACK"));
	}

	/**
	 * Resets the game to the initial starting position, initializing the 8x8
	 * piece array and setting the turn to WHITE.
	 */
	public void resetPosition() {
		// Used by GameStartState to set up a new game.
		// Set up the board with an 8x8 array of Piece objects.
		this.board = new Piece[8][8];

		// This is a placeholder to demonstrate setup.
		// For a full start position, you'd instantiate pieces like this:
		// board[7][4] = new King(new int[]{7, 4}, "WHITE"); // White King on e1

		// We'll place a sample piece for visual testing
		// board[6][4] = new Pawn(new int[]{6, 4}, "WHITE"); // White Pawn on e2

		System.out.println("[GAME LOG] Board and pieces initialized to start position.");
		isWhiteToMove = true;
	}

	// Each state constructor should pass an object of the chess game through it.
	// The state is modifying the behavior of the core object (the chess game)
	// by overriding what methods do in that state.

	// In each state class, you will need to write methods that will do different
	// actions based on what
	// state the game is in. For example, perhaps a "take turn" method that
	// changes who it prompts to go (player 1 versus player 2) depending
	// on whose turn it is in the game?

	// The state-specific behavior of each method ought to be specified in
	// each state. Reference the video linked in the lesson outline for details.

	// You will invoke the state methods in here as part of a simple loop
	// Refer to how we set up text commands in Assignment 1 to manage a game
	// loop that takes instructions through command-line input.
}