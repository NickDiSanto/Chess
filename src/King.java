import java.util.LinkedList;

public class King extends Piece {

    public King(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int newCoordinate) {
        if (Board.canCastleShort() && newCoordinate - coordinate == 20) {
            for (Piece piece : pieces) {
                if (piece.coordinate == 77 && isWhite) {
                    piece.coordinate = 57;
                } else if (piece.coordinate == 70 && !isWhite) {
                    piece.coordinate = 50;
                }
            }
        } else if (Board.canCastleLong() && coordinate - newCoordinate == 20) {
            for (Piece piece : pieces) {
                if (piece.coordinate == 7 && isWhite) {
                    piece.coordinate = 37;
                } else if (piece.coordinate == 0 && !isWhite) {
                    piece.coordinate = 30;
                }
            }
        } else if ((Math.abs(newCoordinate - coordinate) == 10 || Math.abs(newCoordinate - coordinate) == 1 || Math.abs(newCoordinate - coordinate)
                == 9 || Math.abs(newCoordinate - coordinate) == 11) && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
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

        squares.add(coordinate + 1);
        squares.add(coordinate + 11);
        squares.add(coordinate + 10);
        squares.add(coordinate + 9);
        squares.add(coordinate - 1);
        squares.add(coordinate - 11);
        squares.add(coordinate - 10);
        squares.add(coordinate - 9);

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