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
    void createPawns() throws FileNotFoundException {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (y == 1) {
                    assertEquals(new Pawn(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                } else if (y == 6) {
                    assertEquals(new Pawn(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                } else {
                    assertNotEquals(new Pawn(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                    assertNotEquals(new Pawn(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                }
            }
        }
    }

    @Test
    void createRooks() throws FileNotFoundException {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if ((x == 0 && y == 0) || (x == 7 && y == 0)) {
                    assertEquals(new Rook(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                } else if ((x == 0 && y == 7) || (x == 7 && y == 7)) {
                    assertEquals(new Rook(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                } else {
                    assertNotEquals(new Rook(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                    assertNotEquals(new Rook(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                }
            }
        }
    }

    @Test
    void createHorses() throws FileNotFoundException {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if ((x == 1 && y == 0) || (x == 6 && y == 0)) {
                    assertEquals(new Horse(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                } else if ((x == 1 && y == 7) || (x == 6 && y == 7)) {
                    assertEquals(new Horse(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                } else {
                    assertNotEquals(new Horse(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                    assertNotEquals(new Horse(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                }
            }
        }
    }

    @Test
    void createBishops() throws FileNotFoundException {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if ((x == 2 && y == 0) || (x == 5 && y == 0)) {
                    assertEquals(new Bishop(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                } else if ((x == 2 && y == 7) || (x == 5 && y == 7)) {
                    assertEquals(new Bishop(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                } else {
                    assertNotEquals(new Bishop(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                    assertNotEquals(new Bishop(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                }
            }
        }
    }

    @Test
    void createQueens() throws FileNotFoundException {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if ((x == 3 && y == 0)) {
                    assertEquals(new Queen(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                } else if ((x == 3 && y == 7)) {
                    assertEquals(new Queen(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                } else {
                    assertNotEquals(new Queen(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                    assertNotEquals(new Queen(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                }
            }
        }
    }

    @Test
    void createKings() throws FileNotFoundException {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if ((x == 4 && y == 0)) {
                    assertEquals(new King(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                } else if ((x == 4 && y == 7)) {
                    assertEquals(new King(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                } else {
                    assertNotEquals(new King(PieceColor.BLACK, x, y), Board.board[x][y].getPiece());
                    assertNotEquals(new King(PieceColor.WHITE, x, y), Board.board[x][y].getPiece());
                }
            }
        }
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
    void getKing() throws FileNotFoundException {
        assertEquals(new King(PieceColor.BLACK, 4, 0), Board.getKing(PieceColor.BLACK));
    }

    @Test
    void highlightCheckTile() {
        Board.check = PieceColor.BLACK;
        Board.highlightCheckTile(Color.RED);
        //assertEquals(Color.RED, Board.board[4][0].getColor());
    }

    @Test
    void initializeColors() {
    }
}