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

        // Debugging output
        System.out.println("Knight move from (" + position.getRow() + ", " + position.getCol() + 
                           ") to (" + newPosition.getRow() + ", " + newPosition.getCol() + 
                           ") - RowDiff: " + rowDiff + ", ColDiff: " + colDiff);

         // Valid if moving in an "L" shape
         if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            // Ensure the target square is either empty or occupied by an opponent
            ChessPiece targetPiece = model.getPieceAt(newPosition.getRow(), newPosition.getCol());
            if (targetPiece == null || !targetPiece.getColor().equals(this.color)) {
                return true;
            }
        }
        return false;
    }
}