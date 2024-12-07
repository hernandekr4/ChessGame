package Pieces;

import Model.Model;
import Model.Position;

public class Pawn extends ChessPiece {
    public Pawn(Position position, String color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(Position newPosition, Model model) {
        int rowDiff = newPosition.getRow() - position.getRow();
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        // Moving forward (1 square forward, or 2 if on starting row)
        if (colDiff == 0) {
            if (color.equals("white")) {
                return (rowDiff == 1 || (position.getRow() == 1 && rowDiff == 2));
            } else { // Black pawn moves downwards
                return (rowDiff == -1 || (position.getRow() == 6 && rowDiff == -2));
            }
        }

        // Diagonal capture
        return colDiff == 1 && ((color.equals("white") && rowDiff == 1) || (color.equals("black") && rowDiff == -1));
    }
}
