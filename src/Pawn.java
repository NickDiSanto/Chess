import java.util.LinkedList;

/**
 * The Pawn class is an extension of the Piece class, overriding its methods
 * to implement its own specific functionality.
 *
 * Some unique operations of pawns include only moving forward, pawn promotion,
 * capturing diagonally, moving two squares on the first move, and en passant.
 *
 * @version     18 January 2022
 * @author      Nick DiSanto
 */

public class Pawn extends Piece {

    public Pawn(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Integer> legalMoves, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, legalMoves, pieces);
    }

    /*
     * This method, depending on the new coordinate passed as a parameter, moves
     * the pawn to a new square, determining its new legal moves, squares threatened,
     * and en passant status.
     */
    @Override
    public void move(int newCoordinate) {
        int movementDirection = getMovementDirection();

        // if the given coordinate is a legal move for pawns
        if (newCoordinate - coordinate == movementDirection || newCoordinate - coordinate == 2 * movementDirection
                || newCoordinate - coordinate == -9 * movementDirection
                || newCoordinate - coordinate == 11 * movementDirection) {
            // if the pawn is trying to move one square, return if the move is illegal
            if (newCoordinate - coordinate == movementDirection) {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) != null) {
                    updatePiece();
                    return;
                }
            // if the pawn is trying to move two squares, return if the move is illegal
            } else if (newCoordinate - coordinate == 2 * movementDirection) {
                if (hasMoved || piecesBetween() || Board.getPiece(newCoordinate / 10 * 64,
                        newCoordinate % 10 * 64) != null) {
                    updatePiece();
                    return;
                }
            // if the pawn is trying to capture northeast or southwest, return if the move is illegal
            } else if (newCoordinate - coordinate == -9 * movementDirection) {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) == null) {
                    // in case of en passant
                    if (Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64,
                            coordinate % 10 * 64) != null) {
                        Piece piece = Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64,
                                coordinate % 10 * 64);
                        if (piece.canBeEnPassant) {
                            recentCapture = piece;
                            piece.capture();
                        } else {
                            updatePiece();
                            return;
                        }
                    } else {
                        updatePiece();
                        return;
                    }
                } else {
                    // if square contains opposing piece, capture
                    Piece piece = Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64);
                    if (piece.isWhite != isWhite) {
                        recentCapture = piece;
                        piece.capture();
                    } else {
                        updatePiece();
                        return;
                    }
                }
            // if the pawn is trying to capture northwest or southeast, return if the move is illegal
            } else {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) == null) {
                    // in case of en passant
                    if (Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64,
                            coordinate % 10 * 64) != null) {
                        Piece piece = Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64,
                                coordinate % 10 * 64);
                        if (piece.canBeEnPassant) {
                            recentCapture = piece;
                            piece.capture();
                        } else {
                            updatePiece();
                            return;
                        }
                    } else {
                        updatePiece();
                        return;
                    }
                } else {
                    // if square contains opposing piece, capture
                    Piece piece = Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64);
                    if (piece.isWhite != isWhite) {
                        recentCapture = piece;
                        piece.capture();
                    } else {
                        updatePiece();
                        return;
                    }
                }
            }
        // if the given coordinate is not a legal move for pawns
        } else {
            updatePiece();
            return;
        }
        canBeEnPassant = Math.abs(newCoordinate - coordinate) == 2;     // if pawn is now legal for en passant
        coordinate = newCoordinate;
    }

    // returns whether there is a piece between two squares
    private boolean piecesBetween() {
        if (isWhite) {
            return Board.getPiece((coordinate) / 10 * 64, (coordinate - 1) % 10 * 64) != null;
        }
        return Board.getPiece((coordinate) / 10 * 64, (coordinate + 1) % 10 * 64) != null;
    }

    // returns which direction the pieces will be going, depending on color
    private int getMovementDirection() {
        if (isWhite) {
            return -1;
        }
        return 1;
    }

    // returns all squares the pawn actively threatens
    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();
        int movementDirection = getMovementDirection();

        squares.add(coordinate + 10 + movementDirection);
        squares.add(coordinate - 10 + movementDirection);

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

    // returns all legal moves the pawn can make
    @Override
    public LinkedList<Integer> getLegalMoves() {
        LinkedList<Integer> squares = getLegalSquaresAttacked();
        int movementDirection = getMovementDirection();

        int numSquares = 1;     // how many options the pawn has
        if (!hasMoved) {
            numSquares = 2;
        }
        // tests each square to see if it is a legal move
        for (int i = 1; i <= numSquares; i++) {
            int initialCoordinate = coordinate;
            boolean initialEnPassant = canBeEnPassant;
            move(coordinate + (i * movementDirection));

            if (coordinate != initialCoordinate) {      // in case of successful move
                boolean pieceChecks = false;
                for (int j = 0; j < pieces.size(); j++) {
                    if (pieces.get(j).isWhite != isWhite) {
                        pieces.get(j).squaresAttacked = pieces.get(j).getSquaresAttacked();
                        if (pieces.get(j).checksKing()) {
                            pieceChecks = true;             // if a check was opened up, the move was illegal
                            break;
                        }
                    }
                }
                coordinate = initialCoordinate;
                canBeEnPassant = initialEnPassant;
                for (int j = 0; j < pieces.size(); j++) {
                    if (pieces.get(j).isWhite != isWhite) {
                        pieces.get(j).squaresAttacked = pieces.get(j).getSquaresAttacked();
                    }
                }

                // if the move didn't open up a check, it's legal
                if (!pieceChecks) {
                    squares.add(coordinate + (i * movementDirection));
                }
            }
        }
        return squares;
    }
}