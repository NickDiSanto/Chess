import java.util.LinkedList;

public class Rook extends Piece {

    public Rook(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int coord) {
        if ((Math.abs(coord - coordinate) % 10 == 0 || Math.abs(coord - coordinate) < 8) && coord >= 0 && coord <= 77 && coord % 10 <= 7) {
            int numSquaresBetween = Math.abs(coord - coordinate);
            boolean movingSideways = false;
            boolean movingBackwards = false;

            if (numSquaresBetween > 9) {
                numSquaresBetween /= 10;
                movingSideways = true;
            }
            if (coord - coordinate < 0)
                movingBackwards = true;
            numSquaresBetween--;

            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingSideways && movingBackwards) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingSideways) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingBackwards) {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else {
                    if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
            }

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
    public LinkedList<Integer> squaresAttacking() {
        LinkedList<Integer> squares = new LinkedList<>();

        for (int i = 1; i < 8 - coordinate / 10; i++) {
            if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64) == null)
                squares.add(coordinate + 10 * i);
            else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate) % 10 * 64).isWhite != isWhite)
                    squares.add(coordinate + 10 * i);
                break;
            }
        }
        for (int i = 1; i <= coordinate / 10; i++) {
            if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64) == null)
                squares.add(coordinate - 10 * i);
            else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate) % 10 * 64).isWhite != isWhite)
                    squares.add(coordinate - 10 * i);
                break;
            }
        }
        for (int i = 1; i < 8 - coordinate % 10; i++) {
            if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64) == null)
                squares.add(coordinate + i);
            else {
                if (Board.getPiece((coordinate) / 10 * 64, (coordinate + i) % 10 * 64).isWhite != isWhite)
                    squares.add(coordinate + i);
                break;
            }
        }
        for (int i = 1; i <= coordinate % 10; i++) {
            if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64) == null)
                squares.add(coordinate - i);
            else {
                if (Board.getPiece((coordinate) / 10 * 64, (coordinate - i) % 10 * 64).isWhite != isWhite)
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