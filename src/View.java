// Sergio Sauceda, Taven Hathaway, Lugh Dunlap, Kevin Hernandez
// Chess Game - CSCI-3331-001
// 11/8/2024
// Designs the board

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class View {
    private GridPane boardUI;

    public View() {
        boardUI = new GridPane();
        createBoardUI();
    }

    // Create the chessboard grid
    private void createBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = new StackPane();
                Rectangle tile = new Rectangle(75, 75);
                tile.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN);
                square.getChildren().add(tile);
                boardUI.add(square, col, row);
            }
        }
    }

    public GridPane getBoardUI() {
        return boardUI;
    }
}

