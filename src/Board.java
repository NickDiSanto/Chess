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

    public static LinkedList<Piece> pieces = new LinkedList<>();
    public static Piece selectedPiece = null;
    public static boolean whiteTurn = true;

    public static void main(String[] args) throws IOException {
        BufferedImage all = ImageIO.read(new File("D:\\chess.png"));
        Image[] images = new Image[12];
        int index = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 6; x++) {
                images[index] = all.getSubimage(x * 200, y * 200, 200, 200).getScaledInstance(
                        64, 64, BufferedImage.SCALE_SMOOTH);
                index++;
            }
        }

        Piece blackRook1 = new Piece(0, false, false, 'R', pieces);
        Piece blackKnight1 = new Knight(10, false, false, 'N', pieces);
        Piece blackBishop1 = new Piece(20, false, false, 'B', pieces);
        Piece blackQueen = new Piece(30, false, false, 'Q', pieces);
        Piece blackKing = new King(40, false, false, 'K', pieces);
        Piece blackBishop2 = new Piece(50, false, false, 'B', pieces);
        Piece blackKnight2 = new Knight(60, false, false, 'N', pieces);
        Piece blackRook2 = new Piece(70, false, false, 'R', pieces);
        Piece blackPawn1 = new Pawn(1, false, false, 'P', pieces);
        Piece blackPawn2 = new Pawn(11, false, false, 'P', pieces);
        Piece blackPawn3 = new Pawn(21, false, false, 'P', pieces);
        Piece blackPawn4 = new Pawn(31, false, false, 'P', pieces);
        Piece blackPawn5 = new Pawn(41, false, false, 'P', pieces);
        Piece blackPawn6 = new Pawn(51, false, false, 'P', pieces);
        Piece blackPawn7 = new Pawn(61, false, false, 'P', pieces);
        Piece blackPawn8 = new Pawn(71, false, false, 'P', pieces);

        Piece whiteRook1 = new Piece(7, true, false, 'R', pieces);
        Piece whiteKnight1 = new Knight(17, true, false, 'N', pieces);
        Piece whiteBishop1 = new Piece(27, true, false, 'B', pieces);
        Piece whiteQueen = new Piece(37, true, false, 'Q', pieces);
        Piece whiteKing = new King(47, true, false, 'K', pieces);
        Piece whiteBishop2 = new Piece(57, true, false, 'B', pieces);
        Piece whiteKnight2 = new Knight(67, true, false, 'N', pieces);
        Piece whiteRook2 = new Piece(77, true, false, 'R', pieces);
        Piece whitePawn1 = new Pawn(6, true, false, 'P', pieces);
        Piece whitePawn2 = new Pawn(16, true, false, 'P', pieces);
        Piece whitePawn3 = new Pawn(26, true, false, 'P', pieces);
        Piece whitePawn4 = new Pawn(36, true, false, 'P', pieces);
        Piece whitePawn5 = new Pawn(46, true, false, 'P', pieces);
        Piece whitePawn6 = new Pawn(56, true, false, 'P', pieces);
        Piece whitePawn7 = new Pawn(66, true, false, 'P', pieces);
        Piece whitePawn8 = new Pawn(76, true, false, 'P', pieces);


        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        frame.setUndecorated(true);

        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                boolean isWhite = true;
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {
                        if (isWhite) {
                            g.setColor(new Color(240, 225, 190));
                        } else {
                            g.setColor(new Color(180, 140, 100));
                        }
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
                    selectedPiece.xPixel = e.getX() - 32;
                    selectedPiece.yPixel = e.getY() - 32;
                    frame.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // OPTIMIZE: Get rid of this method somehow
                //  - PRIORITY: LOW
            }
        });
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedPiece = getPiece(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedPiece.move(e.getX() / 64 * 10 + e.getY() / 64);
                frame.repaint();
                // FIXME: Don't let piece be dragged off the screen
                //  - PRIORITY: MEDIUM

                // TODO: Change turn after selectedPiece coordinate has changed
                //  if (selectedPiece.coordinate != selectedPiece.PREVIOUS_COORDINATE)
                //     changeTurn();
                //  Have moveSuccessful indicate when a turn took place
                //  - PRIORITY: HIGH

            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

//        while (!isCheckmate()) {
//            if (whiteTurn) {
//                // TODO: Implement individual white turn
//                //  - PRIORITY: HIGH
//            }
//            else {
//                // TODO: Implement individual black turn
//                //  - PRIORITY: HIGH
//            }
//            whiteTurn = !whiteTurn;
//        }

    }

    public static Piece getPiece (int xPixel, int yPixel) {
        int xCoord = xPixel / 64;
        int yCoord = yPixel / 64;
        for (Piece piece : pieces) {
            if (piece.coordinate / 10 == xCoord && piece.coordinate % 10 == yCoord)
                return piece;
        }
        return null;
    }

    public static boolean canCastle() {
        // TODO: Implement canCastle
        //  - PRIORITY: MEDIUM
        return false;
    }

    public static void pawnPromotion() {
        // TODO: Implement pawnPromotion (this method may be unnecessary?)
        //  - PRIORITY: LOW
    }

    public static boolean isCheck() {
        // TODO: Implement isCheck
        //  - PRIORITY: MEDIUM
        return false;
    }

    public static boolean isCheckmate() {
        // TODO: Implement isCheckmate
        //  - PRIORITY: MEDIUM
        return false;
    }
}