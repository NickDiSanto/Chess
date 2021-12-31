import java.util.LinkedList;

public class King extends Piece {

    public King(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coordinate) {
        if (Board.canCastleShort() && coordinate - this.coordinate == 20) {
            for (Piece piece : pieces) {
                if (piece.coordinate == 77 && isWhite) {
                    piece.coordinate = 57;
                    piece.moveSuccessful();
                }
                else if (piece.coordinate == 70 && !isWhite) {
                    piece.coordinate = 50;
                    piece.moveSuccessful();
                }
            }
        }
        else if (Board.canCastleLong() && this.coordinate - coordinate == 20) {
            for (Piece piece : pieces) {
                if (piece.coordinate == 7 && isWhite) {
                    piece.coordinate = 37;
                    piece.moveSuccessful();
                }
                else if (piece.coordinate == 0 && !isWhite) {
                    piece.coordinate = 30;
                    piece.moveSuccessful();
                }
            }
        }
        else if ((Math.abs(coordinate - this.coordinate) == 10 || Math.abs(coordinate - this.coordinate) == 1 ||
                Math.abs(coordinate - this.coordinate) == 9 || Math.abs(coordinate - this.coordinate) == 11) &&
                coordinate >= 0 && coordinate <= 77 && coordinate % 10 <= 7) {
            if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null) {
                if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
                    Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
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
        this.coordinate = coordinate;
        moveSuccessful();
        takeAwayEnPassant();
    }

    @Override
    public LinkedList<Integer> squaresAttacking() {
        LinkedList<Integer> squares = new LinkedList<>();

        squares.add(this.coordinate + 1);
        squares.add(this.coordinate + 11);
        squares.add(this.coordinate + 10);
        squares.add(this.coordinate + 9);
        squares.add(this.coordinate - 1);
        squares.add(this.coordinate - 11);
        squares.add(this.coordinate - 10);
        squares.add(this.coordinate - 9);

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
}