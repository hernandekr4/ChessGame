package pieces;
import model.ChessPiece;
import model.Model;
import model.Position;

public class Queen extends ChessPiece {
    public Queen(Position position, String color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(Position newPosition, Model model) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        // Valid if moving in any direction (horizontally, vertically, or diagonally)
        return (rowDiff == colDiff) || (rowDiff == 0 || colDiff == 0);
    }
}
