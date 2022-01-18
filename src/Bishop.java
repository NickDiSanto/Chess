import java.util.LinkedList;

/**
 * The Bishop class is an extension of the Piece class, overriding its methods
 * to implement its own specific functionality.
 *
 * Bishops move diagonally across the board as far as they can legally travel.
 *
 * @version     18 January 2022
 * @author      Nick DiSanto
 */

public class Bishop extends Piece {

    public Bishop(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                  LinkedList<Integer> squaresAttacked, LinkedList<Integer> legalMoves, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, legalMoves, pieces);
    }

    /*
     * This method, depending on the new coordinate passed as a parameter, moves the
     * bishop to a new square, determining its new legal moves and squares threatened.
     */
    @Override
    public void move(int newCoordinate) {
        // if the given coordinate is a legal move for bishops
        if ((Math.abs(newCoordinate - coordinate) % 11 == 0 || Math.abs(newCoordinate - coordinate) % 9 == 0)
                && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
            int numSquaresBetween = Math.abs(newCoordinate - coordinate) / 11;      // how many options the bishop has
            boolean movingSideways = false;
            boolean movingBackwards = false;

            // determines if the bishop is going northeast/southwest or northwest/southeast
            if (Math.abs(newCoordinate - coordinate) % 11 == 0) {
                movingSideways = true;
            } else {
                numSquaresBetween = Math.abs(newCoordinate - coordinate) / 9;
            }
            if (newCoordinate - coordinate < 0) {
                movingBackwards = true;
            }
            numSquaresBetween--;

            // tests each square to see if it's a legal move, return if the move is illegal
            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingSideways && movingBackwards) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64,
                            (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingSideways) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64,
                            (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingBackwards) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64,
                            (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64,
                            (coordinate - i) % 10 * 64) != null) {
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
        // if the given coordinate is not a legal move for bishops
        } else {
            updatePiece();
            return;
        }
        coordinate = newCoordinate;
    }

    // returns all squares the bishop actively threatens
    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        // adds squares facing southeast
        for (int i = 1; i < 8; i++) {
            if (coordinate + 11 * i > 77 || coordinate + 11 * i < 0 || (coordinate + 11 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64,
                            (coordinate + i) % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate + 11 * i);
                    break;
                }
            }
            squares.add(coordinate + 11 * i);
        }
        // adds squares facing southwest
        for (int i = 1; i < 8; i++) {
            if (coordinate - 9 * i > 77 || coordinate - 9 * i < 0 || (coordinate - 9 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64,
                            (coordinate + i) % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate - 9 * i);
                    break;
                }
            }
            squares.add(coordinate - 9 * i);
        }
        // adds squares facing northwest
        for (int i = 1; i < 8; i++) {
            if (coordinate - 11 * i > 77 || coordinate - 11 * i < 0 || (coordinate - 11 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64,
                            (coordinate - i) % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate - 11 * i);
                    break;
                }
            }
            squares.add(coordinate - 11 * i);
        }
        // adds squares facing northeast
        for (int i = 1; i < 8; i++) {
            if (coordinate + 9 * i > 77 || coordinate + 9 * i < 0 || (coordinate + 9 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64,
                            (coordinate - i) % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate + 9 * i);
                    break;
                }
            }
            squares.add(coordinate + 9 * i);
        }
        return squares;
    }
}