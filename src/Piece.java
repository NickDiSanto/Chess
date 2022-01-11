import java.util.LinkedList;

public class Piece {
    int xPixel;
    int yPixel;
    int coordinate;
    boolean isWhite;
    boolean canBeEnPassant;
    boolean hasMoved;
    char pieceType;
    LinkedList<Integer> squaresAttacked;
    LinkedList<Integer> legalMoves;
    LinkedList<Piece> pieces;
    Piece recentCapture;

    public Piece(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                 LinkedList<Integer> squaresAttacked, LinkedList<Integer> legalMoves, LinkedList<Piece> pieces) {
        this.coordinate = coordinate;
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
        this.isWhite = isWhite;
        this.hasMoved = hasMoved;
        this.canBeEnPassant = canBeEnPassant;
        this.pieceType = pieceType;
        this.squaresAttacked = squaresAttacked;
        this.legalMoves = legalMoves;
        this.pieces = pieces;

        pieces.add(this);
    }

    public void move (int newCoordinate) {}

    public void capture() {
        pieces.remove(this);
    }

    public LinkedList<Integer> getSquaresAttacked() {
        return null;
    }

    public LinkedList<Integer> getLegalMoves() {
        return getLegalSquaresAttacked();
    }

    public LinkedList<Integer> getLegalSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        recentCapture = null;

        for (int square : squaresAttacked) {
            int initialCoordinate = coordinate;
            move(square);

            if (coordinate != initialCoordinate) {
                boolean pieceChecks = false;
                for (int i = 0; i < pieces.size(); i++) {
                    if (pieces.get(i).isWhite != isWhite) {
                        pieces.get(i).squaresAttacked = pieces.get(i).getSquaresAttacked();
                        if (pieces.get(i).checksKing()) {
                            pieceChecks = true;
                            break;
                        }
                    }
                }
                coordinate = initialCoordinate;

                if (recentCapture != null) {
                    recentCapture.updatePiece();
                    pieces.add(recentCapture);
                    recentCapture = null;
                }

                for (int i = 0; i < pieces.size(); i++) {
                    if (pieces.get(i).isWhite != isWhite) {
                        pieces.get(i).squaresAttacked = pieces.get(i).getSquaresAttacked();
                    }
                }

                if (!pieceChecks) {
                    squares.add(square);
                }
            }
        }

        return squares;
    }

    public boolean checksKing() {
        for (int square : squaresAttacked) {
            if (Board.getPiece(square / 10 * 64, square % 10 * 64) != null) {
                if (Board.getPiece(square / 10 * 64, square % 10 * 64).pieceType == 'K') {
                    return true;
                }
            }
        }
        return false;
    }

    public void updatePiece() {
        xPixel = coordinate / 10 * 64;
        yPixel = coordinate % 10 * 64;
        squaresAttacked = getSquaresAttacked();
    }
}