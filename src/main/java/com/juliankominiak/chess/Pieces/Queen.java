package com.juliankominiak.chess.Pieces;

import com.juliankominiak.chess.Pieces.Piece;
import com.juliankominiak.chess.Pieces.PieceColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

import static com.juliankominiak.chess.Board.board;

public class Queen extends Piece {
    Image icon = null;

    public Queen(PieceColor color, int x, int y) throws FileNotFoundException {
        super(color, x, y);

        if (color.equals(PieceColor.WHITE)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/QueenW.png"))));
        }
        if (color.equals(PieceColor.BLACK)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/QueenB.png"))));
        }
    }

    //Checks if the Queen move is allowable.
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
        if (vectorX == 0) {
            if (vectorY > 0) {
                for (int y = y0 + 1; y < newY; y++) {
                    if (board[x0][y].hasPiece()) {
                        return false;
                    }
                }
            }
            if (vectorY < 0) {
                for (int y = y0 - 1; y > newY; y--) {
                    if (board[x0][y].hasPiece()) {
                        return false;
                    }
                }
            }
            return true;
        }

        if (vectorY == 0) {
            if (vectorX > 0) {
                for (int x = x0 + 1; x < newX; x++) {
                    if (board[x][y0].hasPiece()) {
                        return false;
                    }
                }
            }
            if (vectorX < 0) {
                for (int x = x0 - 1; x > newX; x--) {
                    if (board[x][y0].hasPiece()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}