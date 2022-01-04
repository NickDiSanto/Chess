import java.util.LinkedList;

public class Queen extends Piece {

    public Queen(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> friendlyProtected, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, friendlyProtected, pieces);
    }

    @Override
    public void move(int coord) {
        if ((Math.abs(coord - coordinate) % 11 == 0 || Math.abs(coord - coordinate) % 9 == 0 || Math.abs(coord - coordinate)
                % 10 == 0 || Math.abs(coord - coordinate) < 8) && coord >= 0 && coord <= 77 && coord % 10 <= 7) {
            int numSquaresBetween;
            boolean movingLeft = false;
            boolean movingLeftUp = false;
            boolean movingUp = false;
            boolean movingRightUp = false;
            boolean movingRight = false;
            boolean movingRightDown = false;
            boolean movingDown = false;
            boolean movingLeftDown = false;

            if (Math.abs(coord - coordinate) % 10 == 0) {
                numSquaresBetween = Math.abs(coord - coordinate) / 10;
                if (coord - coordinate > 0)
                    movingRight = true;
                else
                    movingLeft = true;
            }
            else if (Math.abs(coord - coordinate) % 9 == 0) {
                numSquaresBetween = Math.abs(coord - coordinate) / 9;
                if (coord - coordinate > 0)
                    movingRightUp = true;
                else
                    movingLeftDown = true;
            }
            else if (Math.abs(coord - coordinate) % 11 == 0) {
                numSquaresBetween = Math.abs(coord - coordinate) / 11;
                if (coord - coordinate > 0)
                    movingRightDown = true;
                else
                    movingLeftUp = true;
            }
            else {
                numSquaresBetween = Math.abs(coord - coordinate);
                if (coord - coordinate > 0)
                    movingDown = true;
                else
                    movingUp = true;
            }
            numSquaresBetween--;

            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingLeft) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingLeftUp) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingUp) {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingRightUp) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingRight) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingRightDown) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingDown) {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingLeftDown) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
            }

            if (Board.getPiece(coord / 10 * 64, coord % 10 * 64) != null) {
                if (Board.getPiece(coord / 10 * 64, coord % 10 * 64).isWhite != isWhite)
                    Board.getPiece(coord / 10 * 64, coord % 10 * 64).capture();
                else {
                    moveFailed();
                    return;
                }
            }
        }
        else {
            moveFailed();
            return;
        }
        coordinate = coord;
        moveSuccessful();
        takeAwayEnPassant();
    }

    @Override
    public void getSquaresAttacked() {
        squaresAttacked.clear();

        for (int i = 1; i < 8 - coordinate / 10; i++) {
            if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64) == null)
                squaresAttacked.add(coordinate + 10 * i);
            else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64).isWhite != isWhite)
                    squaresAttacked.add(coordinate + 10 * i);
                break;
            }
        }
        for (int i = 1; i <= coordinate / 10; i++) {
            if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64) == null)
                squaresAttacked.add(coordinate - 10 * i);
            else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64).isWhite != isWhite)
                    squaresAttacked.add(coordinate - 10 * i);
                break;
            }
        }
        for (int i = 1; i < 8 - coordinate % 10; i++) {
            if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64) == null)
                squaresAttacked.add(coordinate + i);
            else {
                if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64).isWhite != isWhite)
                    squaresAttacked.add(coordinate + i);
                break;
            }
        }
        for (int i = 1; i <= coordinate % 10; i++) {
            if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64) == null)
                squaresAttacked.add(coordinate - i);
            else {
                if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64).isWhite != isWhite)
                    squaresAttacked.add(coordinate - i);
                break;
            }
        }

        for (int i = 1; i < 8; i++) {
            if (coordinate + 11 * i > 77 || coordinate + 11 * i < 0 || (coordinate + 11 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64).isWhite == isWhite)
                        break;
                    squaresAttacked.add(coordinate + 11 * i);
                    break;
                }
            }
            squaresAttacked.add(coordinate + 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate - 9 * i > 77 || coordinate - 9 * i < 0 || (coordinate - 9 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64).isWhite == isWhite)
                        break;
                    squaresAttacked.add(coordinate - 9 * i);
                    break;
                }
            }
            squaresAttacked.add(coordinate - 9 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate - 11 * i > 77 || coordinate - 11 * i < 0 || (coordinate - 11 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64).isWhite == isWhite)
                        break;
                    squaresAttacked.add(coordinate - 11 * i);
                    break;
                }
            }
            squaresAttacked.add(coordinate - 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate + 9 * i > 77 || coordinate + 9 * i < 0 || (coordinate + 9 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64).isWhite == isWhite)
                        break;
                    squaresAttacked.add(coordinate + 9 * i);
                    break;
                }
            }
            squaresAttacked.add(coordinate + 9 * i);
        }
    }

    @Override
    public void getFriendlyProtected() {
        // TODO: Implement
    }
}