package com.juliankominiak.chess;

import com.juliankominiak.chess.Pieces.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

import static com.juliankominiak.chess.Pieces.Piece.turn;

public class Board {
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    public static Tile[][] board = new Tile[WIDTH][HEIGHT];

    public static Group tileGroup;
    public static Group pieceGroup;

    public static PieceColor check = null;

    //Creates new Tiles and Pieces and assigns them on the board
    public void createBoard() throws FileNotFoundException {
        tileGroup = new Group();
        pieceGroup = new Group();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if ((x == 1 && y == 0) || (x == 6 && y == 0)) {
                    piece = new Horse(PieceColor.BLACK, x, y);
                }

                if ((x == 1 && y == 7) || (x == 6 && y == 7)) {
                    piece = new Horse(PieceColor.WHITE, x, y);
                }

                if ((x == 2 && y == 0) || (x == 5 && y == 0)) {
                    piece = new Bishop(PieceColor.BLACK, x, y);
                }

                if ((x == 2 && y == 7) || (x == 5 && y == 7)) {
                    piece = new Bishop(PieceColor.WHITE, x, y);
                }

                if ((x == 4 && y == 0)) {
                    piece = new King(PieceColor.BLACK, x, y);
                }

                if ((x == 4 && y == 7)) {
                    piece = new King(PieceColor.WHITE, x, y);
                }

                if (y == 1) {
                    piece = new Pawn(PieceColor.BLACK, x, y);
                }

                if (y == 6) {
                    piece = new Pawn(PieceColor.WHITE, x, y);
                }

                if ((x == 3 && y == 0)) {
                    piece = new Queen(PieceColor.BLACK, x, y);
                }

                if ((x == 3 && y == 7)) {
                    piece = new Queen(PieceColor.WHITE, x, y);
                }

                if ((x == 0 && y == 0) || (x == 7 && y == 0)) {
                    piece = new Rook(PieceColor.BLACK, x, y);
                }

                if ((x == 0 && y == 7) || (x == 7 && y == 7)) {
                    piece = new Rook(PieceColor.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
    }

    //Converts pixel coordinates to chess tiles.
    public static int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    //Check if there is a check, by trying to kill the King with all of the Pieces.
    public static boolean lookForCheck() {
        King whiteKing = getKing(PieceColor.WHITE);
        King blackKing = getKing(PieceColor.BLACK);

        for (Node piece : pieceGroup.getChildren()) {
            Piece checkPiece = (Piece) piece;
            MoveResult result;
            King target;
            int originalTurn = turn;

            //Sets the right King to kill for the right Piece.
            //Takes care of the turn counter to make the move possible.
            if (checkPiece.getColor().equals(PieceColor.BLACK)) {
                target = whiteKing;
                if ((turn % 2) == 0) turn++;
            } else {
                target = blackKing;
                if ((turn % 2) == 1) turn++;
            }

            result = checkPiece.tryMove(toBoard(target.getOldX()), toBoard(target.getOldY()));

            turn = originalTurn;

            //if the move is possible, we have a check
            if (result.getType().equals(MoveType.KILL)) {
                check = target.getColor();
                highlightCheckTile(Color.CORAL);
                return true;
            }
        }
        check = null;
        return false;
    }

    //Deletes any check that takes place.
    public static void undoCheck() {
        if (check != null) {
            King king = getKing(check);
            check = null;
            assert king != null;
            int x = toBoard(king.getOldX());
            int y = toBoard(king.getOldY());
            board[x][y].returnToDefaultColor();
        }
    }

    //Check if there is a checkmate.
    public static boolean lookForCheckMate(King king) {
        MoveResult result;
        check = king.getColor();
        highlightCheckTile(Color.CORAL);

        //Checks if the King can move anywhere.
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                result = king.tryMove(x, y);
                check = king.getColor();
                highlightCheckTile(Color.CORAL);
                if (result.getType() != MoveType.NONE) {
                    return false;
                }
            }
        }

        //Checks if other Pieces can save the King.
        for (Node piece : pieceGroup.getChildren()) {
            if (piece instanceof King || !((Piece) piece).getColor().equals(king.color)) {
                continue;
            }
            Piece savingPiece = (Piece) piece;
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    result = savingPiece.tryMove(x, y);
                    check = king.getColor();
                    highlightCheckTile(Color.CORAL);
                    if (result.getType() != MoveType.NONE) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static King getKing(PieceColor color) {
        for (Node piece : pieceGroup.getChildren()) {
            if (piece instanceof King && ((King) piece).getColor().equals(color)) {
                return (King) piece;
            }
        }
        return null;
    }

    //If there is a check, it changes the color of the adequate King.
    public static void highlightCheckTile(Color color) {
        King king = getKing(check);
        assert king != null;
        int x = toBoard(king.getOldX());
        int y = toBoard(king.getOldY());
        board[x][y].changeColor(color);
    }

    //Sets all of the Tiles colors as default, deletes any highlights.
    public static void initializeColors() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                board[x][y].returnToDefaultColor();
            }
        }
    }
}

