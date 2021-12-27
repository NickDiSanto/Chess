import java.util.LinkedList;

public class Knight extends Piece {

    public Knight(int coordinate, boolean isWhite, boolean hasMoved, char pieceType,
                  LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coordinate) {
        if (Math.abs(coordinate - this.coordinate) == 8 || Math.abs(coordinate - this.coordinate) == 12 ||
                Math.abs(coordinate - this.coordinate) == 19 || Math.abs(coordinate - this.coordinate) == 21) {
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
        this.squaresAttacked = squaresAttacking();
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
            if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64) != null)
                if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64).isWhite == isWhite)
                    squares.remove(i);
        }
        return squares;
    }
}