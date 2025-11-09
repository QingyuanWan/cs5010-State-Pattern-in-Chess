package pieces;

public class Pawn extends Piece {

    int xMovement;
    int yMovement;

    public Pawn(int[] coordinates, String color) {
        super(coordinates, color);

        // Pawns have special conditions for their x and y movement.
        // Those conditions need to be stored somewhere, like a logic class.
        xMovement = 1;
        yMovement = 1;
    }

    /**
     * Returns true if this pawn is on its starting rank and may ATTEMPT a double
     * step.
     * WHITE: row == 6; BLACK: row == 1.
     * Path blocking / legality is handled by Logic.
     */
    public boolean canAttemptDoubleStep() {
        return ("WHITE".equals(color) && row() == 6)
                || ("BLACK".equals(color) && row() == 1);
    }

    @Override
    public char symbol() {
        return "WHITE".equals(color) ? 'P' : 'p';
    }
}
