package pieces;

public class King extends Piece {

	int xMovement;
	int yMovement;

	public King(int[] coordinates, String color) {
		super(coordinates, color);

		xMovement = 1;
		yMovement = 1;

	}

	@Override
	public char symbol() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'symbol'");
	}
}
