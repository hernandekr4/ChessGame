// Sergio Sauceda, Taven Hathaway, Lugh Dunlap, Kevin Hernandez
// Chess Game - CSCI-3331-001
// 11/8/2024
// Abstract class for the peices.

public abstract class ChessPiece {
    protected Position position;
    protected String color; // "white" or "black"

    public ChessPiece(Position position, String color) {
        this.position = position;
        this.color = color;
    }

    public abstract boolean isValidMove(Position newPosition, Model model);
    
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public String getColor() {
        return color;
    }
}