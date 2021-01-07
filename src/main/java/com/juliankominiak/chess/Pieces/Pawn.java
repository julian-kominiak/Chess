package com.juliankominiak.chess.Pieces;

import com.juliankominiak.chess.DialogClass;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

import static com.juliankominiak.chess.Board.*;

public class Pawn extends Piece {
    Image icon = null;

    public Pawn(PieceColor color, int x, int y) throws FileNotFoundException {
        super(color, x, y);

        if (color.equals(PieceColor.WHITE)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/PawnW.png"))));
        }
        if (color.equals(PieceColor.BLACK)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/PawnB.png"))));
        }
    }

    //Checks if the Pawn move is allowable.
    public boolean checkAvailability(int x0, int y0, int newX, int newY) {

        int vectorX = newX - x0;
        int vectorY = newY - y0;

        if (!board[newX][newY].hasPiece()) {
            if (getColor().equals(PieceColor.WHITE)) {
                if (vectorX == 0 && vectorY == -1) {
                    return true;
                }
                if (y0 == 6 && vectorX == 0 && vectorY == -2) {
                    return !board[x0][y0 - 1].hasPiece(); //check if the pawn is not jumping over a piece
                }
            } else {
                if (vectorX == 0 && vectorY == 1) {
                    return true;
                }
                if (y0 == 1 && vectorX == 0 && vectorY == 2) {
                    return !board[x0][y0 + 1].hasPiece();
                }
            }
        } else {
            if (getColor().equals(PieceColor.WHITE)) {
                if (Math.abs(vectorX) == 1 && vectorY == -1) {
                    return true;
                }
            } else {
                if (Math.abs(vectorX) == 1 && vectorY == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    //Checks if the Pawn reached the opposite border.
    public void checkIfBorderReached(int x, int y) throws FileNotFoundException {
        if ((color.equals(PieceColor.WHITE) && y == 0) ||
                (color.equals(PieceColor.BLACK) && y == 7)) {
            board[x][y].changeColor(Color.YELLOW);
            DialogClass dialog = new DialogClass();
            dialog.showTransformDialog(this);
        }
    }

    //Transforms the Pawn into a new Piece.
    public void transform(Piece transformedPawn) {
        board[toBoard(getOldX())][toBoard(getOldY())].setPiece(transformedPawn);
        pieceGroup.getChildren().add(transformedPawn);
        pieceGroup.getChildren().remove(this);
        initializeColors();
        lookForCheck();
    }
}