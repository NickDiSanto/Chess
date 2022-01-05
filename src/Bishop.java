import java.util.LinkedList;

public class Bishop extends Piece {

    public Bishop(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, pieces);
    }

    @Override
    public void move(int newCoordinate) {
        if ((Math.abs(newCoordinate - coordinate) % 11 == 0 || Math.abs(newCoordinate - coordinate) % 9 == 0) && newCoordinate >= 0 && newCoordinate <= 77 && newCoordinate % 10 <= 7) {
            int numSquaresBetween = Math.abs(newCoordinate - coordinate) / 11;
            boolean movingSideways = false;
            boolean movingBackwards = false;

            if (Math.abs(newCoordinate - coordinate) % 11 == 0)
                movingSideways = true;
            else
                numSquaresBetween = Math.abs(newCoordinate - coordinate) / 9;
            if (newCoordinate - coordinate < 0)
                movingBackwards = true;
            numSquaresBetween--;

            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingSideways && movingBackwards) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                }
                else if (movingSideways) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                }
                else if (movingBackwards) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        updatePiece();
                        return;
                    }
                }
                else {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
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

        for (int i = 1; i < 8; i++) {
            if (coordinate + 11 * i > 77 || coordinate + 11 * i < 0 || (coordinate + 11 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64).isWhite == isWhite)
                        break;
                    squares.add(coordinate + 11 * i);
                    break;
                }
            }
            squares.add(coordinate + 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate - 9 * i > 77 || coordinate - 9 * i < 0 || (coordinate - 9 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64).isWhite == isWhite)
                        break;
                    squares.add(coordinate - 9 * i);
                    break;
                }
            }
            squares.add(coordinate - 9 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate - 11 * i > 77 || coordinate - 11 * i < 0 || (coordinate - 11 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64).isWhite == isWhite)
                        break;
                    squares.add(coordinate - 11 * i);
                    break;
                }
            }
            squares.add(coordinate - 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate + 9 * i > 77 || coordinate + 9 * i < 0 || (coordinate + 9 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64).isWhite == isWhite)
                        break;
                    squares.add(coordinate + 9 * i);
                    break;
                }
            }
            squares.add(coordinate + 9 * i);
        }

        return squares;
    }
}