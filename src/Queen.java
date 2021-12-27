import java.util.LinkedList;

public class Queen extends Piece {

    public Queen(int coordinate, boolean isWhite, boolean hasMoved, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coordinate) {
        if (Math.abs(coordinate - this.coordinate) % 11 == 0 || Math.abs(coordinate - this.coordinate) % 9 == 0 ||
                Math.abs(coordinate - this.coordinate) % 10 == 0 || Math.abs(coordinate - this.coordinate) < 8) {
            int numSquaresBetween = 0;
            boolean movingLeft = false;
            boolean movingLeftUp = false;
            boolean movingUp = false;
            boolean movingRightUp = false;
            boolean movingRight = false;
            boolean movingRightDown = false;
            boolean movingDown = false;
            boolean movingLeftDown = false;
            if (Math.abs(coordinate - this.coordinate) % 10 == 0) {
                numSquaresBetween = Math.abs(coordinate - this.coordinate) / 10;
                if (coordinate - this.coordinate > 0)
                    movingRight = true;
                else
                    movingLeft = true;
            }
            else if (Math.abs(coordinate - this.coordinate) % 9 == 0) {
                numSquaresBetween = Math.abs(coordinate - this.coordinate) / 9;
                if (coordinate - this.coordinate > 0)
                    movingRightUp = true;
                else
                    movingLeftDown = true;
            }
            else if (Math.abs(coordinate - this.coordinate) % 11 == 0) {
                numSquaresBetween = Math.abs(coordinate - this.coordinate) / 11;
                if (coordinate - this.coordinate > 0)
                    movingRightDown = true;
                else
                    movingLeftUp = true;
            }
            else {
                numSquaresBetween = Math.abs(coordinate - this.coordinate);
                if (coordinate - this.coordinate > 0)
                    movingDown = true;
                else
                    movingUp = true;
            }
            numSquaresBetween--;

            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingLeft) {
                    if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingLeftUp) {
                    if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingUp) {
                    if (Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingRightUp) {
                    if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingRight) {
                    if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingRightDown) {
                    if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingDown) {
                    if (Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingLeftDown) {
                    if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
            }

            if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null) {
                if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
                    Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
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
        this.coordinate = coordinate;
        moveSuccessful();
        this.squaresAttacked = squaresAttacking();
    }

    @Override
    public LinkedList<Integer> squaresAttacking() {
        LinkedList<Integer> squares = new LinkedList<>();

        for (int i = 1; i < 8 - this.coordinate / 10; i++) {
            if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate) % 10 * 64) == null)
                squares.add(this.coordinate + 10 * i);
            else {
                if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate) % 10 * 64).isWhite != isWhite)
                    squares.add(this.coordinate + 10 * i);
                break;
            }
        }
        for (int i = 1; i <= this.coordinate / 10; i++) {
            if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate) % 10 * 64) == null)
                squares.add(this.coordinate - 10 * i);
            else {
                if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate) % 10 * 64).isWhite != isWhite)
                    squares.add(this.coordinate - 10 * i);
                break;
            }
        }
        for (int i = 1; i < 8 - this.coordinate % 10; i++) {
            if (Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate + i) % 10 * 64) == null)
                squares.add(this.coordinate + i);
            else {
                if (Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate + i) % 10 * 64).isWhite != isWhite)
                    squares.add(this.coordinate + i);
                break;
            }
        }
        for (int i = 1; i <= this.coordinate % 10; i++) {
            if (Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate - i) % 10 * 64) == null)
                squares.add(this.coordinate - i);
            else {
                if (Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate - i) % 10 * 64).isWhite != isWhite)
                    squares.add(this.coordinate - i);
                break;
            }
        }

        // OPTIMIZE: This probably doesn't have to cycle through all 7 other squares
        //  - PRIORITY: LOW
        for (int i = 1; i < 8; i++) {
            if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64) == null)
                squares.add(this.coordinate + 11 * i);
            else {
                if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64).isWhite != isWhite)
                    squares.add(this.coordinate + 11 * i);
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64) == null)
                squares.add(this.coordinate - 9 * i);
            else {
                if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64).isWhite != isWhite)
                    squares.add(this.coordinate - 9 * i);
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64) == null)
                squares.add(this.coordinate - 11 * i);
            else {
                if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64).isWhite != isWhite)
                    squares.add(this.coordinate - 11 * i);
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64) == null)
                squares.add(this.coordinate + 9 * i);
            else {
                if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64).isWhite != isWhite)
                    squares.add(this.coordinate + 9 * i);
                break;
            }
        }

        for (int i = squares.size() - 1; i >= 0; i--) {
            if (squares.get(i) > 77 || squares.get(i) < 0 || squares.get(i) % 10 > 7 ||
                    (squares.get(i) == 0 && ((this.coordinate == 27) || (this.coordinate == 36) ||
                            (this.coordinate == 45) || (this.coordinate == 54) || (this.coordinate == 63))))
                squares.remove(i);
        }

        return squares;
    }
}