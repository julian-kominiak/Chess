package com.juliankominiak.chess;

import com.juliankominiak.chess.Pieces.Pawn;
import com.juliankominiak.chess.Pieces.Piece;
import com.juliankominiak.chess.Pieces.PieceColor;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    Tile tile;

    @BeforeEach
    void setUp() {
        tile = new Tile(true, 0, 0);
    }

    @Test
    void hasPieceEmpty() {
        assertFalse(tile.hasPiece());
    }

    @Test
    void hasPieceNotSet() throws FileNotFoundException {
        Pawn pawn = new Pawn(PieceColor.BLACK, 0, 0);
        assertFalse(tile.hasPiece());
    }

    @Test
    void hasPieceSet() throws FileNotFoundException {
        Pawn pawn = new Pawn(PieceColor.BLACK, 0, 0);
        tile.setPiece(pawn);
        assertTrue(tile.hasPiece());
    }

    @Test
    void getPiece() throws FileNotFoundException {
        Pawn pawn = new Pawn(PieceColor.BLACK, 0, 0);
        tile.setPiece(pawn);
        assertEquals(pawn, tile.getPiece());
    }

    @Test
    void getPieceNotAdded() throws FileNotFoundException {
        Pawn pawn = new Pawn(PieceColor.BLACK, 0, 0);
        assertNotEquals(pawn, tile.getPiece());
    }

    @Test
    void changeColor() {
        tile.changeColor(Color.BLACK);
        assertEquals(Color.BLACK, tile.getColor());
    }

    @Test
    void getDefaultColor() {
        assertEquals(Color.valueOf("#7df9ff"), tile.getDefaultColor());
    }

    @Test
    void getDefaultColorAfterChange() {
        tile.changeColor(Color.BLACK);
        assertEquals(Color.valueOf("#7df9ff"), tile.getDefaultColor());
    }

    @Test
    void returnToDefaultColor() {
        tile.changeColor(Color.BLACK);
        tile.returnToDefaultColor();
        assertEquals(Color.valueOf("#7df9ff"), tile.getDefaultColor());
    }
}