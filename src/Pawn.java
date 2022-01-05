import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coord) {
        int movementDirection = 1;
        if (isWhite)
            movementDirection = -1;

        if (((coord - coordinate == movementDirection || (coord - coordinate == 2 * movementDirection && !hasMoved
                && !piecesBetween())) && Board.getPiece(coord / 10 * 64, coord % 10 * 64) == null)
                && coord >= 0 && coord <= 77 && coord % 10 <= 7) {
            canBeEnPassant = Math.abs(coord - coordinate) == 2;
            coordinate = coord;
            return;
        }
        else if (coord - coordinate == 11 * movementDirection || coord - coordinate == -9 * movementDirection) {
            if (Board.getPiece(coord / 10 * 64, coord % 10 * 64) != null) {
                if (Board.getPiece(coord / 10 * 64, coord % 10 * 64).isWhite != isWhite) {
                    recentCapture = Board.getPiece(coord / 10 * 64, coord % 10 * 64);
                    Board.getPiece(coord / 10 * 64, coord % 10 * 64).capture();
                    coordinate = coord;
                    return;
                }
            }
            else if (coord - coordinate == -9 * movementDirection) {
                if (Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).canBeEnPassant) {
                        recentCapture = Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64);
                        Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).capture();
                        coordinate = coord;
                        return;
                    }
                }
            }
            else if (coord - coordinate == 11 * movementDirection) {
                if (Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).canBeEnPassant) {
                        recentCapture = Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64);
                        Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).capture();
                        coordinate = coord;
                        return;
                    }
                }
            }
        }
        updatePiece();
    }

    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        int movementDirection = 1;
        if (isWhite)
            movementDirection = -1;

        squares.add(coordinate + 10 + movementDirection);
        squares.add(coordinate - 10 + movementDirection);

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

    private boolean piecesBetween() {
        if (isWhite)
            return Board.getPiece((coordinate) / 10 * 64, (coordinate - 1) % 10 * 64) != null;
        return Board.getPiece((coordinate) / 10 * 64, (coordinate + 1) % 10 * 64) != null;
    }
}