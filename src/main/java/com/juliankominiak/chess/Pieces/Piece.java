package com.juliankominiak.chess.Pieces;

import com.juliankominiak.chess.DialogClass;
import com.juliankominiak.chess.MoveResult;
import com.juliankominiak.chess.MoveType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

import static com.juliankominiak.chess.Board.*;


public abstract class Piece extends StackPane {

    public PieceColor color;
    private double mouseX, mouseY;
    private double oldX, oldY;
    public static int turn = 0;

    public Piece(PieceColor color, int x, int y) {
        this.color = color;
        move(x, y);
        setMouseActions();
    }

    public PieceColor getColor() {
        return color;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    //Moves the Piece onto a new Tile.
    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    //Sets actions for pressing, dragging, and releasing the mouse click.
    public void setMouseActions() {
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            highlightAvailableMoves();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });

        setOnMouseReleased(e -> {
            int x0 = toBoard(getOldX());
            int y0 = toBoard(getOldY());
            int newX = toBoard(getLayoutX());
            int newY = toBoard(getLayoutY());
            MoveResult result;

            //Checks if the move is inside the board.
            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(newX, newY);
            }
            initializeColors();

            //Finalizes the move.
            switch (result.getType()) {
                case NONE:
                    abortMove();
                    lookForCheck();
                    break;
                case NORMAL:
                    turn++;
                    move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(this);
                    break;
                case KILL:
                    turn++;
                    Piece killedPiece = board[newX][newY].getPiece();
                    pieceGroup.getChildren().remove(killedPiece);
                    move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(this);
                    break;
            }

            //Looks for a CheckMate and Pawns reaching the boarder.
            if (result.getType() != MoveType.NONE) {
                if (lookForCheck()) {
                    if (lookForCheckMate(getKing(check))) {
                        DialogClass dialog = new DialogClass();
                        try {
                            dialog.showEndGameDialog();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                    }
                }
                if (this instanceof Pawn) {
                    try {
                        ((Pawn) this).checkIfBorderReached(newX, newY);
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        });
    }

    public MoveResult tryMove(int newX, int newY) {

        //Keeps track of having only one turn per color.
        if ((turn % 2) == 1 && color.equals(PieceColor.WHITE)) {
            return new MoveResult(MoveType.NONE);
        }
        if ((turn % 2) == 0 && color.equals(PieceColor.BLACK)) {
            return new MoveResult(MoveType.NONE);
        }

        //Checks if the new Tile is not occupied by a Piece of the same color.
        if (board[newX][newY].hasPiece()) {
            if (board[newX][newY].getPiece().getColor().equals(color)) {
                return new MoveResult(MoveType.NONE);
            }
        }

        //Checks if the move is legal for the Piece.
        boolean allowable;
        int x0 = toBoard(getOldX());
        int y0 = toBoard(getOldY());
        allowable = checkAvailability(x0, y0, newX, newY);
        if (!allowable) {
            return new MoveResult(MoveType.NONE);
        }

        Piece killedPiece = null;

        //Saving the killedPiece.
        if (board[newX][newY].hasPiece()) {
            //Killing the king earlier will avoid entering an infinite loop with looking for checks.
            if (board[newX][newY].getPiece() instanceof King) {
                return new MoveResult(MoveType.KILL);
            }
            killedPiece = board[newX][newY].getPiece();
            pieceGroup.getChildren().remove(killedPiece);
        }

        //Doing the hypothetical move, to check if we are going to generate a check.
        move(newX, newY);
        board[x0][y0].setPiece(null);
        board[newX][newY].setPiece(this);
        PieceColor previousCheck = check;
        if (lookForCheck()) {
            if (check == this.getColor()) {
                undoCheck();
                board[newX][newY].setPiece(null);
                if (killedPiece != null) {
                    pieceGroup.getChildren().add(killedPiece);
                    board[newX][newY].setPiece(killedPiece);
                }
                move(x0, y0);
                board[x0][y0].setPiece(this);
                return new MoveResult(MoveType.NONE);
            } else if (previousCheck != null && previousCheck != check) {
                undoCheck();
                board[newX][newY].setPiece(null);
                if (killedPiece != null) {
                    pieceGroup.getChildren().add(killedPiece);
                    board[newX][newY].setPiece(killedPiece);
                }
                move(x0, y0);
                board[x0][y0].setPiece(this);
                return new MoveResult(MoveType.NONE);
            }
        }
        undoCheck();
        board[newX][newY].setPiece(null);

        //Adding back the killedPiece.
        if (killedPiece != null) {
            pieceGroup.getChildren().add(killedPiece);
            board[newX][newY].setPiece(killedPiece);
        }
        move(x0, y0);
        board[x0][y0].setPiece(this);

        if (board[newX][newY].hasPiece()) {
            return new MoveResult(MoveType.KILL);
        }
        return new MoveResult(MoveType.NORMAL);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }

    //Made to be overridden
    public boolean checkAvailability(int x0, int y0, int newX, int newY) {
        return false;
    }

    //Checks the result of making a move and highlights adequate Tiles on the board
    public void highlightAvailableMoves() {
        MoveResult result;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                result = tryMove(x, y);
                switch (result.getType()) {
                    case NORMAL:
                        board[x][y].setFill(Color.LIGHTGREEN);
                        break;
                    case KILL:
                        board[x][y].setFill(Color.RED);
                        break;
                }
            }
        }
    }
}