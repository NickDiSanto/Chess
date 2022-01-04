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
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        squares.add(coordinate + 8);
        squares.add(coordinate + 12);
        squares.add(coordinate + 19);
        squares.add(coordinate + 21);
        squares.add(coordinate - 8);
        squares.add(coordinate - 12);
        squares.add(coordinate - 19);
        squares.add(coordinate - 21);

        for (int i = squares.size() - 1; i >= 0; i--) {
            if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64) != null) {
                if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64).isWhite == isWhite)
                    squares.remove(i);
            }
        }
        for (int i = squares.size() - 1; i >= 0; i--) {
            if (squares.get(i) > 77 || squares.get(i) < 0 || squares.get(i) % 10 > 7)
                squares.remove(i);
        }

        return squares;
    }

    @Override
    public LinkedList<Piece> getFriendlyProtected() {
        LinkedList<Piece> protectedPieces = new LinkedList<>();

        for (Piece piece : pieces) {
            if (piece.isWhite == isWhite) {
                if (Math.abs(piece.coordinate - coordinate) == 8 || Math.abs(piece.coordinate - coordinate) == 12 ||
                        Math.abs(piece.coordinate - coordinate) == 19 || Math.abs(piece.coordinate - coordinate) == 21)
                    protectedPieces.add(piece);
            }
        }

        return protectedPieces;
    }
}