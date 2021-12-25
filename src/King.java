import java.util.LinkedList;

public class King extends Piece {

    public King(int coordinate, boolean isWhite, char pieceType, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, pieceType, pieces);
    }

    @Override
    public void move(int coordinate) {
        if (Math.abs(coordinate - this.coordinate) == 10 || Math.abs(coordinate - this.coordinate) == 1 || Math.abs(coordinate - this.coordinate) == 9 || Math.abs(coordinate - this.coordinate) == 11) {
            if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null) {
                if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
                    Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
                else {
                    xPixel = this.coordinate / 10 * 64;
                    yPixel = this.coordinate % 10 * 64;
                    return;
                }
            }
        }
        else {
            xPixel = this.coordinate / 10 * 64;
            yPixel = this.coordinate % 10 * 64;
            return;
        }
        this.coordinate = coordinate;
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
    }
}