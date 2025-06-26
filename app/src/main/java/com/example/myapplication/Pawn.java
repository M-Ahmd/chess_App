package com.example.myapplication;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(Type.PAWN, color,
        color == Color.WHITE ? R.drawable.white_pawn : R.drawable.black_pawn);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Cell[][] board) {
        Piece target = board[endRow][endCol].getPiece();


        if (target != null && target.isWhite() == this.isWhite()) {
            return false;
        }

        int direction = isWhite() ? -1 : 1;
        int startRowOriginal = isWhite() ? 6 : 1;

        // move forward one step
        if (startCol == endCol && target == null) {
            if (endRow == startRow + direction) return true;

            // first step may be two steps forward
            if (startRow == startRowOriginal && endRow == startRow + 2 * direction
                    && board[startRow + direction][startCol].getPiece() == null) {
                return true;
            }
        }

        // eat by passing diagonally
        if (Math.abs(endCol - startCol) == 1 && endRow == startRow + direction) {
            return target != null && target.isWhite() != this.isWhite();
        }

        return false;
    }

    @Override
    public int getImageRes() {
        return isWhite() ? R.drawable.white_pawn : R.drawable.black_pawn;
    }
}
