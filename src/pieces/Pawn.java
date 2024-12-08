package Pieces;

import Model.Model;
import Model.Position;

public class Pawn extends ChessPiece {
    public Pawn(Position position, String color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(Position newPosition, Model model) {
        if(newPosition == null){
            return false;
        }

        int rowDiff = newPosition.getRow() - position.getRow();
        int colDiff = newPosition.getCol() - position.getCol();
        ChessPiece targetPiece = model.getPieceAt(newPosition.getRow(), newPosition.getCol());

        int direction = color.equals("white") ? 1: -1;

        // Moving forward (1 square forward, or 2 if on starting row)
        if(colDiff == 0 && targetPiece == null){
            if(rowDiff == direction){
                return true;
            }

            if(position.getRow() == (color.equals("white") ? 1:6) && rowDiff == 2 * direction){
                Position intermediatePosition = new Position(position.getRow()+ direction, position.getCol());
                return model.getPieceAt(intermediatePosition.getRow(), intermediatePosition.getCol()) == null;
            }
        }

        if(Math.abs(colDiff) ==1 && rowDiff == direction && targetPiece != null && !targetPiece.getColor().equals(color)){
            return true;
        }
        return false;

        }
}
