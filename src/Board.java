import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class Board {

    // TODO: Check general documentation: whitespace, etc
    // TODO: Add comments

    public static LinkedList<Piece> pieces = new LinkedList<>();
    public static LinkedList<Integer> squaresAttacked = new LinkedList<>();
    public static LinkedList<Integer> legalMoves;
    public static Piece selectedPiece = null;
    public static boolean whiteTurn = true;
    public static int initialCoordinate;
    public static int lastInitialCoordinate = Integer.MAX_VALUE;
    public static int lastMove = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedImage all = ImageIO.read(new File("D:\\chess.png"));
        Image[] images = new Image[12];
        int index = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 6; x++) {
                images[index] = all.getSubimage(x * 200, y * 200, 200,
                        200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                index++;
            }
        }

        Piece blackRook1 = new Rook(0, false, false,
                false,'R', squaresAttacked, legalMoves, pieces);
        Piece blackKnight1 = new Knight(10, false, false,
                false, 'N', squaresAttacked, legalMoves, pieces);
        Piece blackBishop1 = new Bishop(20, false, false,
                false, 'B', squaresAttacked, legalMoves, pieces);
        Piece blackQueen = new Queen(30, false, false,
                false, 'Q', squaresAttacked, legalMoves, pieces);
        Piece blackKing = new King(40, false, false,
                false, 'K', squaresAttacked, legalMoves, pieces);
        Piece blackBishop2 = new Bishop(50, false, false,
                false, 'B', squaresAttacked, legalMoves, pieces);
        Piece blackKnight2 = new Knight(60, false, false,
                false, 'N', squaresAttacked, legalMoves, pieces);
        Piece blackRook2 = new Rook(70, false, false,
                false, 'R', squaresAttacked, legalMoves, pieces);
        Piece blackPawn1 = new Pawn(1, false, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece blackPawn2 = new Pawn(11, false, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece blackPawn3 = new Pawn(21, false, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece blackPawn4 = new Pawn(31, false, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece blackPawn5 = new Pawn(41, false, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece blackPawn6 = new Pawn(51, false, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece blackPawn7 = new Pawn(61, false, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece blackPawn8 = new Pawn(71, false, false,
                false, 'P', squaresAttacked, legalMoves, pieces);

        Piece whiteRook1 = new Rook(7, true, false,
                false, 'R', squaresAttacked, legalMoves, pieces);
        Piece whiteKnight1 = new Knight(17, true, false,
                false, 'N', squaresAttacked, legalMoves, pieces);
        Piece whiteBishop1 = new Bishop(27, true, false,
                false, 'B', squaresAttacked, legalMoves, pieces);
        Piece whiteQueen = new Queen(37, true, false,
                false, 'Q', squaresAttacked, legalMoves, pieces);
        Piece whiteKing = new King(47, true, false,
                false, 'K', squaresAttacked, legalMoves, pieces);
        Piece whiteBishop2 = new Bishop(57, true, false,
                false, 'B', squaresAttacked, legalMoves, pieces);
        Piece whiteKnight2 = new Knight(67, true, false,
                false, 'N', squaresAttacked, legalMoves, pieces);
        Piece whiteRook2 = new Rook(77, true, false,
                false, 'R', squaresAttacked, legalMoves, pieces);
        Piece whitePawn1 = new Pawn(6, true, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece whitePawn2 = new Pawn(16, true, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece whitePawn3 = new Pawn(26, true, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece whitePawn4 = new Pawn(36, true, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece whitePawn5 = new Pawn(46, true, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece whitePawn6 = new Pawn(56, true, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece whitePawn7 = new Pawn(66, true, false,
                false, 'P', squaresAttacked, legalMoves, pieces);
        Piece whitePawn8 = new Pawn(76, true, false,
                false, 'P', squaresAttacked, legalMoves, pieces);

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
                    int index = pieceIndices(piece);

                    if (!piece.isWhite) {
                        index += 6;
                    }
                    g.drawImage(images[index], piece.xPixel, piece.yPixel, this);
                }

                g.setColor(new Color(210, 200, 80));

                // OPTIMIZE: the way white vs black is done below might be better for the grey squares
                if (lastInitialCoordinate != Integer.MAX_VALUE) {
                    if (Board.getPiece(lastInitialCoordinate / 10 * 64, lastInitialCoordinate % 10 * 64) != null) {
                        Piece piece = Board.getPiece(lastInitialCoordinate / 10 * 64, lastInitialCoordinate % 10 * 64);
                        g.fillRect(lastInitialCoordinate / 10 * 64, lastInitialCoordinate % 10 * 64, 64, 64);
                        int index;
                        if (piece.isWhite) {
                            index = pieceIndices(piece);
                        } else {
                            index = pieceIndices(piece) + 6;
                        }
                        g.drawImage(images[index], piece.xPixel, piece.yPixel, this);
                    } else {
                        g.fillRect(lastInitialCoordinate / 10 * 64, lastInitialCoordinate % 10 * 64, 64, 64);
                    }
                }

                if (lastMove != Integer.MAX_VALUE) {
                    if (Board.getPiece(lastMove / 10 * 64, lastMove % 10 * 64) != null) {
                        Piece piece = Board.getPiece(lastMove / 10 * 64, lastMove % 10 * 64);
                        g.fillRect(lastMove / 10 * 64, lastMove % 10 * 64, 64, 64);
                        int index;
                        if (piece.isWhite) {
                            index = pieceIndices(piece);
                        } else {
                            index = pieceIndices(piece) + 6;
                        }
                        g.drawImage(images[index], piece.xPixel, piece.yPixel, this);
                    } else {
                        g.fillRect(lastMove / 10 * 64, lastMove % 10 * 64, 64, 64);
                    }
                }

                if (selectedPiece != null) {
                    g.setColor(new Color(110, 130, 70));
                    g.fillRect(initialCoordinate / 10 * 64, initialCoordinate % 10 * 64, 64, 64);


                    g.setColor(new Color(90, 90, 90));

                    selectedPiece.squaresAttacked = selectedPiece.getSquaresAttacked();
                    selectedPiece.legalMoves = selectedPiece.getLegalMoves();

                    // FIXME: Pawn is updated at the end of move() in getLegalMoves(), that's what causes the problem

                    if (selectedPiece.isWhite) {
                        for (int square : selectedPiece.legalMoves) {
                            if (Board.getPiece(square / 10 * 64, square % 10 * 64) != null) {
                                Piece piece = Board.getPiece(square / 10 * 64, square % 10 * 64);
                                g.fillRect(square / 10 * 64, square % 10 * 64, 64, 64);
                                int index = pieceIndices(piece) + 6;
                                g.drawImage(images[index], piece.xPixel, piece.yPixel, this);
                            } else {
                                g.fillOval(square / 10 * 64 + 22, square % 10 * 64 + 22, 18, 18);
                            }
                            int index = pieceIndices(selectedPiece);
                            g.drawImage(images[index], selectedPiece.xPixel, selectedPiece.yPixel, this);
                        }
                    } else {
                        for (int square : selectedPiece.legalMoves) {
                            if (Board.getPiece(square / 10 * 64, square % 10 * 64) != null) {
                                Piece piece = Board.getPiece(square / 10 * 64, square % 10 * 64);
                                g.fillRect(square / 10 * 64, square % 10 * 64, 64, 64);
                                int index = pieceIndices(piece);
                                g.drawImage(images[index], piece.xPixel, piece.yPixel, this);
                            } else {
                                g.fillOval(square / 10 * 64 + 22, square % 10 * 64 + 22, 18, 18);
                            }
                            int index = pieceIndices(selectedPiece) + 6;
                            g.drawImage(images[index], selectedPiece.xPixel, selectedPiece.yPixel, this);
                        }
                    }
                }
                if (isCheck()) {
                    for (Piece piece : pieces) {
                        if (piece.pieceType == 'K' && piece.isWhite == whiteTurn) {
                            g.setColor(new Color(255, 50, 50));
                            g.fillRect(piece.coordinate / 10 * 64, piece.coordinate % 10 * 64, 64, 64);
                            if (!whiteTurn) {
                                g.drawImage(images[6], piece.xPixel, piece.yPixel, this);
                            } else {
                                g.drawImage(images[0], piece.xPixel, piece.yPixel, this);
                            }
                        }
                    }
                }
            }
        };

        frame.add(panel, BorderLayout.CENTER);
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedPiece != null) {
                    if (selectedPiece.isWhite != whiteTurn) {
                        return;
                    }
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
                try {
                    selectedPiece = getPiece(e.getX(), e.getY());
                    if (selectedPiece.isWhite != whiteTurn) {
                        return;
                    }
                    initialCoordinate = e.getX() / 64 * 10 + e.getY() / 64;
                } catch (NullPointerException exception) {
                    System.out.println("No piece selected.");
                    System.out.println("Please try again.");
                    System.out.println();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    if (selectedPiece.isWhite != whiteTurn) {
                        return;
                    }

                    selectedPiece.recentCapture = null;

                    selectedPiece.move(e.getX() / 64 * 10 + e.getY() / 64);

                    if (selectedPiece.coordinate != initialCoordinate) {
                        boolean originalHasMoved = selectedPiece.hasMoved;
                        selectedPiece.hasMoved = true;

                        for (int i = 0; i < pieces.size(); i++) {
                            pieces.get(i).updatePiece();
                        }

                        boolean openedUpCheck = false;
                        for (int i = 0; i < pieces.size(); i++) {
                            if (pieces.get(i).checksKing() && pieces.get(i).isWhite != whiteTurn) {
                                openedUpCheck = true;
                                Piece newPiece = null;
                                if (selectedPiece.recentCapture != null) {
                                    newPiece = selectedPiece.recentCapture;
                                    pieces.add(newPiece);
                                    selectedPiece.recentCapture = null;
                                }
                                selectedPiece.coordinate = initialCoordinate;
                                selectedPiece.hasMoved = originalHasMoved;
                                selectedPiece.updatePiece();

                                if (newPiece != null) {
                                    newPiece.updatePiece();
                                    newPiece.legalMoves = newPiece.getLegalMoves();
                                }
                                break;
                            }
                        }
                        if (openedUpCheck) {
                            for (int i = 0; i < pieces.size(); i++) {
                                pieces.get(i).updatePiece();
                                pieces.get(i).legalMoves = pieces.get(i).getLegalMoves();
                            }
                        }
                    }

                    if (selectedPiece.coordinate != initialCoordinate) {
                        if (selectedPiece.recentCapture != null) {
                            playSound("mixkit-wood-hard-hit-2182.wav");
                            selectedPiece.recentCapture = null;
                        } else {
                            playSound("mixkit-light-impact-on-the-ground-2070.wav");
                        }
                        if (Math.abs(selectedPiece.coordinate - initialCoordinate) != 2 || selectedPiece.pieceType != 'P') {
                            selectedPiece.canBeEnPassant = false;
                        }

                        if (selectedPiece.pieceType == 'P' && (selectedPiece.coordinate % 10 == 7 || selectedPiece.coordinate % 10 == 0)) {
                            pawnPromotion(); // FIXME: Should happen outside mouseReleased so it can repaint (I think is the problem?)
                        }
                        for (int i = 0; i < pieces.size(); i++) {
                            pieces.get(i).updatePiece();
                            pieces.get(i).legalMoves = pieces.get(i).getLegalMoves();
                        }

                        lastInitialCoordinate = initialCoordinate;
                        lastMove = selectedPiece.coordinate;

                        whiteTurn = !whiteTurn;

                        if (kingTrapped()) {
                            System.out.println();
                            if (isCheck()) {
                                System.out.println("Checkmate!");
                                if (!whiteTurn) {
                                    System.out.println("White wins!");
                                    playSound("mixkit-animated-small-group-applause-523.wav");
                                } else {
                                    System.out.println("Black wins!");
                                    playSound("mixkit-player-losing-or-failing-2042.wav");
                                }

                                // TODO: End game?

                            } else {
                                System.out.println("Stalemate!");
                                playSound("mixkit-arcade-space-shooter-dead-notification-272.wav");
                                // FIXME: Sound effect doesn't work

                                // TODO: End game?
                            }
                        }
                    }

                    selectedPiece = null;

                    frame.repaint();
                } catch (NullPointerException ignored) {
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static int pieceIndices(Piece piece) {
        int index = 0;
        if (Character.toUpperCase(piece.pieceType) == 'Q') {
            index = 1;
        } else if (Character.toUpperCase(piece.pieceType) == 'B') {
            index = 2;
        } else if (Character.toUpperCase(piece.pieceType) == 'N') {
            index = 3;
        } else if (Character.toUpperCase(piece.pieceType) == 'R') {
            index = 4;
        } else if (Character.toUpperCase(piece.pieceType) == 'P') {
            index = 5;
        }

        return index;
    }

    public static Piece getPiece(int xPixel, int yPixel) {
        int xCoordinate = xPixel / 64;
        int yCoordinate = yPixel / 64;
        for (Piece piece : pieces) {
            if (piece.coordinate / 10 == xCoordinate && piece.coordinate % 10 == yCoordinate) {
                return piece;
            }
        }
        return null;
    }

    public static boolean canCastleShort() {
        if (whiteTurn) {
            if (Board.getPiece(256, 448) == null || Board.getPiece(448, 448) == null) {
                return false;
            } else {
                if (isCheck() || Board.getPiece(256, 448).hasMoved || Board.getPiece(448, 448).hasMoved
                        || Board.getPiece(320, 448) != null || Board.getPiece(384, 448) != null) {
                    return false;
                } else {
                    for (Piece piece : pieces) {
                        if (!piece.isWhite) {
                            if (piece.squaresAttacked.contains(57) || piece.squaresAttacked.contains(67)) {
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            if (Board.getPiece(256, 0) == null || Board.getPiece(448, 0) == null) {
                return false;
            } else {
                if (isCheck() || Board.getPiece(256, 0).hasMoved || Board.getPiece(448, 0).hasMoved
                        || Board.getPiece(320, 0) != null || Board.getPiece(384, 0) != null) {
                    return false;
                } else {
                    for (Piece piece : pieces) {
                        if (piece.isWhite) {
                            if (piece.squaresAttacked.contains(50) || piece.squaresAttacked.contains(60)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean canCastleLong() {
        if (whiteTurn) {
            if (Board.getPiece(256, 448) == null || Board.getPiece(0, 448) == null) {
                return false;
            } else {
                if (isCheck() || Board.getPiece(256, 448).hasMoved || Board.getPiece(0, 448).hasMoved
                        || Board.getPiece(192, 448) != null || Board.getPiece(128, 448) != null
                        || Board.getPiece(64, 448) != null) {
                    return false;
                } else {
                    for (Piece piece : pieces) {
                        if (!piece.isWhite) {
                            if (piece.squaresAttacked.contains(37) || piece.squaresAttacked.contains(27) || piece.squaresAttacked.contains(17)) {
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            if (Board.getPiece(256, 0) == null || Board.getPiece(0, 0) == null) {
                return false;
            } else {
                if (isCheck() || Board.getPiece(256, 0).hasMoved || Board.getPiece(0, 0).hasMoved
                        || Board.getPiece(192, 0) != null || Board.getPiece(128, 0) != null ||
                        Board.getPiece(64, 0) != null) {
                    return false;
                } else {
                    for (Piece piece : pieces) {
                        if (piece.isWhite) {
                            if (piece.squaresAttacked.contains(30) || piece.squaresAttacked.contains(20) || piece.squaresAttacked.contains(10)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private static void pawnPromotion() {
        pieces.remove(selectedPiece);
        Piece newPiece;
        label:
        while (true) {
            System.out.println("What piece would you like to promote to?");
            Scanner s = new Scanner(System.in);
            String piece = s.nextLine().toUpperCase(Locale.ROOT);

            switch (piece) {
                case "QUEEN":
                    if (selectedPiece.isWhite) {
                        newPiece = new Queen(selectedPiece.coordinate, true, true,
                                false,'Q', squaresAttacked, legalMoves, pieces);
                    } else {
                        newPiece = new Queen(selectedPiece.coordinate, false, true,
                                false, 'Q', squaresAttacked, legalMoves, pieces);
                    }
                    newPiece.updatePiece();
                    System.out.println("Successfully promoted to a Queen.");
                    System.out.println();
                    break label;
                case "ROOK":
                    if (selectedPiece.isWhite) {
                        newPiece = new Rook(selectedPiece.coordinate, true, true,
                                false, 'R', squaresAttacked, legalMoves, pieces);
                    } else {
                        newPiece = new Rook(selectedPiece.coordinate, false, true,
                                false, 'R', squaresAttacked, legalMoves, pieces);
                    }
                    newPiece.updatePiece();
                    System.out.println("Successfully promoted to a Rook.");
                    System.out.println();
                    break label;
                case "BISHOP":
                    if (selectedPiece.isWhite) {
                        newPiece = new Bishop(selectedPiece.coordinate, true, true,
                                false, 'B', squaresAttacked, legalMoves, pieces);
                    } else {
                        newPiece = new Bishop(selectedPiece.coordinate, false, true,
                                false, 'B', squaresAttacked, legalMoves, pieces);
                    }
                    newPiece.updatePiece();
                    System.out.println("Successfully promoted to a Bishop.");
                    System.out.println();
                    break label;
                case "KNIGHT":
                    if (selectedPiece.isWhite) {
                        newPiece = new Knight(selectedPiece.coordinate, true, true,
                                false, 'N', squaresAttacked, legalMoves, pieces);
                    } else {
                        newPiece = new Knight(selectedPiece.coordinate, false, true,
                                false, 'N', squaresAttacked, legalMoves, pieces);
                    }
                    newPiece.updatePiece();
                    System.out.println("Successfully promoted to a Knight.");
                    System.out.println();
                    break label;
            }
            System.out.println("Invalid piece. Please try again.");
            System.out.println();
        }
    }

    public static boolean isCheck() {
        for (Piece piece : pieces) {
            if (piece.checksKing()) {
                return true;
            }
        }
        return false;
    }

    private static boolean kingTrapped() {
        for (Piece piece : pieces) {
            if (piece.isWhite == whiteTurn) {
                if (piece.legalMoves.size() != 0) {
                    System.out.println(piece.pieceType);
                    System.out.println(piece.coordinate);
                    System.out.println(piece.legalMoves);
                    System.out.println();
                    return false;
                }
            }
        }
        return true;
    }

    static void playSound(String soundFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File f = new File("./" + soundFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }
}