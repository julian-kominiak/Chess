package com.juliankominiak.chess.Pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

public class Horse extends Piece {
    Image icon = null;

    public Horse(PieceColor color, int x, int y) throws FileNotFoundException {
        super(color, x, y);

        if (color.equals(PieceColor.WHITE)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/HorseW.png"))));
        }
        if (color.equals(PieceColor.BLACK)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/HorseB.png"))));
        }
    }

    //Checks if the Horse move is allowable
    public boolean checkAvailability(int x0, int y0, int newX, int newY) {

        int vectorX = newX - x0;
        int vectorY = newY - y0;

        if ((Math.abs(vectorX) == 1 && Math.abs(vectorY) == 2) ||
                (Math.abs(vectorX) == 2 && Math.abs(vectorY) == 1)) {
                return true;
        }

        return false;
    }
}
