package com.juliankominiak.chess.Pieces;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

import static com.juliankominiak.chess.Board.*;

public class King extends Piece {
    Image icon = null;

    public King(PieceColor color, int x, int y) throws FileNotFoundException {
        super(color, x, y);

        if (color.equals(PieceColor.WHITE)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/KingW.png"))));
        }
        if (color.equals(PieceColor.BLACK)) {
            getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("/icons/KingB.png"))));
        }
    }

    //Checks if the King move is allowable.
    public boolean checkAvailability(int x0, int y0, int newX, int newY) {

        int vectorX = newX - x0;
        int vectorY = newY - y0;

        if (Math.abs(vectorX) <= 1 && Math.abs(vectorY) <= 1 && canKingMove(newX, newY)) {
            return true;
        }

        return false;
    }

    //Checks if the king does not move too close to enemy Pieces/
    public boolean canKingMove(int onX, int onY) {
        for (Node piece : pieceGroup.getChildren()) {
            Piece checkPiece = (Piece) piece;
            if ((checkPiece.getColor().equals(this.getColor()) || !(checkPiece instanceof King))) {
                continue;
            }
            int vectorX = onX - toBoard(checkPiece.getOldX());
            int vectorY = onY - toBoard(checkPiece.getOldY());

            if (Math.abs(vectorX) <= 1 && Math.abs(vectorY) <= 1) {
                return false;
            }
        }
        return true;
    }
}