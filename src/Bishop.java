//import java.util.LinkedList;
//
//public class Bishop extends Piece {
//
//    public Bishop(int coordinate, boolean isWhite, char pieceType, LinkedList<Piece> pieces) {
//        super(coordinate, isWhite, pieceType, pieces);
//    }
//
//    @Override
//    public void move(int coordinate) {
//        if ((coordinate - this.coordinate % 9 == 0 || coordinate - this.coordinate % 11 == 0) && ) { // TODO: only allow movement if there are no pieces inbetween)
//            if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null) {
//                if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
//                    Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
//            }
//        }
//    }
//}