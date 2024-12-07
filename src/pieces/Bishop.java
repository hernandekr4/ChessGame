package Pieces;
import Model.Model;
import Model.Position;

public class Bishop extends ChessPiece {
    public Bishop(Position position, String color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(Position newPosition, Model model) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        // Valid if moving diagonally
        return rowDiff == colDiff;
    }
}