import java.util.LinkedList;

public class Queen extends Piece {

    public Queen(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                 LinkedList<Integer> squaresAttacked, LinkedList<Integer> possibleMoves, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, possibleMoves, pieces);
    }

    @Override
    public void move(int newCoordinate) {
        if ((Math.abs(newCoordinate - coordinate) % 11 == 0 || Math.abs(newCoordinate - coordinate) % 9 == 0
                || Math.abs(newCoordinate - coordinate) % 10 == 0 || Math.abs(newCoordinate - coordinate) < 8)
                && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
            int numSquaresBetween;
            boolean movingLeft = false;
            boolean movingLeftUp = false;
            boolean movingUp = false;
            boolean movingRightUp = false;
            boolean movingRight = false;
            boolean movingRightDown = false;
            boolean movingDown = false;
            boolean movingLeftDown = false;

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

            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingLeft) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingLeftUp) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingUp) {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingRightUp) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingRight) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingRightDown) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingDown) {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                } else if (movingLeftDown) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                }
            }

            if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) != null) {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).isWhite != isWhite) {
                    recentCapture = Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64);
                    Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).capture();
                } else {
                    updatePiece();
                    return;
                }
            }
        } else {
            updatePiece();
            return;
        }
        coordinate = newCoordinate;
    }

    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        for (int i = 1; i < 8 - coordinate / 10; i++) {
            if (Board.getPiece((coordinate + 10 * i) / 10 * 64, coordinate % 10 * 64) == null) {
                squares.add(coordinate + 10 * i);
            } else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, coordinate % 10 * 64).isWhite != isWhite) {
                    squares.add(coordinate + 10 * i);
                }
                break;
            }
        }
        for (int i = 1; i <= coordinate / 10; i++) {
            if (Board.getPiece((coordinate - 10 * i) / 10 * 64, coordinate % 10 * 64) == null) {
                squares.add(coordinate - 10 * i);
            } else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, coordinate % 10 * 64).isWhite != isWhite) {
                    squares.add(coordinate - 10 * i);
                }
                break;
            }
        }
        for (int i = 1; i < 8 - coordinate % 10; i++) {
            if (Board.getPiece(coordinate / 10 * 64, (coordinate + i) % 10 * 64) == null) {
                squares.add(coordinate + i);
            } else {
                if (Board.getPiece(coordinate / 10 * 64, (coordinate + i) % 10 * 64).isWhite != isWhite) {
                    squares.add(coordinate + i);
                }
                break;
            }
        }
        for (int i = 1; i <= coordinate % 10; i++) {
            if (Board.getPiece(coordinate / 10 * 64, (coordinate - i) % 10 * 64) == null) {
                squares.add(coordinate - i);
            } else {
                if (Board.getPiece(coordinate / 10 * 64, (coordinate - i) % 10 * 64).isWhite != isWhite) {
                    squares.add(coordinate - i);
                }
                break;
            }
        }

        for (int i = 1; i < 8; i++) {
            if (coordinate + 11 * i > 77 || coordinate + 11 * i < 0 || (coordinate + 11 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate + 11 * i);
                    break;
                }
            }
            squares.add(coordinate + 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate - 9 * i > 77 || coordinate - 9 * i < 0 || (coordinate - 9 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate - 9 * i);
                    break;
                }
            }
            squares.add(coordinate - 9 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate - 11 * i > 77 || coordinate - 11 * i < 0 || (coordinate - 11 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64).isWhite == isWhite) {
                        break;
                    }
                    squares.add(coordinate - 11 * i);
                    break;
                }
            }
            squares.add(coordinate - 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate + 9 * i > 77 || coordinate + 9 * i < 0 || (coordinate + 9 * i) % 10 > 7) {
                break;
            } else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64).isWhite == isWhite) {
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