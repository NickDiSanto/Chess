import java.util.LinkedList;

public class Rook extends Piece {

    public Rook(int coordinate, boolean isWhite, boolean hasMoved, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coordinate) {
        if (Math.abs(coordinate - this.coordinate) % 10 == 0 || Math.abs(coordinate - this.coordinate) < 8) {
            int numSquaresBetween = Math.abs(coordinate - this.coordinate);
            boolean movingSideways = false;
            boolean movingBackwards = false;
            if (numSquaresBetween > 9) {
                numSquaresBetween /= 10;
                movingSideways = true;
            }
            if (coordinate - this.coordinate < 0)
                movingBackwards = true;
            numSquaresBetween--;

            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingSideways && movingBackwards) {
                    if (Board.getPiece((this.coordinate - 10 * i) / 10 * 64, (this.coordinate) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingSideways) {
                    if (Board.getPiece((this.coordinate + 10 * i) / 10 * 64, (this.coordinate) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingBackwards) {
                    if (Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else {
                    if (Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate + i) % 10 * 64) != null) {
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
        squares.add(this.coordinate + 8);
        squares.add(this.coordinate + 12);
        squares.add(this.coordinate + 19);
        squares.add(this.coordinate + 21);
        squares.add(this.coordinate - 8);
        squares.add(this.coordinate - 12);
        squares.add(this.coordinate - 19);
        squares.add(this.coordinate - 21);

        // TODO: THIS

        for (int i = squares.size() - 1; i >= 0; i--) {
            if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64) != null)
                if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64).isWhite == isWhite)
                    squares.remove(i);
        }

        return squares;
    }
}