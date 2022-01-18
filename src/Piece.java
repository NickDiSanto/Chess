import java.util.LinkedList;

/**
 * The Piece class is an abstract interface after which each individual
 * piece extends to include its own specific functionality.
 *
 * While most methods are overridden, some hold true for specific pieces.
 *
 * @version     18 January 2022
 * @author      Nick DiSanto
 */

public class Piece {
    int xPixel;
    int yPixel;
    int coordinate;
    boolean isWhite;
    boolean canBeEnPassant;
    boolean hasMoved;
    char pieceType;
    LinkedList<Integer> squaresAttacked;
    LinkedList<Integer> legalMoves;
    LinkedList<Piece> pieces;
    Piece recentCapture;

    public Piece(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                 LinkedList<Integer> squaresAttacked, LinkedList<Integer> legalMoves, LinkedList<Piece> pieces) {
        this.coordinate = coordinate;
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
        this.isWhite = isWhite;
        this.hasMoved = hasMoved;
        this.canBeEnPassant = canBeEnPassant;
        this.pieceType = pieceType;
        this.squaresAttacked = squaresAttacked;
        this.legalMoves = legalMoves;
        this.pieces = pieces;

        pieces.add(this);
    }

    // is overridden by each individual piece class
    public void move (int newCoordinate) {
    }

    // removes captured pieces from the list of active pieces
    public void capture() {
        pieces.remove(this);
    }

    // is overridden by each individual piece
    public LinkedList<Integer> getSquaresAttacked() {
        return null;
    }

    // returns the legal moves a piece can make (is overridden by the pawn and king class)
    public LinkedList<Integer> getLegalMoves() {
        return getLegalSquaresAttacked();
    }

    // returns legal squares that piece actively threatens
    public LinkedList<Integer> getLegalSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        recentCapture = null;
        squaresAttacked = getSquaresAttacked();

        // tests each square threatened to see if it is a legal move
        for (int square : squaresAttacked) {
            int initialCoordinate = coordinate;
            move(square);

            if (coordinate != initialCoordinate) {      // in case of successful move
                boolean pieceChecks = false;
                for (int i = 0; i < pieces.size(); i++) {
                    if (pieces.get(i).isWhite != isWhite) {
                        pieces.get(i).squaresAttacked = pieces.get(i).getSquaresAttacked();
                        if (pieces.get(i).checksKing()) {
                            pieceChecks = true;     // if a check was opened up, the move was illegal
                            break;
                        }
                    }
                }
                coordinate = initialCoordinate;

                // if the test move captured a piece, restore and update it
                if (recentCapture != null) {
                    pieces.add(recentCapture);
                    recentCapture.updatePiece();
                    recentCapture = null;
                }

                for (int i = 0; i < pieces.size(); i++) {
                    if (pieces.get(i).isWhite != isWhite) {
                        pieces.get(i).updatePiece();
                    }
                }

                if (!pieceChecks) {
                    squares.add(square);    // if the move didn't open up a check, it's legal
                }
            }
        }
        return squares;
    }

    // returns whether the piece is checking the opposing king
    public boolean checksKing() {
        for (int square : squaresAttacked) {
            if (Board.getPiece(square / 10 * 64, square % 10 * 64) != null) {
                if (Board.getPiece(square / 10 * 64, square % 10 * 64).pieceType == 'K') {
                    return true;
                }
            }
        }
        return false;
    }

    // updates the piece on the board and its squares threatened
    public void updatePiece() {
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
        squaresAttacked = getSquaresAttacked();
    }
}