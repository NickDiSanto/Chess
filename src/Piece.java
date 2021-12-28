import java.util.LinkedList;

public class Piece {
    int xPixel;
    int yPixel;
    int coordinate;
    boolean isWhite;
    boolean hasMoved;
    char pieceType;
    LinkedList<Integer> squaresAttacked;
    LinkedList<Piece> pieces;

    public Piece(int coordinate, boolean isWhite, boolean hasMoved, char pieceType,
                 LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        this.coordinate = coordinate;
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
        this.isWhite = isWhite;
        this.hasMoved = hasMoved;
        this.pieceType = pieceType;
        this.squaresAttacked = squaresAttacked;
        this.pieces = pieces;
        pieces.add(this);
    }

    public void move (int coordinate) {
        if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null) {
            if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
                Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
            else {
                moveFailed();
                return;
            }
        }
        this.coordinate = coordinate;
        moveSuccessful();
    }

    public void capture() {
        pieces.remove(this);
    }

    public LinkedList<Integer> squaresAttacking() {
        return null;
    }

    public boolean checksKing() {
        for (int square : this.squaresAttacked) {
            if (Board.getPiece(square / 10 * 64, square % 10 * 64) != null) {
                if (Board.getPiece(square / 10 * 64, square % 10
                        * 64).pieceType == 'K')
                    return true;
            }
        }
        return false;
    }

    public void moveSuccessful() {
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
        hasMoved = true;
    }

    public void moveFailed() {
        xPixel = this.coordinate / 10 * 64;
        yPixel = this.coordinate % 10 * 64;
    }
}