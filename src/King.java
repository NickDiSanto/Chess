import java.util.LinkedList;

public class King extends Piece {

    public King(int coordinate, boolean isWhite, boolean hasMoved, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coordinate) {
        if (Math.abs(coordinate - this.coordinate) == 10 || Math.abs(coordinate - this.coordinate) == 1 ||
                Math.abs(coordinate - this.coordinate) == 9 || Math.abs(coordinate - this.coordinate) == 11) {
            if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64) != null) {
                if (Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
                    Board.getPiece(coordinate / 10 * 64, coordinate % 10 * 64).capture();
                else {
                    xPixel = this.coordinate / 10 * 64;
                    yPixel = this.coordinate % 10 * 64;
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