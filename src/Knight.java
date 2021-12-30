import java.util.LinkedList;

public class Knight extends Piece {

    public Knight(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                  LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coordinate) {
        if ((Math.abs(coordinate - this.coordinate) == 8 || Math.abs(coordinate - this.coordinate) == 12 ||
                Math.abs(coordinate - this.coordinate) == 19 || Math.abs(coordinate - this.coordinate) == 21) &&
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

        squares.add(this.coordinate + 8);
        squares.add(this.coordinate + 12);
        squares.add(this.coordinate + 19);
        squares.add(this.coordinate + 21);
        squares.add(this.coordinate - 8);
        squares.add(this.coordinate - 12);
        squares.add(this.coordinate - 19);
        squares.add(this.coordinate - 21);

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