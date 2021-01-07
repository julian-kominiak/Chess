package com.juliankominiak.chess;

import com.juliankominiak.chess.Pieces.*;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Objects;

import static com.juliankominiak.chess.Board.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        board = new Board();
        board.createBoard();
    }

    @Test
    void createBoard() {
        assertNotNull(tileGroup);
        assertNotNull(pieceGroup);
        assertNotNull(Board.board[2][3]);
    }

    @Test
    void toBoard() {
        assertEquals(0, Board.toBoard(0));
        assertEquals(1, Board.toBoard(50));
        assertEquals(1, Board.toBoard(149));
        assertEquals(2, Board.toBoard(150));
        assertEquals(3, Board.toBoard(250));
    }

    @Test
    void lookForCheckFalseIfEmpty() {
        board = new Board();
        assertFalse(lookForCheck());
    }

    @Test
    void lookForCheckFalseIfNewGame() {
        assertFalse(lookForCheck());
    }

    @Test
    void undoCheck() {
        check = PieceColor.WHITE;
        Board.undoCheck();
        assertNull(check);
    }

    @Test
    void lookForCheckMateFalseIfNewGame() {
        assertFalse(lookForCheckMate(Objects.requireNonNull(Board.getKing(PieceColor.WHITE))));
    }

    @Test
    void getKing() {
        King king = Board.getKing(PieceColor.BLACK);
        assertEquals(PieceColor.BLACK, king.getColor());
        assertEquals(400, king.getOldX());
        assertEquals(0, king.getOldY());
    }

    @Test
    void highlightCheckTile() {
        Board.check = PieceColor.BLACK;
        Board.highlightCheckTile(Color.RED);
        //assertEquals(Color.RED, Board.board[4][0].getColor());
    }

    @Test
    void initializeColors() {
        Board.initializeColors();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                assertEquals(Board.board[x][y].getDefaultColor(), Board.board[x][y].getColor());
            }
        }
    }
}