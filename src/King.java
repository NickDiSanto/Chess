import java.util.LinkedList;

/**
 * The King class is an extension of the Piece class, overriding its methods
 * to implement its own specific functionality.
 *
 * Some unique operations of kings include short and long castling, evading check,
 * and sensing checkmate.
 *
 * @version     18 January 2022
 * @author      Nick DiSanto
 */

public class King extends Piece {

    public King(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Integer> legalMoves, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, legalMoves, pieces);
    }

    /*
     * This method, depending on the new coordinate passed as a parameter, either castles
     * the king or moves the king to a new square, determining its new legal moves and
     * squares threatened.
     */
    @Override
    public void move(int newCoordinate) {
        if (Board.canCastleShort() && newCoordinate - coordinate == 20) {       // in case of short castling
            for (int i = 0; i < pieces.size(); i++) {
                if (pieces.get(i).coordinate == 77 && isWhite) {
                    pieces.get(i).coordinate = 57;
                } else if (pieces.get(i).coordinate == 70 && !isWhite) {
                    pieces.get(i).coordinate = 50;
                }
            }
        } else if (Board.canCastleLong() && coordinate - newCoordinate == 20) {     // in case of long castling
            for (int i = 0; i < pieces.size(); i++) {
                if (pieces.get(i).coordinate == 7 && isWhite) {
                    pieces.get(i).coordinate = 37;
                } else if (pieces.get(i).coordinate == 0 && !isWhite) {
                    pieces.get(i).coordinate = 30;
                }
            }
        // if the given coordinate is a legal move for kings, return if the move is illegal
        } else if ((Math.abs(newCoordinate - coordinate) == 10 || Math.abs(newCoordinate - coordinate) == 1
                || Math.abs(newCoordinate - coordinate) == 9 || Math.abs(newCoordinate - coordinate) == 11)
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
        // if the given coordinate is not a legal move for kings
        } else {
            updatePiece();
            return;
        }
        coordinate = newCoordinate;
    }

    // returns all squares the king actively threatens
    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        squares.add(coordinate + 1);
        squares.add(coordinate + 11);
        squares.add(coordinate + 10);
        squares.add(coordinate + 9);
        squares.add(coordinate - 1);
        squares.add(coordinate - 11);
        squares.add(coordinate - 10);
        squares.add(coordinate - 9);

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

    // returns all legal moves the king can make
    @Override
    public LinkedList<Integer> getLegalMoves() {
        LinkedList<Integer> squares = getLegalSquaresAttacked();

        // adds squares for castling, if applicable
        if (Board.canCastleShort()) {
            squares.add(coordinate + 20);
        }
        if (Board.canCastleLong()) {
            squares.add(coordinate - 20);
        }
        return squares;
    }
}