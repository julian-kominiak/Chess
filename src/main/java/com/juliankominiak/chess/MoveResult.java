package com.juliankominiak.chess;

//Helps managing the 3 possible moves: NONE, NORMAL and KILL.
public class MoveResult {

    private final MoveType type;

    public MoveType getType() {
        return type;
    }

    public MoveResult(MoveType type) {
        this.type = type;
    }
}