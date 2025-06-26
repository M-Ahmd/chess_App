package com.example.myapplication;
public class Rook extends Piece {

    public Rook(Color color) {
        super(Type.ROOK, color,
        color == Color.WHITE ? R.drawable.white_rook : R.drawable.black_rook);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Cell[][] board) {
        if (startRow != endRow && startCol != endCol) return false;

        int stepRow = Integer.compare(endRow, startRow);
        int stepCol = Integer.compare(endCol, startCol);

        int r = startRow + stepRow;
        int c = startCol + stepCol;

        while (r != endRow || c != endCol) {
            if (board[r][c].getPiece() != null) return false;
            r += stepRow;
            c += stepCol;
        }

        Piece target = board[endRow][endCol].getPiece();
        return target == null || target.isWhite() != this.isWhite();
    }


}