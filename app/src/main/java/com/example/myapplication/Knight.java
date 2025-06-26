package com.example.myapplication;

public class Knight extends Piece {
    public Knight(Color color) {
        super(Type.KNIGHT, color,
        color == Color.WHITE ? R.drawable.white_knight : R.drawable.black_knight);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Cell[][] board) {
        int dx = Math.abs(endRow - startRow);
        int dy = Math.abs(endCol - startCol);

        // حركة الحصان تكون 2x1 أو 1x2
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}