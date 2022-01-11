import java.util.LinkedList;

public class Pawn extends Piece {

    public int movementDirection;

    public Pawn(int coordinate, boolean isWhite, boolean hasMoved, boolean canBeEnPassant, char pieceType,
                LinkedList<Integer> squaresAttacked, LinkedList<Integer> legalMoves, LinkedList<Piece> pieces) {
        super(coordinate, isWhite, hasMoved, canBeEnPassant, pieceType, squaresAttacked, legalMoves, pieces);
    }

    @Override
    public void move(int newCoordinate) {

        setMovementDirection();

        if (newCoordinate - coordinate == movementDirection || newCoordinate - coordinate == 2 * movementDirection
                || newCoordinate - coordinate == -9 * movementDirection || newCoordinate - coordinate == 11 * movementDirection) {
            if (newCoordinate - coordinate == movementDirection) {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) != null) {
                    updatePiece();
                    return;
                }
            } else if (newCoordinate - coordinate == 2 * movementDirection) {
                if (hasMoved || piecesBetween() || Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) != null) {
                    updatePiece();
                    return;
                }
            } else if (newCoordinate - coordinate == -9 * movementDirection) {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) == null) {
                    if (Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64) != null) {
                        if (Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).canBeEnPassant) {
                            recentCapture = Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64);
                            Board.getPiece((coordinate - (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).capture();
                        } else {
                            updatePiece();
                            return;
                        }
                    } else {
                        updatePiece();
                        return;
                    }
                } else {
                    if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).isWhite != isWhite) {
                        recentCapture = Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64);
                        Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).capture();
                    } else {
                        updatePiece();
                        return;
                    }
                }
            } else {
                if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64) == null) {
                    if (Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64) != null) {
                        if (Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).canBeEnPassant) {
                            recentCapture = Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64);
                            Board.getPiece((coordinate + (10 * movementDirection)) / 10 * 64, coordinate % 10 * 64).capture();
                        } else {
                            updatePiece();
                            return;
                        }
                    } else {
                        updatePiece();
                        return;
                    }
                } else {
                    if (Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).isWhite != isWhite) {
                        recentCapture = Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64);
                        Board.getPiece(newCoordinate / 10 * 64, newCoordinate % 10 * 64).capture();
                    } else {
                        updatePiece();
                        return;
                    }
                }
            }
        } else {
            updatePiece();
            return;
        }
        canBeEnPassant = Math.abs(newCoordinate - coordinate) == 2;
        coordinate = newCoordinate;
    }

    private boolean piecesBetween() {
        if (isWhite) {
            return Board.getPiece((coordinate) / 10 * 64, (coordinate - 1) % 10 * 64) != null;
        }
        return Board.getPiece((coordinate) / 10 * 64, (coordinate + 1) % 10 * 64) != null;
    }

    public void setMovementDirection() {
        movementDirection = 1;
        if (isWhite) {
            movementDirection = -1;
        }
    }

    @Override
    public LinkedList<Integer> getSquaresAttacked() {
        LinkedList<Integer> squares = new LinkedList<>();

        setMovementDirection();

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
    public LinkedList<Integer> getLegalMoves() {

        LinkedList<Integer> squares = getLegalSquaresAttacked();

        for (int i = squares.size() - 1; i >= 0; i--) {
            if (Board.getPiece(squares.get(i) / 10 * 64, squares.get(i) % 10 * 64) != null) {
                continue;
            } else if (Board.getPiece(squares.get(i) / 10 * 64, (squares.get(i) - movementDirection) % 10 * 64) != null) {
                if (Board.getPiece(squares.get(i) / 10 * 64, (squares.get(i) - movementDirection) % 10 * 64).canBeEnPassant) {
                    continue;
                }
            }
            squares.remove(squares.get(i));
        }

        int numSquares = 1;
        if (!hasMoved) {
            numSquares = 2;
        }

        for (int i = 1; i <= numSquares; i++) {
            int initialCoordinate = coordinate;
            boolean initialEnPassant = canBeEnPassant;

            move(coordinate + (i * movementDirection));

            if (coordinate != initialCoordinate) {
                boolean pieceChecks = false;
                for (int j = 0; j < pieces.size(); j++) {
                    if (pieces.get(j).isWhite != isWhite) {
                        pieces.get(j).squaresAttacked = pieces.get(j).getSquaresAttacked();
                        if (pieces.get(j).checksKing()) {
                            pieceChecks = true;
                            break;
                        }
                    }
                }
                coordinate = initialCoordinate;
                canBeEnPassant = initialEnPassant;

                for (int j = 0; j < pieces.size(); j++) {
                    if (pieces.get(j).isWhite != isWhite) {
                        pieces.get(j).updatePiece();
                    }
                }

                if (!pieceChecks) {
                    squares.add(coordinate + (i * movementDirection));
                }
            }
        }

        return squares;
    }
}