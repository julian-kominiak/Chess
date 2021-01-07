package com.juliankominiak.chess.Pieces;

import com.juliankominiak.chess.Board;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    Pawn pawn;
    Board board = new Board();

    @BeforeEach
    void setUp() throws FileNotFoundException {
        board = new Board();
        board.createBoard();
        pawn = (Pawn) Board.board[0][6].getPiece();
    }

    @Test
    void getColor() {
        assertEquals(PieceColor.WHITE, pawn.getColor());
    }

    @Test
    void getOldX() {
        assertEquals(0, pawn.getOldX());
    }

    @Test
    void getOldY() {
        assertEquals(600, pawn.getOldY());
    }

    @Test
    void move() {
        pawn.move(0, 2);
        assertEquals(0, pawn.getOldX());
        assertEquals(200, pawn.getOldY());
    }

    @Test
    void checkAvailabilityTrue() {
        assertTrue(pawn.checkAvailability(0, 6, 0, 5));
    }

    @Test
    void checkAvailabilityFalse() {
        assertFalse(pawn.checkAvailability(0, 6, 0, 3));
    }

    @Test
    void highlightAvailableMoves() {
        pawn.highlightAvailableMoves();
        assertEquals(Color.LIGHTGREEN, Board.board[0][5].getColor());
        assertEquals(Color.LIGHTGREEN, Board.board[0][4].getColor());
    }
}