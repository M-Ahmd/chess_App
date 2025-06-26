package com.example.myapplication;
import android.widget.ImageView;
public class Cell {
    private int row;
    private int col;
    private Piece piece;
    private ImageView view;

    public Cell(int row, int col, ImageView view) {
        this.row = row;
        this.col = col;
        this.view = view;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            view.setImageResource(piece.getImageRes());
        } else {
            view.setImageDrawable(null);
        }
    }

    public Piece getPiece() { return piece; }
    public ImageView getView() { return view; }
    public int getRow() { return row; }
    public int getCol() { return col; }
}
