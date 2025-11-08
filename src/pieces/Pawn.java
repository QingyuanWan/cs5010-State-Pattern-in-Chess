package main.src.pieces;

public class Pawn extends Piece {

	int xMovement;
	int yMovement;

    // Movement parameters for Logic to consume:
    private static final int FORWARD_STEP = 1;   // one-square push
    private static final int DOUBLE_STEP  = 2;   // potential two-square from start rank
    private static final int CAPTURE_STEP = 1;   // diagonal capture is 1 column offset

	public Pawn(int[] coordinates, String color) {
		super(coordinates, color);
		
		//Pawns have special conditions for their x and y movement.
		//Those conditions need to be stored somewhere, like a logic class.
		xMovement = 1;
		yMovement = 1;
		
	}

    /** WHITE moves up (-1 row), BLACK moves down (+1 row). */
    public int dir() {
        return color.equals("WHITE") ? -1 : 1;
    }

    /** Basic one-step forward distance (row delta magnitude). */
    public int forwardStep() {
        return FORWARD_STEP;
    }

    /** Two-step forward distance from start rank (row delta magnitude). */
    public int doubleStep() {
        return DOUBLE_STEP;
    }

    /** Diagonal capture uses 1 column of offset. */
    public int captureStep() {
        return CAPTURE_STEP;
    }

    /** Conventional board symbol. */
    @Override
    public char symbol() {
        return color.equals("WHITE") ? 'P' : 'p';
    }

}
