import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(int coordinate, boolean isWhite, char pieceType, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, pieceType, pieces);
    }
}