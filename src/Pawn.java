import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(int coordinate, boolean isWhite, boolean hasMoved, char pieceType, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, pieceType, pieces);
    }

    @Override
    public void move(int coordinate) {
        int movementDirection = 1;
        if (isWhite)
            movementDirection = -1;

        if ((coordinate - this.coordinate == movementDirection &&
                Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) == null) ||
                (coordinate - this.coordinate == (2 * movementDirection) && !this.hasMoved && !this.piecesBetween() &&
                Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) == null)) {
            this.coordinate = coordinate;
            moveSuccessful();
        }
        else if ((coordinate - this.coordinate == (11 * movementDirection) || coordinate - this.coordinate ==
                (-9 * movementDirection)) && Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null
                && Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite) {
            Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
            this.coordinate = coordinate;
            moveSuccessful();
        }
        else
            moveFailed();
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