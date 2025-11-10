package main;

import states.GameStartState;
import java.util.Scanner;

/**
 * Driver class to run the ChessGame context in a loop,
 * demonstrating the State Pattern by passing terminal commands
 * to the current state's handleCommand method.
 * 
 * @author Mohammad Arshan Shaikh
 * @version 11/9/2025
 */
public class ChessDriver {

	public static void main(String[] args) {
		System.out.println("--- State Pattern Chess Game Driver ---");
		System.out.println("Type 'quit' or 'exit' to end the simulation.");

		// Initial Setup: Create a new game instance
		ChessGame game = new ChessGame();

		// Set the initial state to GameStartState for a clean demo
		game.setState(new GameStartState(game));

		// --- Interactive Game Loop ---
		Scanner scanner = new Scanner(System.in);
		String input;

		// Simulate a game loop using command-line commands to trigger state logic
		while (true) {

			// The current state determines the prompt message
			System.out.print(game.getState().prompt());

			if (scanner.hasNextLine()) {
				input = scanner.nextLine().trim();

				if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
					break;
				}

				// The current state handles the command, including state transitions
				// This is the core of the State Pattern application.
				game.getState().handleCommand(input);
			}
		}

		System.out.println("\n--- Simulation Ended ---");
		scanner.close();
	}
}