package Pieces;
import Model.Model;
import Model.Position;

public class Knight extends ChessPiece {
    public Knight(Position position, String color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(Position newPosition, Model model) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        // Valid if moving in an "L" shape
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
}