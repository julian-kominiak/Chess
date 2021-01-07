package com.juliankominiak.chess;

import com.juliankominiak.chess.Pieces.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static com.juliankominiak.chess.Board.*;

public class Tile extends Rectangle {

    private Piece piece;
    private final Color defaultColor;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(boolean light, int x, int y) {
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(light ? Color.valueOf("#7df9ff") : Color.valueOf("#0095b6"));
        defaultColor = (light ? Color.valueOf("#7df9ff") : Color.valueOf("#0095b6"));
        setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
    }

    public void changeColor(Color color) {
        setFill(color);
    }

    public void returnToDefaultColor() {
        setFill(defaultColor);
    }
}