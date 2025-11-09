package pieces;

/**
 * Abstract base class for all chess pieces.
 * 
 * Uses a matrix-style coordinate system:
 * coordinates[0] = row (0 at top, 7 at bottom)
 * coordinates[1] = column (0 = 'a' file, 7 = 'h' file)
 * 
 * Color is stored as uppercase "WHITE" or "BLACK".
 */
public abstract class Piece {

    protected int[] coordinates;
    protected String color;

    public Piece(int[] coordinates, String color) {
        this.coordinates = coordinates;
        this.color = color.toUpperCase();
    }

    /** Returns the color of this piece ("WHITE" or "BLACK"). */
    public String getColor() {
        return color;
    }

    /** Returns the current row (0–7). */
    public int row() {
        return coordinates[0];
    }

    /** Returns the current column (0–7). */
    public int col() {
        return coordinates[1];
    }

    /** Updates the piece's position on the board. */
    public void setPos(int r, int c) {
        coordinates[0] = r;
        coordinates[1] = c;
    }

    /** Returns the piece’s position as an int[] {row, col}. */
    public int[] getCoordinates() {
        return coordinates;
    }

    /** Each subclass must define its board symbol (e.g., 'K', 'R', 'P'). */
    public abstract char symbol();
}
