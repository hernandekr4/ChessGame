package controller;
// Sergio Sauceda, Taven Hathaway, Lugh Dunlap, Kevin Hernandez
// Chess Game - CSCI-3331-001
// 11/8/2024
// Handles peice selection and movement.

import javafx.scene.input.MouseEvent;
import main.View;
import model.Model;
import model.Position;

public class Controller {
    private Model model;
    private View view;
    private Position selectedPosition = null;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
    }

    // Set up mouse click handlers
    private void addEventHandlers() {
        view.getBoardUI().setOnMouseClicked(this::handleMouseClick);
    }

    // Handle mouse clicks on the board
    private void handleMouseClick(MouseEvent event) {
        int col = (int) (event.getX() / 75);
        int row = (int) (event.getY() / 75);

        if (selectedPosition == null) {
            // First click: Select a piece
            if (model.getPieceAt(row, col) != null) {
                selectedPosition = new Position(row, col);
                System.out.println("Selected piece at (" + row + ", " + col + ")");
            }
        } else {
            // Second click: Attempt to move selected piece
            Position targetPosition = new Position(row, col);
            if (model.isMoveValid(selectedPosition, targetPosition)) {
                model.movePiece(selectedPosition, targetPosition);
                System.out.println("Moved piece to (" + row + ", " + col + ")");
            } else {
                System.out.println("Invalid move");
            }
            selectedPosition = null; // Reset selection
        }
    }
}