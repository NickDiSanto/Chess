import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> friendlyProtected, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, friendlyProtected, pieces);
    }

    @Override
    public void move(int coord) {
        int movementDirection = 1;
        if (isWhite)
            movementDirection = -1;

        if (((coord - coordinate == movementDirection || (coord - coordinate == 2 * movementDirection && !hasMoved
                && !piecesBetween())) && Board.getPiece(coord / 10 * 64, coord % 10 * 64) == null)
                && coord >= 0 && coord <= 77 && coord % 10 <= 7) {
            takeAwayEnPassant();
            canBeEnPassant = Math.abs(coord - coordinate) == 2;
            coordinate = coord;
            moveSuccessful();
            return;
        }
        else if (coord - coordinate == 11 * movementDirection || coord - coordinate == -9 * movementDirection) {
            if (Board.getPiece(coord / 10 * 64, coord % 10 * 64) != null) {
                if (Board.getPiece(coord / 10 * 64, coord % 10 * 64).isWhite != isWhite) {
                    Board.getPiece(coord / 10 * 64, coord % 10 * 64).capture();
                    coordinate = coord;
                    moveSuccessful();
                    takeAwayEnPassant();
                    return;
                }
            }
            else if (coord - coordinate == -9 * movementDirection) {
                if (Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64) != null) {
                    if (Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).canBeEnPassant) {
                        Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).capture();
                        coordinate = coord;
                        moveSuccessful();
                        takeAwayEnPassant();
                        return;
                    }
                }
            }
            else if (coord - coordinate == 11 * movementDirection) {
                if (Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64) != null) {
                    if (Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).canBeEnPassant) {
                        Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).capture();
                        coordinate = coord;
                        moveSuccessful();
                        takeAwayEnPassant();
                        return;
                    }
                }
            }
        }
        moveFailed();
    }

    @Override
    public void getSquaresAttacked() {
        squaresAttacked.clear();

        int movementDirection = 1;
        if (isWhite)
            movementDirection = -1;

        squaresAttacked.add(coordinate + 10 + movementDirection);
        squaresAttacked.add(coordinate - 10 + movementDirection);

        // OPTIMIZE: this could maybe be better, also in king and knight
        for (int i = squaresAttacked.size() - 1; i >= 0; i--) {
            if (Board.getPiece(squaresAttacked.get(i) / 10 * 64, squaresAttacked.get(i) % 10 * 64) != null) {
                if (Board.getPiece(squaresAttacked.get(i) / 10 * 64, squaresAttacked.get(i) % 10 * 64).isWhite == isWhite)
                    squaresAttacked.remove(i);
            }
        }
        for (int i = squaresAttacked.size() - 1; i >= 0; i--) {
            if (squaresAttacked.get(i) > 77 || squaresAttacked.get(i) < 0 || squaresAttacked.get(i) % 10 > 7)
                squaresAttacked.remove(i);
        }
    }

    @Override
    public void getFriendlyProtected() {
        friendlyProtected.clear();

        int movementDirection = 1;
        if (isWhite)
            movementDirection = -1;

        for (Piece piece : pieces) {
            if (piece.isWhite == isWhite) {
                if (piece.coordinate - coordinate == 11 * movementDirection || piece.coordinate - coordinate == -9 * movementDirection)
                    friendlyProtected.add(piece);
            }
        }
    }

    private boolean piecesBetween() {
        if (isWhite)
            return Board.getPiece((coordinate) / 10 * 64, (coordinate - 1) % 10 * 64) != null;
        return Board.getPiece((coordinate) / 10 * 64, (coordinate + 1) % 10 * 64) != null;
    }
}