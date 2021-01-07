package com.juliankominiak.chess.Pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

import static com.juliankominiak.chess.Board.board;

public class Bishop extends Piece {
    Image icon = null;

    public Bishop(PieceColor color, int x, int y) throws FileNotFoundException {
        super(color, x, y);

        if (color.equals(PieceColor.WHITE)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/BishopW.png"))));
        }
        if (color.equals(PieceColor.BLACK)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/BishopB.png"))));
        }
    }

    //Checks if the Bishop move is allowable.
    public boolean checkAvailability(int x0, int y0, int newX, int newY) {

        int vectorX = newX - x0;
        int vectorY = newY - y0;

        if (Math.abs(vectorX) == Math.abs(vectorY)) {
            if (vectorX > 0 && vectorY > 0) {
                for (int i = 1; i < vectorX; i++) {
                    if (board[x0 + i][y0 + i].hasPiece()) {
                        return false;
                    }
                }
            }
            if (vectorX > 0 && vectorY < 0) {
                for (int i = 1; i < vectorX; i++) {
                    if (board[x0 + i][y0 - i].hasPiece()) {
                        return false;
                    }
                }
            }
            if (vectorX < 0 && vectorY < 0) {
                for (int i = 1; i < Math.abs(vectorX); i++) {
                    if (board[x0 - i][y0 - i].hasPiece()) {
                        return false;
                    }
                }
            }
            if (vectorX < 0 && vectorY > 0) {
                for (int i = 1; i < Math.abs(vectorX); i++) {
                    if (board[x0 - i][y0 + i].hasPiece()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
