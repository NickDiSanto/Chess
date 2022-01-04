import java.util.LinkedList;

public class Knight extends Piece {

    public Knight(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                  LinkedList<Integer> squaresAttacked, LinkedList<Piece> friendlyProtected, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, friendlyProtected, pieces);
    }

    @Override
    public void move(int coord) {
        if ((Math.abs(coord - coordinate) == 8 || Math.abs(coord - coordinate) == 12 || Math.abs(coord - coordinate)
                == 19 || Math.abs(coord - coordinate) == 21) && coord >= 0 && coord <= 77 && coord % 10 <= 7) {
            if (Board.getPiece(coord / 10 * 64, coord % 10 * 64) != null) {
                if (Board.getPiece(coord / 10 * 64, coord % 10 * 64).isWhite != isWhite)
                    Board.getPiece(coord / 10 * 64, coord % 10 * 64).capture();
                else {
                    moveFailed();
                    return;
                }
            }
        }
        else {
            moveFailed();
            return;
        }
        coordinate = coord;
        moveSuccessful();
        takeAwayEnPassant();
    }

    @Override
    public void getSquaresAttacked() {
        squaresAttacked.clear();

        squaresAttacked.add(coordinate + 8);
        squaresAttacked.add(coordinate + 12);
        squaresAttacked.add(coordinate + 19);
        squaresAttacked.add(coordinate + 21);
        squaresAttacked.add(coordinate - 8);
        squaresAttacked.add(coordinate - 12);
        squaresAttacked.add(coordinate - 19);
        squaresAttacked.add(coordinate - 21);

        for (int i = squaresAttacked.size() - 1; i >= 0; i--) {
            if (Board.getPiece(squaresAttacked.get(i) / 10 * 64, squaresAttacked.get(i) % 10 * 64) != null) {
                if (Board.getPiece(squaresAttacked.get(i) / 10 * 64, squaresAttacked.get(i) % 10 * 64).isWhite == isWhite)
                    squaresAttacked.remove(i);
            }
        }
        for (int i = squaresAttacked.size() - 1; i >= 0; i--) {
            if (squaresAttacked.get(i) > 77 || squaresAttacked.get(i) < 0 || squaresAttacked.get(i) % 10 > 7)
                squaresAttacked.remove(i);
        }
    }

    @Override
    public void getFriendlyProtected() {
        friendlyProtected.clear();

        for (Piece piece : pieces) {
            if (piece.isWhite == isWhite) {
                if (Math.abs(piece.coordinate - coordinate) == 8 || Math.abs(piece.coordinate - coordinate) == 12 ||
                        Math.abs(piece.coordinate - coordinate) == 19 || Math.abs(piece.coordinate - coordinate) == 21)
                    friendlyProtected.add(piece);
            }
        }
    }
}