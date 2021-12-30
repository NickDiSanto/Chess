import java.util.LinkedList;

public class Bishop extends Piece {

    public Bishop(int coordinate, boolean isWhite, boolean hasMoved, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coordinate) {
        if (Math.abs(coordinate - this.coordinate) % 11 == 0 || Math.abs(coordinate - this.coordinate) % 9 == 0) {
            int numSquaresBetween = Math.abs(coordinate - this.coordinate) / 11;
            boolean movingSideways = false;
            boolean movingBackwards = false;

            if (Math.abs(coordinate - this.coordinate) % 11 == 0)
                movingSideways = true;
            else
                numSquaresBetween = Math.abs(coordinate - this.coordinate) / 9;
            if (coordinate - this.coordinate < 0)
                movingBackwards = true;
            numSquaresBetween--;

            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingSideways && movingBackwards) {
                    if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingSideways) {
                    if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingBackwards) {
                    if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else {
                    if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64) != null) {
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
    }

    @Override
    public LinkedList<Integer> squaresAttacking() {
        LinkedList<Integer> squares = new LinkedList<>();

        for (int i = 1; i < 8; i++) {
            if (this.coordinate + 11 * i > 77 || this.coordinate + 11 * i < 0 || (this.coordinate + 11 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64).isWhite == isWhite)
                        break;
                    squares.add(this.coordinate + 11 * i);
                    break;
                }
            }
            squares.add(this.coordinate + 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (this.coordinate - 9 * i > 77 || this.coordinate - 9 * i < 0 || (this.coordinate - 9 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate + i) % 10 * 64).isWhite == isWhite)
                        break;
                    squares.add(this.coordinate - 9 * i);
                    break;
                }
            }
            squares.add(this.coordinate - 9 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (this.coordinate - 11 * i > 77 || this.coordinate - 11 * i < 0 || (this.coordinate - 11 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64).isWhite == isWhite)
                        break;
                    squares.add(this.coordinate - 11 * i);
                    break;
                }
            }
            squares.add(this.coordinate - 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (this.coordinate + 9 * i > 77 || this.coordinate + 9 * i < 0 || (this.coordinate + 9 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate - i) % 10 * 64).isWhite == isWhite)
                        break;
                    squares.add(this.coordinate + 9 * i);
                    break;
                }
            }
            squares.add(this.coordinate + 9 * i);
        }

        return squares;
    }
}