package com.juliankominiak.chess;

import com.juliankominiak.chess.Pieces.*;
import javafx.application.Platform;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.util.Optional;

import static com.juliankominiak.chess.Board.toBoard;

public class DialogClass {

    //Creates a Game Over dialog.
    public void showEndGameDialog() throws FileNotFoundException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(Main.scene.getWindow());
        dialog.setTitle("Game Over");
        String winner = null;
        switch (Board.check) {
            case WHITE:
                winner = "Black";
                break;
            case BLACK:
                winner = "White";
                break;
        }
        dialog.getDialogPane().setHeaderText(winner + " wins!");
        ButtonType playAgainButton = new ButtonType("Play Again");
        ButtonType quitButton = new ButtonType("Quit");

        dialog.getDialogPane().getButtonTypes().add(playAgainButton);
        dialog.getDialogPane().getButtonTypes().add(quitButton);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (result.get() == playAgainButton) {
                Main.playAgain();
            }
            if (result.get() == quitButton) {
                Platform.exit();
            }
        }
    }

    //Creates a transformation dialog for Pawns that reached the end of the board.
    public void showTransformDialog(Pawn pawn) throws FileNotFoundException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(Main.scene.getWindow());
        dialog.setTitle("Transform");
        String color = null;
        switch (pawn.getColor()) {
            case WHITE:
                color = "White";
                break;
            case BLACK:
                color = "Black";
                break;
        }
        dialog.getDialogPane().setHeaderText(color + " pawn transforms into a: ");
        ButtonType transformToRookButton = new ButtonType("Rook");
        ButtonType transformToKnightButton = new ButtonType("Knight");
        ButtonType transformToBishopButton = new ButtonType("Bishop");
        ButtonType transformToQueenButton = new ButtonType("Queen");

        dialog.getDialogPane().getButtonTypes().add(transformToRookButton);
        dialog.getDialogPane().getButtonTypes().add(transformToKnightButton);
        dialog.getDialogPane().getButtonTypes().add(transformToBishopButton);
        dialog.getDialogPane().getButtonTypes().add(transformToQueenButton);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (result.get() == transformToRookButton) {
                pawn.transform(new Rook(pawn.getColor(), toBoard(pawn.getOldX()),
                        toBoard(pawn.getOldY())));
            }
            if (result.get() == transformToKnightButton) {
                pawn.transform(new Horse(pawn.getColor(), toBoard(pawn.getOldX()),
                        toBoard(pawn.getOldY())));
            }
            if (result.get() == transformToBishopButton) {
                pawn.transform(new Bishop(pawn.getColor(), toBoard(pawn.getOldX()),
                        toBoard(pawn.getOldY())));
            }
            if (result.get() == transformToQueenButton) {
                pawn.transform(new Queen(pawn.getColor(), toBoard(pawn.getOldX()),
                        toBoard(pawn.getOldY())));
            }
        }
    }
}
