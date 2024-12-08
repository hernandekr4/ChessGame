

import java.util.Optional;

import Model.Model;
import Model.Position;
import Pieces.Bishop;
import Pieces.ChessPiece;
import Pieces.King;
import Pieces.Knight;
import Pieces.Queen;
import Pieces.Rook;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


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

    private void handleMouseClick(MouseEvent event) {

        // If the board is disabled (game over), do not process the click
        if(view.getBoardUI().isDisable()){
            return;
        }
    
        // Convert mouse click coordinates into board row and column
        int col = (int) (event.getX() / 75);
        int row = (int) (event.getY() / 75);
        Position clickedPosition = new Position(row, col);
    
        // First Click: Select a piece
        if (selectedPosition == null) {
            ChessPiece piece = model.getPieceAt(row, col);
            // Check if the selected piece belongs to the player whose turn it is
            if (piece != null && piece.getColor().equals(model.getCurrentTurn())) {
                selectedPosition = clickedPosition;
                view.highlightSquare(selectedPosition, Color.LIGHTGREEN);
            } else {
                view.flashInvalidSquare(clickedPosition);
            } 
        } 
        // Second Click: Move the selected piece
        else {
            String result = model.movePiece(selectedPosition, clickedPosition);
    
            if(result.equals("promote")){
                // Pawn Promotion Logic
                showPromotionDialog(clickedPosition);
            } 
            else if (result.equals("moved")) {
                // Update the board, turn, and UI after a successful move
                view.refreshBoard(model);
                view.updateTurn(model.getCurrentTurn());
    
                // **Check if a king was captured**
                String winner = model.isKingCaptured();
                if (winner != null) {
                    view.displayWinner(winner + " wins!");
                    view.getBoardUI().setDisable(true);
                    return; // End the game
                }
    
                // **Checkmate Detection**
                boolean checkmate = model.isCheckmate(model.getCurrentTurn());
                if (checkmate) {
                    String winningPlayer = model.getCurrentTurn().equals("white") ? "Black" : "White";
                    view.displayWinner("Checkmate! " + winningPlayer + " wins!");
                    view.getBoardUI().setDisable(true);
                    return; // End the game
                }
    
                // **Check Detection**
                if (model.checkForCheck("white")) {
                    view.updateTurn("Check on White King!");
                } else if (model.checkForCheck("black")) {
                    view.updateTurn("Check on Black King!");
                }
    
            } 
            else {
                // Flash the square red and display "Invalid move"
                view.flashInvalidSquare(clickedPosition);
            }
    
            // Clear selection and highlight
            view.clearHighlight(selectedPosition);
            selectedPosition = null;
        }
    }
    
    private void showPromotionDialog(Position position) {
    ChoiceDialog<String> dialog = new ChoiceDialog<>("Queen", "Queen", "Rook", "Bishop", "Knight");
    dialog.setTitle("Pawn Promotion");
    dialog.setHeaderText("Promote your pawn!");
    dialog.setContentText("Choose a piece:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(choice -> {
        model.promotePawn(position, choice);
        view.refreshBoard(model);
        view.updateTurn(model.getCurrentTurn());
        // Switch the turn after the promotion
        //model.toggleTurn(); 
        view.updateTurn(model.getCurrentTurn());
    });
}




}