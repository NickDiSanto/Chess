import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Integer> possibleMoves, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, possibleMoves, pieces);
    }

    @Override
    public void move(int newCoordinate) {
        int movementDirection = 1;
        if (isWhite) {
            movementDirection = -1;
        }

        if (((newCoordinate - coordinate == movementDirection || (newCoordinate - coordinate == 2 * movementDirection && !hasMoved
                && !piecesBetween())) && Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) == null)
                && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
            canBeEnPassant = Math.abs(newCoordinate - coordinate) == 2;
            coordinate = newCoordinate;
            return;
        } else if (newCoordinate - coordinate == 11 * movementDirection || newCoordinate - coordinate == -9 * movementDirection) {
            if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) != null) {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).isWhite != isWhite) {
                    recentCapture = Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64);
                    Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).capture();
                    coordinate = newCoordinate;
                    return;
                }
            } else if (newCoordinate - coordinate == -9 * movementDirection) {
                if (Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).canBeEnPassant) {
                        recentCapture = Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64);
                        Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).capture();
                        coordinate = newCoordinate;
                        return;
                    }
                }
            } else if (newCoordinate - coordinate == 11 * movementDirection) {
                if (Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).canBeEnPassant) {
                        recentCapture = Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64);
                        Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).capture();
                        coordinate = newCoordinate;
                        return;
                    }
                }
            }
        }
        updatePiece();
    }

    private boolean piecesBetween() {
        if (isWhite) {
            return Board.getPiece((coordinate) / 10 * 64, (coordinate - 1) % 10 * 64) != null;
        }
        return Board.getPiece((coordinate) / 10 * 64, (coordinate + 1) % 10 * 64) != null;
    }

    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        int movementDirection = 1;
        if (isWhite) {
            movementDirection = -1;
        }

        squares.add(coordinate + 10 + movementDirection);
        squares.add(coordinate - 10 + movementDirection);

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

    @Override
    public LinkedList<Integer> getPossibleMoves() {
        return super.getPossibleMoves();
    }
}