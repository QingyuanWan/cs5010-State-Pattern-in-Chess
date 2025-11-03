package main.src.states;

import main.src.main.ChessGame;

public abstract class State {
	
	ChessGame game;
	
	public State(ChessGame game) {
		this.game = game;
	}
	
	//here is where you write the methods you will want overridden by each
	//specific state's behavior
	
}
