import java.util.LinkedList;

/**
 * The Rook class is an extension of the Piece class, overriding its methods
 * to implement its own specific functionality.
 *
 * Rooks move up, down, left, and right across the board as far as they can legally travel.
 *
 * @version     18 January 2022
 * @author      Nick DiSanto
 */

public class Rook extends Piece {

    public Rook(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Integer> legalMoves, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, legalMoves, pieces);
    }

    /*
     * This method, depending on the new coordinate passed as a parameter, moves the
     * rook to a new square, determining its new legal moves and squares threatened.
     */
    @Override
    public void move(int newCoordinate) {
        // if the given coordinate is a legal move for rooks
        if ((Math.abs(newCoordinate - coordinate) % 10 == 0 || Math.abs(newCoordinate - coordinate) < 8)
                && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
            int numSquaresBetween = Math.abs(newCoordinate - coordinate);       // how many options the rook has
            boolean movingSideways = false;
            boolean movingBackwards = false;

            // determines if the rook is going up/down or left/right
            if (numSquaresBetween > 9) {
                numSquaresBetween /= 10;
                movingSideways = true;
            }
            if (newCoordinate - coordinate < 0) {
                movingBackwards = true;
            }
            numSquaresBetween--;

            // tests each square to see if it's a legal move, return if the move is illegal
            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingSideways && movingBackwards) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingSideways) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingBackwards) {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                }
            }

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
        // if the given coordinate is not a legal move for rooks
        } else {
            updatePiece();
            return;
        }
        coordinate = newCoordinate;
    }

    // returns all squares the rook actively threatens
    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        // adds squares facing right
        for (int i = 1; i < 8 - coordinate / 10; i++) {
            if (coordinate + 10 * i > 77 || coordinate + 10 * i < 0 || (coordinate + 10 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, coordinate % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64,
                            coordinate % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate + 10 * i);
                    break;
                }
            }
            squares.add(coordinate + 10 * i);
        }
        // adds squares facing left
        for (int i = 1; i <= coordinate / 10; i++) {
            if (coordinate - 10 * i > 77 || coordinate - 10 * i < 0 || (coordinate - 10 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, coordinate % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64,
                            coordinate % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate - 10 * i);
                    break;
                }
            }
            squares.add(coordinate - 10 * i);
        }
        // adds squares facing up
        for (int i = 1; i < 8 - coordinate % 10; i++) {
            if (coordinate + i > 77 || coordinate + i < 0 || (coordinate + i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece(coordinate / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece(coordinate / 10 * 64,
                            (coordinate + i) % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate + i);
                    break;
                }
            }
            squares.add(coordinate + i);
        }
        // adds squares facing down
        for (int i = 1; i <= coordinate % 10; i++) {
            if (coordinate - i > 77 || coordinate - i < 0 || (coordinate - i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece(coordinate / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece(coordinate / 10 * 64,
                            (coordinate - i) % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate - i);
                    break;
                }
            }
            squares.add(coordinate - i);
        }
        return squares;
    }
}