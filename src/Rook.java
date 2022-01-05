import java.util.LinkedList;

public class Rook extends Piece {

    public Rook(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int newCoordinate) {
        if ((Math.abs(newCoordinate - coordinate) % 10 == 0 || Math.abs(newCoordinate - coordinate) < 8) && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
            int numSquaresBetween = Math.abs(newCoordinate - coordinate);
            boolean movingSideways = false;
            boolean movingBackwards = false;

            if (numSquaresBetween > 9) {
                numSquaresBetween /= 10;
                movingSideways = true;
            }
            if (newCoordinate - coordinate < 0)
                movingBackwards = true;
            numSquaresBetween--;

            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingSideways && movingBackwards) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                }
                else if (movingSideways) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                }
                else if (movingBackwards) {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                }
                else {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                }
            }

            if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) != null) {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).isWhite != isWhite) {
                    recentCapture = Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64);
                    Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).capture();
                }
                else {
                    updatePiece();
                    return;
                }
            }
        }
        else {
            updatePiece();
            return;
        }
        coordinate = newCoordinate;
    }

    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        for (int i = 1; i < 8 - coordinate / 10; i++) {
            if (Board.getPiece((coordinate + 10 * i) / 10 * 64, coordinate % 10 * 64) == null)
                squares.add(coordinate + 10 * i);
            else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
                    squares.add(coordinate + 10 * i);
                break;
            }
        }
        for (int i = 1; i <= coordinate / 10; i++) {
            if (Board.getPiece((coordinate - 10 * i) / 10 * 64, coordinate % 10 * 64) == null)
                squares.add(coordinate - 10 * i);
            else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, coordinate % 10 * 64).isWhite != isWhite)
                    squares.add(coordinate - 10 * i);
                break;
            }
        }
        for (int i = 1; i < 8 - coordinate % 10; i++) {
            if (Board.getPiece(coordinate / 10 * 64, (coordinate + i) % 10 * 64) == null)
                squares.add(coordinate + i);
            else {
                if (Board.getPiece(coordinate / 10 * 64, (coordinate + i) % 10 * 64).isWhite != isWhite)
                    squares.add(coordinate + i);
                break;
            }
        }
        for (int i = 1; i <= coordinate % 10; i++) {
            if (Board.getPiece(coordinate / 10 * 64, (coordinate - i) % 10 * 64) == null)
                squares.add(coordinate - i);
            else {
                if (Board.getPiece(coordinate / 10 * 64, (coordinate - i) % 10 * 64).isWhite != isWhite)
                    squares.add(coordinate - i);
                break;
            }
        }

        for (int i = squares.size() - 1; i >= 0; i--) {
            if (squares.get(i) > 77 || squares.get(i) < 0 || squares.get(i) % 10 > 7)
                squares.remove(i);
        }

        return squares;
    }
}