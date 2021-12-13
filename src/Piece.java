import java.util.LinkedList;

public class Piece {
    int xPixel;
    int yPixel;
    int coordinate;
    boolean isWhite;
    LinkedList<Piece> pieces;
    char pieceType;

    public Piece(int coordinate, boolean isWhite, char pieceType, LinkedList<Piece> pieces) {
        this.coordinate = coordinate;
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
        this.isWhite = isWhite;
        this.pieceType = pieceType;
        this.pieces = pieces;
        pieces.add(this);
    }

    public void move (int coordinate) {
        if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null) {
            if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
                Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
            else {
                xPixel = this.coordinate / 10 * 64;
                yPixel = this.coordinate % 10 * 64;
                return;
            }
        }
        this.coordinate = coordinate;
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
    }

    public void capture() {
        pieces.remove(this);
    }
}