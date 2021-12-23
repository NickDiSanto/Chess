import java.util.LinkedList;

public class King extends Piece {

    public King(int coordinate, boolean isWhite, char pieceType, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, pieceType, pieces);
    }

    @Override
    public void move(int coordinate) {
        if ((Math.abs(coordinate - this.coordinate) == 10 || Math.abs(coordinate - this.coordinate) == 1 || Math.abs(coordinate - this.coordinate) == 9 || Math.abs(coordinate - this.coordinate) == 11) && ) { // TODO: only allow movement if there are no pieces inbetween)
            if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null) {
                if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
                    Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
            }
        }
    }
}