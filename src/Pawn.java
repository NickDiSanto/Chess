import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(int coordinate, boolean isWhite, char pieceType, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, pieceType, pieces);
    }

    @Override
    public void move(int coordinate) {
        if (coordinate - this.coordinate == 1 || (coordinate - this.coordinate == 2 && this.hasntMoved())) { // TODO: only allow movement if there are no pieces inbetween)
            if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) == null) {
                this.coordinate = coordinate;
                xPixel = coordinate / 10 * 64;
                yPixel = coordinate % 10 * 64;
            }
        }
        else if (coordinate - this.coordinate == 11 || coordinate - this.coordinate == 9)
            Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
    }
}