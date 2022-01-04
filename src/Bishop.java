import java.util.LinkedList;

public class Bishop extends Piece {

    public Bishop(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> friendlyProtected, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, friendlyProtected, pieces);
    }

    @Override
    public void move(int coord) {
        if ((Math.abs(coord - coordinate) % 11 == 0 || Math.abs(coord - coordinate) % 9 == 0) && coord >= 0 && coord <= 77 && coord % 10 <= 7) {
            int numSquaresBetween = Math.abs(coord - coordinate) / 11;
            boolean movingSideways = false;
            boolean movingBackwards = false;

            if (Math.abs(coord - coordinate) % 11 == 0)
                movingSideways = true;
            else
                numSquaresBetween = Math.abs(coord - coordinate) / 9;
            if (coord - coordinate < 0)
                movingBackwards = true;
            numSquaresBetween--;

            for (int i = 1; i <= numSquaresBetween; i++) {
                if (movingSideways && movingBackwards) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingSideways) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else if (movingBackwards) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                        moveFailed();
                        return;
                    }
                }
                else {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
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
    public void getSquaresAttacked() {
        squaresAttacked.clear();

        for (int i = 1; i < 8; i++) {
            if (coordinate + 11 * i > 77 || coordinate + 11 * i < 0 || (coordinate + 11 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate + i) % 10 * 64).isWhite == isWhite)
                        break;
                    squaresAttacked.add(coordinate + 11 * i);
                    break;
                }
            }
            squaresAttacked.add(coordinate + 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate - 9 * i > 77 || coordinate - 9 * i < 0 || (coordinate - 9 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate + i) % 10 * 64).isWhite == isWhite)
                        break;
                    squaresAttacked.add(coordinate - 9 * i);
                    break;
                }
            }
            squaresAttacked.add(coordinate - 9 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate - 11 * i > 77 || coordinate - 11 * i < 0 || (coordinate - 11 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - 10 * i) / 10 * 64, (coordinate - i) % 10 * 64).isWhite == isWhite)
                        break;
                    squaresAttacked.add(coordinate - 11 * i);
                    break;
                }
            }
            squaresAttacked.add(coordinate - 11 * i);
        }
        for (int i = 1; i < 8; i++) {
            if (coordinate + 9 * i > 77 || coordinate + 9 * i < 0 || (coordinate + 9 * i) % 10 > 7)
                break;
            else {
                if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + 10 * i) / 10 * 64, (coordinate - i) % 10 * 64).isWhite == isWhite)
                        break;
                    squaresAttacked.add(coordinate + 9 * i);
                    break;
                }
            }
            squaresAttacked.add(coordinate + 9 * i);
        }
    }

    @Override
    public void getFriendlyProtected() {
        // TODO: Implement
    }
}