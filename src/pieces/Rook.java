package Pieces;
import Model.Model;
import Model.Position;

public class Rook extends ChessPiece {
    public Rook(Position position, String color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(Position newPosition, Model model) {
        

        if (newPosition == null) {
            return false; // Invalid move
        }
        int rowDiff = newPosition.getRow() - position.getRow();
        int colDiff = newPosition.getCol() - position.getCol();

        // Ensure the target square does not contain a piece of the same color
        ChessPiece targetPiece = model.getPieceAt(newPosition.getRow(), newPosition.getCol());
        if (targetPiece != null && targetPiece.getColor().equals(this.color)) {
            return false;
        }


        // Valid if moving in a straight line horizontally or vertically
        return (rowDiff == 0 || colDiff == 0);
    }
}
