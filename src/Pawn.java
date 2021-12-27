import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(int coordinate, boolean isWhite, boolean hasMoved, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coordinate) {
        int movementDirection = 1;
        if (this.isWhite)
            movementDirection = -1;

        if ((coordinate - this.coordinate == movementDirection &&
                Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) == null) ||
                (coordinate - this.coordinate == (2 * movementDirection) && !this.hasMoved && !this.piecesBetween() &&
                Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) == null)) {
            this.coordinate = coordinate;
            moveSuccessful();
            this.squaresAttacked = squaresAttacking();
        }
        else if ((coordinate - this.coordinate == (11 * movementDirection) || coordinate - this.coordinate ==
                (-9 * movementDirection)) && Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64)
                != null && Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite) {
            Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
            this.coordinate = coordinate;
            moveSuccessful();
            this.squaresAttacked = squaresAttacking();
        }
        else
            moveFailed();
    }

    @Override
    public java.util.LinkedList<Integer> squaresAttacking() {
        int movementDirection = 1;
        if (this.isWhite) // TODO: this.isWhite? Also on line 12
            movementDirection = -1;

        LinkedList<Integer> squares = new LinkedList<>();

        squares.add(this.coordinate + 10 + movementDirection);
        squares.add(this.coordinate - 10 + movementDirection);

        for (int i = squares.size() - 1; i >= 0; i--) {
            if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64) != null)
                if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64).isWhite == isWhite)
                    squares.remove(i);
        }

        return squares;
    }

    private void enPassant() {
        // TODO: Capture En Passant
        //  - PRIORITY: LOW
    }

    private boolean piecesBetween() {
        if (isWhite)
            return Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate - 1) % 10 * 64) != null;
        return Board.getPiece((this.coordinate) / 10 * 64, (this.coordinate + 1) % 10 * 64) != null;
    }
}