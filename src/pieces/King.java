package Pieces;

import Model.Model;
import Model.Position;

public class King extends ChessPiece {
    public King(Position position, String color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(Position newPosition, Model model) {

        if (newPosition == null) {
            return false;
        }

        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        // Ensure the target square does not contain a piece of the same color
        ChessPiece targetPiece = model.getPieceAt(newPosition.getRow(), newPosition.getCol());
        if (targetPiece != null && targetPiece.getColor().equals(this.color)) {
            return false;
        }


        // Valid if moving one square in any direction
        return rowDiff <= 1 && colDiff <= 1;
    }
}

