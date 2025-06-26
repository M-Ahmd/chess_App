package com.example.myapplication;

/**
 * abstract class for all the piece
 * */
public abstract class Piece {
    public enum Type { PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING }
    public enum Color { WHITE, BLACK }

    private Type type;
    private Color color;
    private int imageRes;

    private boolean hasMoved = false;
    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }
    public Piece(Type type, Color color, int imageRes) {
        this.type = type;
        this.color = color;
        this.imageRes = imageRes;
    }
    public boolean isWhite() {
        return color == Color.WHITE;
    }
    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public int getImageRes() {
        return imageRes;
    }
    private boolean sameColor(Piece p1, Piece p2) {
        return p1.color == p2.color;
    }

    public boolean canMoveTo(Cell from, Cell to, Cell[][] board) {

        if (to.getPiece() != null && sameColor(from.getPiece(), to.getPiece())) {
            return false;
        }


        return isValidMove(from.getRow(), from.getCol(), to.getRow(), to.getCol(), board);
    }
    public abstract boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Cell[][] board);


}