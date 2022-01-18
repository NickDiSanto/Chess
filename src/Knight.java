import java.util.LinkedList;

/**
 * The Knight class is an extension of the Piece class, overriding its methods
 * to implement its own specific functionality.
 *
 * Knights move in an 'L' shape, unique from any other piece.
 *
 * @version     18 January 2022
 * @author      Nick DiSanto
 */

public class Knight extends Piece {

    public Knight(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                  LinkedList<Integer> squaresAttacked, LinkedList<Integer> legalMoves, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, legalMoves, pieces);
    }

    /*
     * This method, depending on the new coordinate passed as a parameter, moves the
     * knight to a new square, determining its new legal moves and squares threatened.
     */
    @Override
    public void move(int newCoordinate) {
        // if the given coordinate is a legal move for knights, return if the move is illegal
        if ((Math.abs(newCoordinate - coordinate) == 8 || Math.abs(newCoordinate - coordinate) == 12
                || Math.abs(newCoordinate - coordinate) == 19 || Math.abs(newCoordinate - coordinate) == 21)
                && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
            if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) != null) {
                Piece piece = Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64);
                if (piece.isWhite != isWhite) {
                    recentCapture = piece;
                    piece.capture();
                } else {
                    updatePiece();
                    return;
                }
            }
        // if the given coordinate is not a legal move for knights
        } else {
            updatePiece();
            return;
        }
        coordinate = newCoordinate;
    }

    // returns all squares the knight actively threatens
    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        squares.add(coordinate + 8);
        squares.add(coordinate + 12);
        squares.add(coordinate + 19);
        squares.add(coordinate + 21);
        squares.add(coordinate - 8);
        squares.add(coordinate - 12);
        squares.add(coordinate - 19);
        squares.add(coordinate - 21);

        // removes squares if they contain a friendly piece
        for (int i = squares.size() - 1; i >= 0; i--) {
            if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64) != null) {
                if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64).isWhite == isWhite) {
                    squares.remove(i);
                }
            }
        }
        // removes squares if they contain an out-of-bounds square
        for (int i = squares.size() - 1; i >= 0; i--) {
            if (squares.get(i) > 77 || squares.get(i) < 0 || squares.get(i) % 10 > 7) {
                squares.remove(i);
            }
        }
        return squares;
    }
}