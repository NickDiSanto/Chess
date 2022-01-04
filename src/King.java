import java.util.LinkedList;

public class King extends Piece {

    public King(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Piece> friendlyProtected, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, friendlyProtected, pieces);
    }

    @Override
    public void move(int coord) {
        if (Board.canCastleShort() && coord - coordinate == 20) {
            for (Piece piece : pieces) {
                if (piece.coordinate == 77 && isWhite) {
                    piece.coordinate = 57;
                    piece.moveSuccessful();
                }
                else if (piece.coordinate == 70 && !isWhite) {
                    piece.coordinate = 50;
                    piece.moveSuccessful();
                }
            }
        }
        else if (Board.canCastleLong() && coordinate - coord == 20) {
            for (Piece piece : pieces) {
                if (piece.coordinate == 7 && isWhite) {
                    piece.coordinate = 37;
                    piece.moveSuccessful();
                }
                else if (piece.coordinate == 0 && !isWhite) {
                    piece.coordinate = 30;
                    piece.moveSuccessful();
                }
            }
        }
        else if ((Math.abs(coord - coordinate) == 10 || Math.abs(coord - coordinate) == 1 || Math.abs(coord - coordinate)
                == 9 || Math.abs(coord - coordinate) == 11) && coord >= 0 && coord <= 77 && coord % 10 <= 7) {
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
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        squares.add(coordinate + 1);
        squares.add(coordinate + 11);
        squares.add(coordinate + 10);
        squares.add(coordinate + 9);
        squares.add(coordinate - 1);
        squares.add(coordinate - 11);
        squares.add(coordinate - 10);
        squares.add(coordinate - 9);

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

    @Override
    public LinkedList<Piece> getFriendlyProtected() {
        LinkedList<Piece> protectedPieces = new LinkedList<>();

        for (Piece piece : pieces) {
            if (piece.isWhite == isWhite) {
                if (Math.abs(piece.coordinate - coordinate) == 10 || Math.abs(piece.coordinate - coordinate) == 1 ||
                        Math.abs(piece.coordinate - coordinate) == 9 || Math.abs(piece.coordinate - coordinate) == 11)
                    protectedPieces.add(piece);
            }
        }

        return protectedPieces;
    }
}