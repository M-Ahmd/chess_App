package com.example.myapplication;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(Type.BISHOP, color,
        color == Color.WHITE ? R.drawable.white_bishop : R.drawable.black_bishop);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Cell[][] board) {
        int dx = Math.abs(endRow - startRow);
        int dy = Math.abs(endCol - startCol);
        if (dx != dy) return false;

        int rowStep = (endRow - startRow) / dx;
        int colStep = (endCol - startCol) / dy;

        for (int i = 1; i < dx; i++) {
            if (board[startRow + i * rowStep][startCol + i * colStep].getPiece() != null)
                return false;
        }

        Piece target = board[endRow][endCol].getPiece();
        return target == null || target.isWhite() != this.isWhite();
    }
}