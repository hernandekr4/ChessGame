public class King extends ChessPiece {
    public King(Position position, String color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(Position newPosition, Model model) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - position.getCol());

        // Valid if moving one square in any direction
        return rowDiff <= 1 && colDiff <= 1;
    }
}

