package com.example.myapplication;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private GridLayout chessBoard;
    private Cell[][] board = new Cell[8][8];
    private Cell selectedCell = null;
    private MoveManager moveManager = new MoveManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chessBoard = findViewById(R.id.chessBoard);
        createChessBoard();
    }

    private void createChessBoard() {
        int size = getResources().getDisplayMetrics().widthPixels / 8;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ImageView square = new ImageView(this);

                square.setBackgroundColor((row + col) % 2 == 0
                        ? getResources().getColor(R.color.chess_white)
                        : getResources().getColor(R.color.chess_blue));

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = size;
                params.height = size;
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);
                square.setLayoutParams(params);
                square.setScaleType(ImageView.ScaleType.FIT_CENTER);
                square.setAdjustViewBounds(true);

                Cell cell = new Cell(row, col, square);
                board[row][col] = cell;

                square.setOnClickListener(v -> onCellClick(cell));

                chessBoard.addView(square);
            }
        }

        initPieces();
    }

    private void initPieces() {
        for (int col = 0; col < 8; col++) {
            board[1][col].setPiece(new Pawn(Piece.Color.BLACK));
            board[6][col].setPiece(new Pawn(Piece.Color.WHITE));
        }

        board[0][0].setPiece(new Rook(Piece.Color.BLACK));
        board[0][1].setPiece(new Knight(Piece.Color.BLACK));
        board[0][2].setPiece(new Bishop(Piece.Color.BLACK));
        board[0][3].setPiece(new Queen(Piece.Color.BLACK));
        board[0][4].setPiece(new King(Piece.Color.BLACK));
        board[0][5].setPiece(new Bishop(Piece.Color.BLACK));
        board[0][6].setPiece(new Knight(Piece.Color.BLACK));
        board[0][7].setPiece(new Rook(Piece.Color.BLACK));

        board[7][0].setPiece(new Rook(Piece.Color.WHITE));
        board[7][1].setPiece(new Knight(Piece.Color.WHITE));
        board[7][2].setPiece(new Bishop(Piece.Color.WHITE));
        board[7][3].setPiece(new Queen(Piece.Color.WHITE));
        board[7][4].setPiece(new King(Piece.Color.WHITE));
        board[7][5].setPiece(new Bishop(Piece.Color.WHITE));
        board[7][6].setPiece(new Knight(Piece.Color.WHITE));
        board[7][7].setPiece(new Rook(Piece.Color.WHITE));
    }

    private void onCellClick(Cell targetCell) {
        if (selectedCell == null) {
            if (targetCell.getPiece() != null &&
                    targetCell.getPiece().isWhite() == moveManager.isWhitesTurn()) {
                selectedCell = targetCell;
                selectedCell.getView().setAlpha(0.5f);
            }
            return;
        }

        boolean moved = moveManager.handleMove(selectedCell, targetCell, board);

        selectedCell.getView().setAlpha(1f);
        selectedCell = null;

        if (moved) {
            boolean isCheckmate = moveManager.isCheckmate(moveManager.isWhitesTurn(), board);
            if (isCheckmate) {
                String winner = moveManager.isWhitesTurn() ? "Black" : "White";
                showGameOverDialog(winner);
            }
        }
    }

    private void showGameOverDialog(String winner) {
        new AlertDialog.Builder(this)
                .setTitle("GAME OVER")
                .setMessage(winner + " wins, Do you wanna play again?")
                .setPositiveButton("YES", (dialog, which) -> resetGame())
                .setNegativeButton("NO", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void resetGame() {
        chessBoard.removeAllViews();
        createChessBoard();
        moveManager = new MoveManager();
        selectedCell = null;
    }
}
