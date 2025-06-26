package com.example.myapplication;

public class King extends Piece {
    public King(Color color) {
        super(Type.KING, color,
        color == Color.WHITE ? R.drawable.white_king : R.drawable.black_king);
    }



    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Cell[][] board) {
        int dx = Math.abs(endRow - startRow);
        int dy = Math.abs(endCol - startCol);

        // Normal move
        if (dx <= 1 && dy <= 1) return true;

        // Allow castling pattern; actual checks are in MoveManager
        if (!hasMoved() && dx == 0 && dy == 2) return true;

        return false;
    }
}