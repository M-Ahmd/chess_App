package com.example.myapplication;

public class Queen extends Piece {
    public Queen(Color color) {
        super(Type.QUEEN, color,
        color == Color.WHITE ? R.drawable.white_queen : R.drawable.black_queen);
    }


    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Cell[][] board) {
        Rook rook = new Rook(getColor());
        Bishop bishop = new Bishop(getColor());

        if (!(rook.isValidMove(startRow, startCol, endRow, endCol, board) ||
                bishop.isValidMove(startRow, startCol, endRow, endCol, board))) {
            return false;
        }

        Piece target = board[endRow][endCol].getPiece();
        return target == null || target.isWhite() != this.isWhite();
    }
}