package main;

import states.NormalPlayState;
import states.State;
import pieces.*;

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

    /** Returns the underlying board array (for rule checks). */
    public Piece[][] getBoard() {
        return board;
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
	 */
	public boolean tryMove(String moveCommand) {
		if (board == null) {
			System.out.println("[GAME LOG] Game not started. Use 'reset' command.");
			return false;
		}

		String[] parts = moveCommand.trim().split("\\s+");
		if (parts.length != 2) {
			System.out.println("[GAME LOG] Invalid move format. Use 'e2 e4'.");
			return false;
		}

		String from = parts[0];
		String to = parts[1];
		String color = isWhiteToMove ? "WHITE" : "BLACK";

		if (!Logic.isLegal(board, from, to, color)) {
			System.out.println("[GAME LOG] Illegal move for " + color + ".");
			return false;
		}

		// Actually move the piece
		int[] a = Logic.parseSquare(from);
		int[] b = Logic.parseSquare(to);
		Piece mover = board[a[0]][a[1]];
		board[b[0]][b[1]] = mover;
		board[a[0]][a[1]] = null;
		if (mover != null) mover.setPos(b[0], b[1]);

		return true;
	}

	// Minimal implementation: sets up pawns, rooks, kings for both sides
	public void resetPosition() {
		board = new Piece[8][8];
		isWhiteToMove = true;

		// Place white pawns
		for (int c = 0; c < 8; c++) {
			board[6][c] = new Pawn(new int[]{6, c}, "WHITE");
		}
		// Place black pawns
		for (int c = 0; c < 8; c++) {
			board[1][c] = new Pawn(new int[]{1, c}, "BLACK");
		}
		// Place rooks
		board[7][0] = new Rook(new int[]{7, 0}, "WHITE");
		board[7][7] = new Rook(new int[]{7, 7}, "WHITE");
		board[0][0] = new Rook(new int[]{0, 0}, "BLACK");
		board[0][7] = new Rook(new int[]{0, 7}, "BLACK");
		// Place kings
		board[7][4] = new King(new int[]{7, 4}, "WHITE");
		board[0][4] = new King(new int[]{0, 4}, "BLACK");
	}

	public void toggleTurn() {
		isWhiteToMove = !isWhiteToMove;
	}
}