import java.util.LinkedList;

/**
 * The Queen class is an extension of the Piece class, overriding its methods
 * to implement its own specific functionality.
 *
 * Queens move as a combination of both the bishop and the rook: they can move diagonally
 * across the board, as well as up, down, left, and right as far as they can legally travel.
 *
 * @version     18 January 2022
 * @author      Nick DiSanto
 */


public class Queen extends Piece {

    public Queen(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                 LinkedList<Integer> squaresAttacked, LinkedList<Integer> legalMoves, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, legalMoves, pieces);
    }

    /*
     * This method, depending on the new coordinate passed as a parameter, moves the
     * queen to a new square, determining its new legal moves and squares threatened.
     */
    @Override
    public void move(int newCoordinate) {
        // if the given coordinate is a legal move for queens
        if ((Math.abs(newCoordinate - coordinate) % 11 == 0 || Math.abs(newCoordinate - coordinate) % 9 == 0
                || Math.abs(newCoordinate - coordinate) % 10 == 0 || Math.abs(newCoordinate - coordinate) < 8)
                && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
            int numSquaresBetween;              // how many options the queen has
            boolean movingLeft = false;
            boolean movingLeftUp = false;
            boolean movingUp = false;
            boolean movingRightUp = false;
            boolean movingRight = false;
            boolean movingRightDown = false;
            boolean movingDown = false;
            boolean movingLeftDown = false;

            // determines what direction the queen is going
            if (Math.abs(newCoordinate - coordinate) % 10 == 0) {
                numSquaresBetween = Math.abs(newCoordinate - coordinate) / 10;
                if (newCoordinate - coordinate > 0) {
                    movingRight = true;
                } else {
                    movingLeft = true;
                }
            }
            else if (Math.abs(newCoordinate - coordinate) % 9 == 0) {
                numSquaresBetween = Math.abs(newCoordinate - coordinate) / 9;
                if (newCoordinate - coordinate > 0) {
                    movingRightUp = true;
                } else {
                    movingLeftDown = true;
                }
            } else if (Math.abs(newCoordinate - coordinate) % 11 == 0) {
                numSquaresBetween = Math.abs(newCoordinate - coordinate) / 11;
                if (newCoordinate - coordinate > 0) {
                    movingRightDown = true;
                } else {
                    movingLeftUp = true;
                }
            } else {
                numSquaresBetween = Math.abs(newCoordinate - coordinate);
                if (newCoordinate - coordinate > 0) {
                    movingDown = true;
                } else {
                    movingUp = true;
                }
            }
            numSquaresBetween--;

            // tests each square to see if it's a legal move, return if the move is illegal
            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingLeft) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingLeftUp) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64,
                            (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingUp) {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingRightUp) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64,
                            (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingRight) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingRightDown) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64,
                            (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingDown) {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingLeftDown) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64,
                            (coordinate + i) % 10 * 64) != null) {
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
        // if the given coordinate is not a legal move for queens
        } else {
            updatePiece();
            return;
        }
        coordinate = newCoordinate;
    }

    // returns all squares the queen actively threatens
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