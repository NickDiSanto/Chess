import java.util.LinkedList;

public class Piece {
    int xPixel;
    int yPixel;
    int coordinate;
    boolean isWhite;
    boolean canBeEnPassant;
    boolean hasMoved;
    char pieceType;
    LinkedList<Integer> squaresAttacked;
    LinkedList<Piece> pieces;

    public Piece(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                 LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        this.coordinate = coordinate;
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
        this.isWhite = isWhite;
        this.hasMoved = hasMoved;
        this.canBeEnPassant = canBeEnPassant;
        this.pieceType = pieceType;
        this.squaresAttacked = squaresAttacked;
        this.pieces = pieces;

        pieces.add(this);
    }

    public void move (int coord) {}

    public void capture() {
        pieces.remove(this);
    }

    public LinkedList<Integer> getSquaresAttacked() {
        return null;
    }

    public boolean checksKing() {
        for (Integer square : squaresAttacked) {
            if (Board.getPiece(square / 10 * 64, square % 10 * 64) != null) {
                if (Board.getPiece(square / 10 * 64, square % 10 * 64).pieceType == 'K')
                    return true;
            }
        }
        return false;
    }

    public void moveSuccessful() {
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
        hasMoved = true;
        getSquaresAttacked();
    }

    public void moveFailed() {
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
    }

    public void takeAwayEnPassant() {
        for (Piece piece : pieces)
            piece.canBeEnPassant = false;
    }
}