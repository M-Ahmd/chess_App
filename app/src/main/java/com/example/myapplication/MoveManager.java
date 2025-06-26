package com.example.myapplication;
import android.util.Log;
public class MoveManager {
    private boolean isWhiteTurn = true;

    public boolean isWhitesTurn() {
        return isWhiteTurn;
    }

    private boolean canCastle(Cell from, Cell to, Cell[][] board) {
        Log.d("Castling", "Checking if castling is allowed...");
        King king = (King) from.getPiece();
        if (king.hasMoved()) {
            Log.d("Castling", "Checking if castling is allowed...");
            return false;
        }

        int row = from.getRow();
        int colFrom = from.getCol();
        int colTo = to.getCol();
        int rookCol = colTo == 6 ? 7 : 0;
        Cell rookCell = board[row][rookCol];

        if (rookCell.getPiece() == null || !(rookCell.getPiece() instanceof Rook)) {
            Log.d("Castling", "No rook found at the correct side.");
            return false;
        }
        Rook rook = (Rook) rookCell.getPiece();
        if (rook.hasMoved()) {
            Log.d("Castling", "Rook has already moved. Cannot castle.");
            return false;
        }

        // Check if squares between are empty
        int step = (colTo > colFrom) ? 1 : -1;
        for (int c = colFrom + step; c != colTo; c += step) {
            if (board[row][c].getPiece() != null) {
                Log.d("Castling", "Square at col " + c + " is not empty.");
                return false;
            }
        }

        // Check if king is in check or passing through check
        for (int c = colFrom + step; c != colTo + step; c += step) {
            Piece temp = board[row][c].getPiece();
            board[row][c].setPiece(king);
            board[row][colFrom].setPiece(null);
            if (!isKingSafe(board, king.isWhite())) {
                Log.d("Castling", "King would be in check at col " + c);
                board[row][colFrom].setPiece(king);
                board[row][c].setPiece(temp);
                return false;
            }
            board[row][colFrom].setPiece(king);
            board[row][c].setPiece(temp);
        }
        Log.d("Castling", "Castling is allowed!");
        return true;
    }

    private void performCastling(Cell kingCell, Cell to, Cell[][] board) {
        Log.d("Castling", "Performing castling move...");

        int row = kingCell.getRow();
        int colFrom = kingCell.getCol();
        int colTo = to.getCol();
        int rookFromCol = (colTo == 6) ? 7 : 0;
        int rookToCol = (colTo == 6) ? 5 : 3;

        Cell rookFrom = board[row][rookFromCol];
        Cell rookTo = board[row][rookToCol];

        Log.d("Castling", "Moving rook from col " + rookFromCol + " to " + rookToCol);

        rookTo.setPiece(rookFrom.getPiece());
        rookFrom.setPiece(null);
        rookTo.getPiece().setMoved(true);

        to.setPiece(kingCell.getPiece());
        kingCell.setPiece(null);
        to.getPiece().setMoved(true);

        Log.d("Castling", "King castled to col " + colTo);
    }

    private boolean isKingSafe(Cell[][] board, boolean forWhite) {
        Cell kingCell = findKing(board, forWhite);
        if (kingCell == null) return false;

        for (Cell[] row : board) {
            for (Cell cell : row) {
                Piece p = cell.getPiece();
                if (p != null && p.isWhite() != forWhite) {
                    boolean threatens = p.canMoveTo(cell, kingCell, board);
                    Log.d("ThreatCheck", p.getType() + " at (" + cell.getRow() + "," + cell.getCol() + ") threatens king? " + threatens);
                    if (threatens) {
                        Log.d("Threat", "King threatened by " + p.getType() + " at row " + cell.getRow() + " col " + cell.getCol());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Cell findKing(Cell[][] board, boolean white) {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                Piece p = cell.getPiece();
                if (p != null && p.getType() == Piece.Type.KING && p.isWhite() == white) {
                    return cell;
                }
            }
        }
        return null;
    }

    public boolean handleMove(Cell from, Cell to, Cell[][] board) {
        Piece selected = from.getPiece();
        if (selected == null || selected.isWhite() != isWhiteTurn) return false;

        // Try castling
        if (selected instanceof King && Math.abs(to.getCol() - from.getCol()) == 2) {
            Log.d("Castling", "King is attempting to castle...");
            if (canCastle(from, to, board)) {
                // Backup rook and king
                Cell rookFrom = board[from.getRow()][(to.getCol() == 6) ? 7 : 0];
                Cell rookTo = board[from.getRow()][(to.getCol() == 6) ? 5 : 3];
                Piece rookPiece = rookFrom.getPiece();
                Piece king = from.getPiece();

                // Perform castling
                performCastling(from, to, board);

                // Temporarily switch turn to check safety for current player
                if (!isKingSafe(board, isWhiteTurn)) {
                    // Undo castling
                    from.setPiece(to.getPiece());
                    to.setPiece(null);
                    rookFrom.setPiece(rookTo.getPiece());
                    rookTo.setPiece(null);
                    return false;
                }

                isWhiteTurn = !isWhiteTurn;
                return true;
            } else {
                return false;
            }
        }

        // Regular move
        if (!selected.canMoveTo(from, to, board)) return false;

        Piece temp = to.getPiece();
        to.setPiece(selected);
        from.setPiece(null);
        selected.setMoved(true);

        // Check king safety for **current turn**, not future
        if (!isKingSafe(board, isWhiteTurn)) {
            from.setPiece(selected);
            to.setPiece(temp);
            selected.setMoved(false);
            return false;
        }

        isWhiteTurn = !isWhiteTurn;
        return true;
    }

    public boolean isCheckmate(boolean whiteTurn, Cell[][] board) {
        if (isKingSafe(board, whiteTurn)) return false;

        for (Cell[] row : board)
            for (Cell cell : row) {
                Piece p = cell.getPiece();
                if (p == null || p.isWhite() != whiteTurn) continue;

                for (int r = 0; r < 8; r++)
                    for (int c = 0; c < 8; c++) {
                        Cell target = board[r][c];
                        if (!p.canMoveTo(cell, target, board)) continue;

                        Piece originalPiece = target.getPiece();
                        target.setPiece(p);
                        cell.setPiece(null);

                        boolean stillInCheck = !isKingSafe(board, whiteTurn);

                        cell.setPiece(p);
                        target.setPiece(originalPiece);

                        if (!stillInCheck) return false;
                    }
            }

        return true;

    }
}