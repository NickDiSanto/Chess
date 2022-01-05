import java.util.LinkedList;

public class Knight extends Piece {

    public Knight(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                  LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int newCoordinate) {
        if ((Math.abs(newCoordinate - coordinate) == 8 || Math.abs(newCoordinate - coordinate) == 12 || Math.abs(newCoordinate - coordinate)
                == 19 || Math.abs(newCoordinate - coordinate) == 21) && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
            if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) != null) {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).isWhite != isWhite) {
                    recentCapture = Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64);
                    Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).capture();
                } else {
                    updatePiece();
                    return;
                }
            }
        } else {
            updatePiece();
            return;
        }
        coordinate = newCoordinate;
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
                if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64).isWhite == isWhite) {
                    squares.remove(i);
                }
            }
        }
        for (int i = squares.size() - 1; i >= 0; i--) {
            if (squares.get(i) > 77 || squares.get(i) < 0 || squares.get(i) % 10 > 7) {
                squares.remove(i);
            }
        }

        return squares;
    }
}