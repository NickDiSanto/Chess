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

/**
 * The Board class utilizes the JFrame and JPanel classes to paint the chess board and add pieces.
 * This class highlights notable squares during the game, utilizes the mouseMotionListener interface to
 * detect when pieces are being interacted with, and determines and executes important functions
 * for the game.
 *
 * This class also contains the Chess project's main method, beginning its execution.
 * The rest of the project stems from the frame that this class establishes.
 *
 * @version     18 January 2022
 * @author      Nick DiSanto
 */

public class Board {

    public static LinkedList<Piece> pieces = new LinkedList<>();
    public static LinkedList<Integer> squaresAttacked = new LinkedList<>();
    public static LinkedList<Integer> legalMoves = new LinkedList<>();
    public static Piece selectedPiece = null;
    public static boolean whiteTurn = true;
    public static int initialCoordinate;
    public static int lastInitialCoordinate = Integer.MAX_VALUE;
    public static int lastMove = Integer.MAX_VALUE;
//    public static boolean kingTrapped = false;

    public static void main(String[] args) throws IOException {
        BufferedImage all = ImageIO.read(new File("pieces.png"));
        Image[] images = new Image[12];
        int index = 0;

        // reads individual pieces off of pieces.png
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 6; x++) {
                images[index] = all.getSubimage(x * 200, y * 200, 200,
                        200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                index++;
            }
        }

        /*
         * Creates each individual Piece object, giving each one its
         * own unique values depending on the initial state of the game
         */
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

        /*
         * JFrame is utilized to create the window, along with the interacting operations,
         * that the user corresponds with during the game
         */
        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        frame.setUndecorated(true);

        /*
         * JPanel is used initially to paint the board, and is continually used as the user
         * selects and moves pieces
         */
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
            boolean isWhite = true;

            // paints original white and brown squares on the board
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
                isWhite = !isWhite;              // alternates colors
            }
            for (Piece piece : pieces) {
                int index = pieceIndices(piece, piece.isWhite);         // returns the appropriate piece index
                g.drawImage(images[index], piece.xPixel, piece.yPixel, this);   // draws pieces
            }

            // highlights the lastInitialCoordinate and lastMove squares
            g.setColor(new Color(210, 200, 80));
            if (lastInitialCoordinate != Integer.MAX_VALUE) {
                highlightSquare(lastInitialCoordinate, g, images, this);
            }
            if (lastMove != Integer.MAX_VALUE) {
                highlightSquare(lastMove, g, images, this);
            }

            if (selectedPiece != null) {
                // highlights the current square of the selected piece
                g.setColor(new Color(110, 130, 70));
                g.fillRect(initialCoordinate / 10 * 64, initialCoordinate % 10 * 64, 64, 64);

                // adds grey dots to show where the current legal moves are
                g.setColor(new Color(90, 90, 90));
                for (int square : selectedPiece.legalMoves) {
                    // if there is a piece, highlight the piece's square
                    if (Board.getPiece(square / 10 * 64, square % 10 * 64) != null) {
                        Piece piece = Board.getPiece(square / 10 * 64, square % 10 * 64);
                        g.fillRect(square / 10 * 64, square % 10 * 64, 64, 64);

                        int index = pieceIndices(piece, piece.isWhite);     // returns the appropriate piece index
                        // redraws piece over square
                        g.drawImage(images[index], piece.xPixel, piece.yPixel, this);
                    } else {
                        // if there is no piece, highlight a dot in the middle of the square
                        g.fillOval(square / 10 * 64 + 22, square % 10 * 64 + 22, 18, 18);
                    }
                }
                int index = pieceIndices(selectedPiece, selectedPiece.isWhite);   // returns the appropriate piece index
                // redraws piece over square
                g.drawImage(images[index], selectedPiece.xPixel, selectedPiece.yPixel, this);
            }

            // highlights the king's square red when it's in check
            if (isCheck()) {
                for (Piece piece : pieces) {
                    if (piece.pieceType == 'K' && piece.isWhite == whiteTurn) {
                        g.setColor(new Color(255, 50, 50));
                        g.fillRect(piece.coordinate / 10 * 64, piece.coordinate % 10 * 64, 64, 64);

                        // redraws piece over square
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

        /*
         * MouseMotionListener detects when a piece is being dragged,
         *  repainting the panel for the user's ease
         */
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedPiece != null) {
                    if (selectedPiece.isWhite != whiteTurn) {
                        return;
                    }
                    selectedPiece.xPixel = e.getX() - 32;       // corrects the piece to the middle of the mouse
                    selectedPiece.yPixel = e.getY() - 32;
                    frame.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        /*
         * MouseListener detects and assigns values to pieces when they are pressed
         * and released, implementing essential functions to the game when these
         * events take place
         */
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    selectedPiece = getPiece(e.getX(), e.getY());
                    if (selectedPiece.isWhite != whiteTurn) {
                        return;
                    }
                    // sets initialCoordinate to the pixels pressed on
                    initialCoordinate = e.getX() / 64 * 10 + e.getY() / 64;

                    // scans the threats and legal moves of the piece
                    selectedPiece.squaresAttacked = selectedPiece.getSquaresAttacked();
                    selectedPiece.legalMoves = selectedPiece.getLegalMoves();
                } catch (NullPointerException exception) {
                    System.out.println("No piece selected.");       // in the case an empty square is selected
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
                    selectedPiece.recentCapture = null;      //  resets recentCapture in case it previously held a value

                    // gets the coordinate of the pixels released on
                    int newCoordinate = e.getX() / 64 * 10 + e.getY() / 64;

                    if (selectedPiece.legalMoves.contains(newCoordinate)) {
                        selectedPiece.move(newCoordinate);      // moves selected piece
                        selectedPiece.hasMoved = true;

                        if (selectedPiece.recentCapture != null) {
                            playSound("sound_effects/captureSound.wav");
                        } else {
                            playSound("sound_effects/moveSound.wav");
                        }

                        // unless it is valid for en passant, takes the option away
                        if (Math.abs(selectedPiece.coordinate - initialCoordinate) != 2
                                || selectedPiece.pieceType != 'P') {
                            selectedPiece.canBeEnPassant = false;
                        }

                        // if a pawn reaches the end, promotes it
                        if (selectedPiece.pieceType == 'P' && (selectedPiece.coordinate % 10 == 7
                                || selectedPiece.coordinate % 10 == 0)) {
                            pawnPromotion(); // FIXME: Should happen outside mouseReleased so it can repaint
                        }

                        // updates the pieces of the selected piece's color
                        for (int i = 0; i < pieces.size(); i++) {
                            if (pieces.get(i).isWhite == selectedPiece.isWhite) {
                                pieces.get(i).updatePiece();
                                pieces.get(i).legalMoves = pieces.get(i).getLegalMoves();
                            }
                        }

                        // updates the pieces of the opposite color
                        for (int i = 0; i < pieces.size(); i++) {
                            if (pieces.get(i).isWhite != selectedPiece.isWhite) {
                                pieces.get(i).updatePiece();
                                pieces.get(i).legalMoves = pieces.get(i).getLegalMoves();
                                pieces.get(i).legalMoves = pieces.get(i).getLegalMoves();
                            }
                        }

                        // updates the lastInit and lastMove for highlighting
                        lastInitialCoordinate = initialCoordinate;
                        lastMove = selectedPiece.coordinate;

                        whiteTurn = !whiteTurn;

                        // determines if the game is over due to the king being trapped
                        if (kingTrapped()) { // FIXME: Should happen outside mouseReleased so it can repaint
                            System.out.println();
                            if (isCheck()) {
                                System.out.println("Checkmate!");
                                if (!whiteTurn) {
                                    System.out.println("White wins!");
                                    playSound("sound_effects/winningSound.wav");
                                } else {
                                    System.out.println("Black wins!");
                                    playSound("sound_effects/losingSound.wav");
                                }
                                System.out.println();
                            } else {
                                // in the case that it is not check
                                System.out.println("Stalemate!");
                                System.out.println();
                                playSound("sound_effects/drawSound.wav");
                            }
                            while (true) {
                                // gives the option to play the game again
                                System.out.println("Would you like to play again? (Y/N)");
                                Scanner s = new Scanner(System.in);
                                String playAgain = s.nextLine();
                                if (playAgain.toUpperCase(Locale.ROOT).equals("Y")) {
                                    // TODO: Play again
                                    System.exit(0);         // restarts program
                                } else if (playAgain.toUpperCase(Locale.ROOT).equals("N")) {
                                    System.out.println();
                                    System.out.println("Thanks for playing!");
                                    System.exit(0);         // ends program
                                } else {
                                    System.out.println("Invalid option. Please try again.");
                                    System.out.println();
                                }
                            }
                        }
                    } else {
                        selectedPiece.coordinate = initialCoordinate;
                        selectedPiece.updatePiece();
                    }
                    selectedPiece = null;       // resets selectedPiece to null
                    frame.repaint();

                } catch (NullPointerException ignored) {        // in the case that empty square is selected
                } catch (UnsupportedAudioFileException | LineUnavailableException |
                        IOException unsupportedAudioFileException) {   // in the case that the audio file is unavailable
                    unsupportedAudioFileException.printStackTrace();
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // determines what piece will be accessed from the images array
    private static int pieceIndices(Piece piece, boolean isWhite) {
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
        if (!isWhite) {
            index += 6;
        }
        return index;
    }

    // highlights the given square in the given color and redraws the piece
    private static void highlightSquare(int lastSquare, Graphics g, Image[] images, JPanel jPanel) {
        if (Board.getPiece(lastSquare / 10 * 64, lastSquare % 10 * 64) != null) {
            Piece piece = Board.getPiece(lastSquare / 10 * 64, lastSquare % 10 * 64);
            g.fillRect(lastSquare / 10 * 64, lastSquare % 10 * 64, 64, 64);
            int index = pieceIndices(piece, piece.isWhite);
            g.drawImage(images[index], piece.xPixel, piece.yPixel, jPanel);
        } else {
            g.fillRect(lastSquare / 10 * 64, lastSquare % 10 * 64, 64, 64);
        }
    }

   // returns the piece at a given coordinate on the board
    public static Piece getPiece(int xPixel, int yPixel) {
        int xCoordinate = xPixel / 64;      // converts pixels to coordinates
        int yCoordinate = yPixel / 64;
        for (Piece piece : pieces) {
            if (piece.coordinate / 10 == xCoordinate && piece.coordinate % 10 == yCoordinate) {
                return piece;
            }
        }
        return null;
    }

    // checks if the given king can legally castle short
    public static boolean canCastleShort() {
        if (whiteTurn) {
            // checks if both the king and the rook are in the correct squares
            if (Board.getPiece(256, 448) == null || Board.getPiece(448, 448) == null) {
                return false;
            } else {
                // checks if either piece has moved or there are pieces in between
                if (isCheck() || Board.getPiece(256, 448).hasMoved ||
                        Board.getPiece(448, 448).hasMoved || Board.getPiece(320, 448) != null
                        || Board.getPiece(384, 448) != null) {
                    return false;
                } else {
                    // checks if there are opposing pieces threatening check
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
            // checks if both the king and the rook are in the correct squares
            if (Board.getPiece(256, 0) == null || Board.getPiece(448, 0) == null) {
                return false;
            } else {
                // checks if either piece has moved or there are pieces in between
                if (isCheck() || Board.getPiece(256, 0).hasMoved ||
                        Board.getPiece(448, 0).hasMoved || Board.getPiece(320, 0) != null
                        || Board.getPiece(384, 0) != null) {
                    return false;
                } else {
                    // checks if there are opposing pieces threatening check
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

    // checks if the given king can legally castle long
    public static boolean canCastleLong() {
        if (whiteTurn) {
            // checks if both the king and the rook are in the correct squares
            if (Board.getPiece(256, 448) == null || Board.getPiece(0, 448) == null) {
                return false;
            } else {
                // checks if either piece has moved or there are pieces in between
                if (isCheck() || Board.getPiece(256, 448).hasMoved ||
                        Board.getPiece(0, 448).hasMoved || Board.getPiece(192, 448) != null ||
                        Board.getPiece(128, 448) != null || Board.getPiece(64, 448) != null) {
                    return false;
                } else {
                    // checks if there are opposing pieces threatening check
                    for (Piece piece : pieces) {
                        if (!piece.isWhite) {
                            if (piece.squaresAttacked.contains(37) || piece.squaresAttacked.contains(27)
                                    || piece.squaresAttacked.contains(17)) {
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            // checks if both the king and the rook are in the correct squares
            if (Board.getPiece(256, 0) == null || Board.getPiece(0, 0) == null) {
                return false;
            } else {
                // checks if either piece has moved or there are pieces in between
                if (isCheck() || Board.getPiece(256, 0).hasMoved ||
                        Board.getPiece(0, 0).hasMoved || Board.getPiece(192, 0) != null ||
                        Board.getPiece(128, 0) != null || Board.getPiece(64, 0) != null) {
                    return false;
                } else {
                    // checks if there are opposing pieces threatening check
                    for (Piece piece : pieces) {
                        if (piece.isWhite) {
                            if (piece.squaresAttacked.contains(30) || piece.squaresAttacked.contains(20)
                                    || piece.squaresAttacked.contains(10)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    // promotes a pawn when it reaches the end of the board
    private static void pawnPromotion() {
        // FIXME: Paint pawn onto square?
        pieces.remove(selectedPiece);
        Piece newPiece;
        label:
        while (true) {
            System.out.println("What piece would you like to promote to?");
            Scanner s = new Scanner(System.in);
            String piece = s.nextLine().toUpperCase(Locale.ROOT);
            switch (piece) {
                case "QUEEN":
                    // replaces pawn with new queen and adds it to the board
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
                    // replaces pawn with new rook and adds it to the board
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
                    // replaces pawn with new bishop and adds it to the board
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
                    // replaces pawn with new knight and adds it to the board
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
            System.out.println("Invalid piece. Please try again.");     // in case that invalid piece is entered
            System.out.println();
        }
    }

    // checks if either king is currently in check
    public static boolean isCheck() {
        for (Piece piece : pieces) {
            if (piece.checksKing()) {
                return true;
            }
        }
        return false;
    }

    // checks if the given king is trapped, with no legal moves
    private static boolean kingTrapped() {
        for (Piece piece : pieces) {
            if (piece.isWhite == whiteTurn) {
                if (piece.legalMoves.size() != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // plays sound effects
    private static void playSound(String soundFile) throws LineUnavailableException,
            IOException, UnsupportedAudioFileException {
        File f = new File("./" + soundFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f);
        clip.start();
    }
}