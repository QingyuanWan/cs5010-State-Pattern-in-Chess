package pieces;

/**
 * Simple rules for King, Rook, Pawn, and basic check/checkmate detection.
 * - Board is Piece[8][8]
 * - Coordinates: row 0 = rank 8 (top), row 7 = rank 1 (bottom); col 0 = 'a'
 * - WHITE moves "up" (row-1), BLACK moves "down" (row+1)
 * - No castling, en passant, double pawn push, or promotion.
 */
public class Logic {

    private Logic() {
    }

    /** Validate a move like "e2" -> "e4" for the side toMove ("WHITE"/"BLACK"). */
    public static boolean isLegal(Piece[][] board, String from, String to, String toMove) {
        int[] a = parseSquare(from);
        int[] b = parseSquare(to);
        return isLegal(board, a[0], a[1], b[0], b[1], toMove);
    }

    /** Validate a move using row/col, including "no self-check". */
    public static boolean isLegal(Piece[][] board, int fr, int fc, int tr, int tc, String toMove) {
        if (!inside(fr, fc) || !inside(tr, tc))
            return false;

        Piece mover = board[fr][fc];
        if (mover == null)
            return false;
        if (!equalsIgnoreCase(mover.getColor(), toMove))
            return false;

        Piece dest = board[tr][tc];
        if (dest != null && equalsIgnoreCase(dest.getColor(), mover.getColor()))
            return false;

        // Piece movement rules (pseudo-legal)
        if (!pseudoLegal(board, mover, fr, fc, tr, tc))
            return false;

        // Must not leave own king in check
        return !leavesOwnKingInCheck(board, mover, fr, fc, tr, tc);
    }

    /** Is the given color currently in check? */
    public static boolean isInCheck(Piece[][] board, String color) {
        int[] k = findKing(board, color);
        if (k == null)
            return false;
        int kr = k[0], kc = k[1];
        String enemy = enemyOf(color);

        // Is any enemy piece pseudo-legally attacking the king square?
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p == null || !equalsIgnoreCase(p.getColor(), enemy))
                    continue;
                if (pseudoLegal(board, p, r, c, kr, kc))
                    return true;
            }
        }
        return false;
    }

    /** Checkmate = in check and no legal move exists to escape it. */
    public static boolean isCheckmated(Piece[][] board, String color) {
        if (!isInCheck(board, color))
            return false;

        // Try every pseudo-legal move for 'color'; if any resolves check, it's not
        // mate.
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p == null || !equalsIgnoreCase(p.getColor(), color))
                    continue;

                for (int tr = 0; tr < 8; tr++) {
                    for (int tc = 0; tc < 8; tc++) {
                        if (r == tr && c == tc)
                            continue;
                        if (!pseudoLegal(board, p, r, c, tr, tc))
                            continue;
                        if (!leavesOwnKingInCheck(board, p, r, c, tr, tc)) {
                            return false; // found an escape
                        }
                    }
                }
            }
        }
        return true;
    }

    /** Pseudo-legal rules */
    private static boolean pseudoLegal(Piece[][] board, Piece mover, int fr, int fc, int tr, int tc) {
        if (!inside(tr, tc))
            return false;
        Piece dest = board[tr][tc];
        if (dest != null && equalsIgnoreCase(dest.getColor(), mover.getColor()))
            return false;

        switch (Character.toUpperCase(mover.symbol())) {
            case 'R':
                // straight line and path clear
                if (fr != tr && fc != tc)
                    return false;
                return pathClear(board, fr, fc, tr, tc);

            case 'K':
                // one square in any direction
                return Math.max(Math.abs(tr - fr), Math.abs(tc - fc)) == 1;

            case 'P': {
                int dir = isWhite(mover) ? -1 : 1; // WHITE up, BLACK down
                int dr = tr - fr, dc = tc - fc;

                // single forward push
                if (dc == 0 && dest == null && dr == dir)
                    return true;

                // double forward push from starting position
                if (dc == 0 && dest == null && dr == 2 * dir) {
                    // Check if pawn is on starting rank
                    if ((isWhite(mover) && fr == 6) || (!isWhite(mover) && fr == 1)) {
                        // Check path is clear
                        return board[fr + dir][fc] == null;
                    }
                }

                // diagonal capture
                if (Math.abs(dc) == 1 && dr == dir && dest != null &&
                        !equalsIgnoreCase(dest.getColor(), mover.getColor())) {
                    return true;
                }
                return false;
            }
            default:
                // Only King, Rook, and Pawn supported
                return false;
        }
    }

    /** Simulates the move and sees if the mover’s king is in check after it. */
    private static boolean leavesOwnKingInCheck(Piece[][] board, Piece mover, int fr, int fc, int tr, int tc) {
        Piece captured = board[tr][tc];

        board[tr][tc] = mover;
        board[fr][fc] = null;
        int oldR = mover.row(), oldC = mover.col();
        mover.setPos(tr, tc);

        boolean bad = isInCheck(board, mover.getColor());

        // undo
        board[fr][fc] = mover;
        board[tr][tc] = captured;
        mover.setPos(oldR, oldC);

        return bad;
    }

    /**
     * Checks if the path between two squares is clear of other pieces.
     * Used by pieces that move in straight lines (like the rook).
     */
    private static boolean pathClear(Piece[][] board, int fr, int fc, int tr, int tc) {
        int dr = Integer.compare(tr, fr);
        int dc = Integer.compare(tc, fc);
        int r = fr + dr, c = fc + dc;
        while (r != tr || c != tc) {
            if (board[r][c] != null)
                return false;
            r += dr;
            c += dc;
        }
        return true;
    }

    /**
     * Finds the position of the king of the given color on the board.
     */
    private static int[] findKing(Piece[][] board, String color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p != null && Character.toUpperCase(p.symbol()) == 'K'
                        && equalsIgnoreCase(p.getColor(), color)) {
                    return new int[] { r, c };
                }
            }
        }
        return null;
    }

    /** "e2" -> [row,col]; row 0 = rank 8, col 0 = 'a'. */
    public static int[] parseSquare(String alg) {
        alg = alg.trim().toLowerCase();
        int col = alg.charAt(0) - 'a';
        int rankIdx = alg.charAt(1) - '1'; // 0..7
        int row = 7 - rankIdx;
        return new int[] { row, col };
    }

    /** Helpers */
    private static boolean inside(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    private static boolean equalsIgnoreCase(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }

    private static boolean isWhite(Piece p) {
        return equalsIgnoreCase(p.getColor(), "WHITE");
    }

    private static String enemyOf(String color) {
        return equalsIgnoreCase(color, "WHITE") ? "BLACK" : "WHITE";
    }
}
