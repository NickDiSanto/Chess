import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Board {

    // TODO: Add clock
    // TODO: Add squaresAttacked options
    // TODO: If check, make king's square red

    public static LinkedList<Piece> pieces = new LinkedList<>();
    public static LinkedList<Integer> squaresAttacked = new LinkedList<>();
    public static Piece selectedPiece = null;
    public static boolean whiteTurn = true;
    public static int initialCoordinate;

    public static void main(String[] args) throws IOException {
        BufferedImage all = ImageIO.read(new File("D:\\chess.png"));
        Image[] images = new Image[12];
        int index = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 6; x++) {
                images[index] = all.getSubimage(x * 200, y * 200, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                index++;
            }
        }

        Piece blackRook1 = new Rook(0, false, false, false, 'R', squaresAttacked, pieces);
        Piece blackKnight1 = new Knight(10, false, false, false, 'N', squaresAttacked, pieces);
        Piece blackBishop1 = new Bishop(20, false, false, false, 'B', squaresAttacked, pieces);
        Piece blackQueen = new Queen(30, false, false, false, 'Q', squaresAttacked, pieces);
        Piece blackKing = new King(40, false, false, false, 'K', squaresAttacked, pieces);
        Piece blackBishop2 = new Bishop(50, false, false, false, 'B', squaresAttacked, pieces);
        Piece blackKnight2 = new Knight(60, false, false, false, 'N', squaresAttacked, pieces);
        Piece blackRook2 = new Rook(70, false, false, false, 'R', squaresAttacked, pieces);
        Piece blackPawn1 = new Pawn(1, false, false, false, 'P', squaresAttacked, pieces);
        Piece blackPawn2 = new Pawn(11, false, false, false, 'P', squaresAttacked, pieces);
        Piece blackPawn3 = new Pawn(21, false, false, false, 'P', squaresAttacked, pieces);
        Piece blackPawn4 = new Pawn(31, false, false, false, 'P', squaresAttacked, pieces);
        Piece blackPawn5 = new Pawn(41, false, false, false, 'P', squaresAttacked, pieces);
        Piece blackPawn6 = new Pawn(51, false, false, false, 'P', squaresAttacked, pieces);
        Piece blackPawn7 = new Pawn(61, false, false, false, 'P', squaresAttacked, pieces);
        Piece blackPawn8 = new Pawn(71, false, false, false, 'P', squaresAttacked, pieces);

        Piece whiteRook1 = new Rook(7, true, false, false, 'R', squaresAttacked, pieces);
        Piece whiteKnight1 = new Knight(17, true, false, false, 'N', squaresAttacked, pieces);
        Piece whiteBishop1 = new Bishop(27, true, false, false, 'B', squaresAttacked, pieces);
        Piece whiteQueen = new Queen(37, true, false, false, 'Q', squaresAttacked, pieces);
        Piece whiteKing = new King(47, true, false, false, 'K', squaresAttacked, pieces);
        Piece whiteBishop2 = new Bishop(57, true, false, false, 'B', squaresAttacked, pieces);
        Piece whiteKnight2 = new Knight(67, true, false, false, 'N', squaresAttacked, pieces);
        Piece whiteRook2 = new Rook(77, true, false, false, 'R', squaresAttacked, pieces);
        Piece whitePawn1 = new Pawn(6, true, false, false, 'P', squaresAttacked, pieces);
        Piece whitePawn2 = new Pawn(16, true, false, false, 'P', squaresAttacked, pieces);
        Piece whitePawn3 = new Pawn(26, true, false, false, 'P', squaresAttacked, pieces);
        Piece whitePawn4 = new Pawn(36, true, false, false, 'P', squaresAttacked, pieces);
        Piece whitePawn5 = new Pawn(46, true, false, false, 'P', squaresAttacked, pieces);
        Piece whitePawn6 = new Pawn(56, true, false, false, 'P', squaresAttacked, pieces);
        Piece whitePawn7 = new Pawn(66, true, false, false, 'P', squaresAttacked, pieces);
        Piece whitePawn8 = new Pawn(76, true, false, false, 'P', squaresAttacked, pieces);

        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        frame.setUndecorated(true);

        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                boolean isWhite = true;
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {
                        if (isWhite)
                            g.setColor(new Color(240, 225, 190));
                        else
                            g.setColor(new Color(180, 140, 100));
                        g.fillRect(x * 64, y * 64, 64, 64);
                        isWhite = !isWhite;
                    }
                    isWhite = !isWhite;
                }
                for (Piece piece : pieces) {
                    int index = 0;
                    if (Character.toUpperCase(piece.pieceType) == 'Q')
                        index = 1;
                    if (Character.toUpperCase(piece.pieceType) == 'B')
                        index = 2;
                    if (Character.toUpperCase(piece.pieceType) == 'N')
                        index = 3;
                    if (Character.toUpperCase(piece.pieceType) == 'R')
                        index = 4;
                    if (Character.toUpperCase(piece.pieceType) == 'P')
                        index = 5;
                    if (!piece.isWhite)
                        index += 6;
                    g.drawImage(images[index], piece.xPixel, piece.yPixel, this);
                }
            }
        };

        frame.add(panel, BorderLayout.CENTER);
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedPiece != null) {
                    if (selectedPiece.isWhite != whiteTurn)
                        return;
                    selectedPiece.xPixel = e.getX() - 32;
                    selectedPiece.yPixel = e.getY() - 32;
                    frame.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedPiece = getPiece(e.getX(), e.getY());
                if (selectedPiece.isWhite != whiteTurn)
                    return;

                initialCoordinate = e.getX() / 64 * 10 + e.getY() / 64;
            }

            @Override
            public void mouseReleased(MouseEvent e) throws IllegalArgumentException {
                if (selectedPiece == null)
                    throw new IllegalArgumentException("No piece selected");
                else {
                    if (selectedPiece.isWhite != whiteTurn)
                        return;

                    selectedPiece.recentCapture = null;

                    selectedPiece.move(e.getX() / 64 * 10 + e.getY() / 64);

                    if (selectedPiece.coordinate != initialCoordinate) {
                        for (Piece piece : pieces)
                            piece.updatePiece();
                        for (int i = 0; i < pieces.size(); i++) {
                            if (pieces.get(i).checksKing() && pieces.get(i).isWhite != whiteTurn) {
                                if (selectedPiece.recentCapture != null) {
                                    Piece newPiece = selectedPiece.recentCapture;
                                    pieces.add(newPiece);
                                    selectedPiece.recentCapture = null;
                                }
                                selectedPiece.coordinate = initialCoordinate;
                                selectedPiece.updatePiece();
                            }
                        }
                        for (Piece piece : pieces)
                            piece.squaresAttacked = piece.getSquaresAttacked();

                        if (selectedPiece.coordinate != initialCoordinate) {
                            selectedPiece.updatePiece();
                            if (Math.abs(selectedPiece.coordinate - initialCoordinate) != 2 || selectedPiece.pieceType != 'P')
                                selectedPiece.takeAwayEnPassant();
                            selectedPiece.hasMoved = true;
                            whiteTurn = !whiteTurn;

                            if (isCheckmate()) {
                                System.out.println("Checkmate!");
                                if (whiteTurn)
                                    System.out.println("Black wins!");
                                else
                                    System.out.println("White wins!");

                                // TODO: END GAME
                            }
                        }
                    }
                    frame.repaint();
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static Piece getPiece(int xPixel, int yPixel) {
        int xCoordinate = xPixel / 64;
        int yCoordinate = yPixel / 64;
        for (Piece piece : pieces) {
            if (piece.coordinate / 10 == xCoordinate && piece.coordinate % 10 == yCoordinate)
                return piece;
        }
        return null;
    }

    public static boolean canCastleShort() {
        if (whiteTurn) {
            if (Board.getPiece(256, 448) == null || Board.getPiece(448, 448) == null)
                return false;
            else {
                if (isCheck() || Board.getPiece(256, 448).hasMoved || Board.getPiece(448, 448).hasMoved
                        || Board.getPiece(320, 448) != null || Board.getPiece(384, 448) != null)
                    return false;
                else {
                    for (Piece piece : pieces) {
                        if (!piece.isWhite) {
                            if (piece.squaresAttacked.contains(57) || piece.squaresAttacked.contains(67))
                                return false;
                        }
                    }
                }
            }
        } else {
            if (Board.getPiece(256, 0) == null || Board.getPiece(448, 0) == null)
                return false;
            else {
                if (isCheck() || Board.getPiece(256, 0).hasMoved || Board.getPiece(448, 0).hasMoved
                        || Board.getPiece(320, 0) != null || Board.getPiece(384, 0) != null)
                    return false;
                else {
                    for (Piece piece : pieces) {
                        if (piece.isWhite) {
                            if (piece.squaresAttacked.contains(50) || piece.squaresAttacked.contains(60))
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean canCastleLong() {
        if (whiteTurn) {
            if (Board.getPiece(256, 448) == null || Board.getPiece(0, 448) == null)
                return false;
            else {
                if (isCheck() || Board.getPiece(256, 448).hasMoved || Board.getPiece(0, 448).hasMoved
                        || Board.getPiece(192, 448) != null || Board.getPiece(128, 448) != null
                        || Board.getPiece(64, 448) != null)
                    return false;
                else {
                    for (Piece piece : pieces) {
                        if (!piece.isWhite) {
                            if (piece.squaresAttacked.contains(37) || piece.squaresAttacked.contains(27) || piece.squaresAttacked.contains(17))
                                return false;
                        }
                    }
                }
            }
        } else {
            if (Board.getPiece(256, 0) == null || Board.getPiece(0, 0) == null)
                return false;
            else {
                if (isCheck() || Board.getPiece(256, 0).hasMoved || Board.getPiece(0, 0).hasMoved
                        || Board.getPiece(192, 0) != null || Board.getPiece(128, 0) != null ||
                        Board.getPiece(64, 0) != null)
                    return false;
                else {
                    for (Piece piece : pieces) {
                        if (piece.isWhite) {
                            if (piece.squaresAttacked.contains(30) || piece.squaresAttacked.contains(20) || piece.squaresAttacked.contains(10))
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static void pawnPromotion() {
        // TODO: Implement pawnPromotion (maybe should happen in pawn class?)
        //  - PRIORITY: LOW
    }

    public static boolean isCheck() {
        for (Piece piece : pieces) {
            if (piece.checksKing())
                return true;
        }
        return false;
    }

    public static boolean isCheckmate() {
        if (isCheck()) {
            for (int i = 0; i < pieces.size(); i++) {
                if (pieces.get(i).isWhite == whiteTurn) {
                    pieces.get(i).recentCapture = null;
                    for (Integer square : pieces.get(i).squaresAttacked) {
                        int initialCoordinate = pieces.get(i).coordinate;
                        pieces.get(i).move(square);
                        boolean pieceChecks = false;
                        for (Piece oppPiece : pieces) {
                            oppPiece.squaresAttacked = oppPiece.getSquaresAttacked();
                            if (oppPiece.checksKing() && oppPiece.isWhite != whiteTurn)
                                pieceChecks = true;
                        }
                        if (pieces.get(i).recentCapture != null) {
                            Piece newPiece = pieces.get(i).recentCapture;
                            pieces.add(newPiece);
                            pieces.get(i).recentCapture = null;
                        }
                        pieces.get(i).coordinate = initialCoordinate;
                        if (!pieceChecks)
                            return false;
                    }
                }
            }
            return true;
        }
        else
            return false;
    }
}