import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coordinate) {
        int movementDirection = 1;
        if (isWhite)
            movementDirection = -1;

        if (((coordinate - this.coordinate == movementDirection || (coordinate - this.coordinate == 2 * movementDirection &&
                !hasMoved && !piecesBetween())) && Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) == null)
                && coordinate >= 0 && coordinate <= 77 && coordinate % 10 <= 7) {
            takeAwayEnPassant();
            canBeEnPassant = Math.abs(coordinate - this.coordinate) == 2;
            this.coordinate = coordinate;
            moveSuccessful();
            return;
        }
        else if ((coordinate - this.coordinate == 11 * movementDirection || coordinate - this.coordinate == -9 *
                movementDirection) && Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null &&
                Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite &&
                coordinate >= 0 && coordinate <= 77 && coordinate % 10 <= 7) {
            Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
            this.coordinate = coordinate;
            moveSuccessful();
            takeAwayEnPassant();
            return;
        }
        else if ((coordinate - this.coordinate == 11 * movementDirection || coordinate - this.coordinate == -9 * movementDirection)
                && (Board.getPiece((this.coordinate + 10) / 10 * 64, this.coordinate % 10 * 64) != null ||
                Board.getPiece((this.coordinate - 10) / 10 * 64, this.coordinate % 10 * 64) != null)) {
            if (Board.getPiece((this.coordinate + 10) / 10 * 64, this.coordinate % 10 * 64) != null) {
                if (Board.getPiece((this.coordinate + 10) / 10 * 64, this.coordinate % 10 * 64).canBeEnPassant) {
                    Board.getPiece((this.coordinate + 10) / 10 * 64, this.coordinate % 10 * 64).capture();
                    this.coordinate = coordinate;
                    moveSuccessful();
                    takeAwayEnPassant();
                    return;
                }
            }
            if (Board.getPiece((this.coordinate - 10) / 10 * 64, this.coordinate % 10 * 64) != null) {
                if (Board.getPiece((this.coordinate - 10) / 10 * 64, this.coordinate % 10 * 64).canBeEnPassant) {
                    Board.getPiece((this.coordinate - 10) / 10 * 64, this.coordinate % 10 * 64).capture();
                    this.coordinate = coordinate;
                    moveSuccessful();
                    takeAwayEnPassant();
                    return;
                }
            }
        }
        moveFailed();
    }

    @Override
    public java.util.LinkedList<Integer> squaresAttacking() {
        int movementDirection = 1;
        if (isWhite)
            movementDirection = -1;

        LinkedList<Integer> squares = new LinkedList<>();

        squares.add(this.coordinate + 10 + movementDirection);
        squares.add(this.coordinate - 10 + movementDirection);

        for (int i = squares.size() - 1; i >= 0; i--) {
            if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64) != null) {
                if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64).isWhite == isWhite)
                    squares.remove(i);
            }
        }
        for (int i = squares.size() - 1; i >= 0; i--) {
            if (squares.get(i) > 77 || squares.get(i) < 0 || squares.get(i) % 10 > 7)
                squares.remove(i);
        }

        return squares;
    }

    private boolean piecesBetween() {
        if (isWhite)
            return Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate - 1) % 10 * 64) != null;
        return Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate + 1) % 10 * 64) != null;
    }
}