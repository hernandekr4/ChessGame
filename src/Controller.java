

import Model.Model;
import Model.Position;
import javafx.scene.input.MouseEvent;


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
                // Notify the view to refresh
                view.refreshBoard(model);
            } else {
                System.out.println("Invalid move");
            }
            selectedPosition = null; // Reset selection
        }
    }
}