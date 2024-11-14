public class Rook extends ChessPiece {
    public Rook(Position position, String color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(Position newPosition, Model model) {
        int rowDiff = newPosition.getRow() - position.getRow();
        int colDiff = newPosition.getCol() - position.getCol();

        // Valid if moving in a straight line horizontally or vertically
        return (rowDiff == 0 || colDiff == 0);
    }
}
