import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import Model.Model;
import Pieces.ChessPiece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.animation.PauseTransition;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.util.Duration;
import Model.Position;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
    
public class View {
    private GridPane boardUI;
    private Model model; 
    private VBox root;
    private Label statusLabel;


    public View(Model model) {
        this.model = model;
        boardUI = new GridPane();
        statusLabel = new Label("White Turn's");
        statusLabel.setFont(new Font("Arial", 20));
        root =  new VBox(boardUI, statusLabel);
        createBoardUI();

        //root.getChildren().addAll(boardUI, statusLabel);
    }



    // Create the chessboard grid
    private void createBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = new StackPane();
                Rectangle tile = new Rectangle(75, 75);
                tile.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN);
                square.getChildren().add(tile);


                 // Add piece image if a piece exists at this position
                ChessPiece piece = model.getPieceAt(row, col);
                if (piece != null) {
                    ImageView imageView = getPieceImage(piece);
                    square.getChildren().add(imageView);
                }

                boardUI.add(square, col, row);
            }
        }
    }

    public GridPane getBoardUI() {
        return boardUI;
    }

      // Get the correct chess piece image for the initial board setup
      private ImageView getPieceImage(ChessPiece piece) {
        String fileName = piece.getColor() + "_"+ piece.getClass().getSimpleName().toLowerCase()+ ".png";
        
        // Load image using the class loader
        Image image = new Image(getClass().getResource("/resources/images/" + fileName).toExternalForm());
        
        // Add the image to the ImageView and resize it to fit the tile
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(75);
        imageView.setFitWidth(75);
        
        return imageView;
    }


     // Return the name of the image file (without extension) for each piece
     private String getPieceName(int row, int col) {
        if (row == 1) return "pawn_white";
        if (row == 6) return "pawn_black";
        
        // Assign major pieces for row 0 and row 7
        if (row == 0 || row == 7) {
            String color = row == 0 ? "white" : "black";
            switch (col) {
                case 0: case 7: return "rook_" + color;
                case 1: case 6: return "knight_" + color;
                case 2: case 5: return "bishop_" + color;
                case 3: return row == 0 ? "queen_white" : "queen_black";
                case 4: return row == 0 ? "king_white" : "king_black";
            }
        }
        
        return null; // No piece on this tile
    }

 

    public void refreshBoard(Model model) {
        boardUI.getChildren().clear(); // Clear the board UI
    
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = new StackPane();
    
                // Create the tile color
                Rectangle tile = new Rectangle(75, 75);
                tile.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN);
                square.getChildren().add(tile);
    
                // Add the piece image if a piece exists at this position
                ChessPiece piece = model.getPieceAt(row, col);
                if (piece != null) {
                    ImageView pieceImage = getPieceImage(piece);
                    square.getChildren().add(pieceImage);
                }
    
                boardUI.add(square, col, row);
            }
        }
    }

    public void endGame(String winnerColor){
        displayWinner(winnerColor);
        boardUI.setDisable(true);
    }


    public void highlightSquare(Position position, Color color){
        StackPane square = getSquareAt(position);
        if(square != null){
            Rectangle tile = (Rectangle) square.getChildren().get(0);
            tile.setFill(color);
        }
    }


    public void clearHighlight(Position position){
        StackPane square = getSquareAt(position);
        if(square !=null){
            Rectangle tile =(Rectangle) square.getChildren().get(0);
            tile.setFill((position.getRow()+ position.getCol()) % 2 ==0? Color.BEIGE : Color.BROWN);
        }
    }

    public VBox getRoot() {
    return root; // Return the entire layout, not just the board
}



    public void flashInvalidSquare(Position position){
        highlightSquare(position, Color.RED);
        statusLabel.setText("Invalid move");
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> {
            clearHighlight(position);
            statusLabel.setText(model.getCurrentTurn().equals("white") ? "White's Turn" : "Black's Turn");
        });
        pause.play();
    }

    // Method to update the status label with the current turn
public void updateTurn(String currentTurn) {
    statusLabel.setText(currentTurn.equals("white") ? "White's Turn" : "Black's Turn");
}

// Method to display the winner
public void displayWinner(String winner) {
    statusLabel.setText(winner + " Wins!");
    boardUI.setDisable(true);
}

// Helper method to get a square at a specific position
private StackPane getSquareAt(Position position) {
    for (javafx.scene.Node node : boardUI.getChildren()) {
        if (GridPane.getRowIndex(node) == position.getRow() &&
            GridPane.getColumnIndex(node) == position.getCol()) {
            return (StackPane) node;
        }
    }
    return null;
}

    
}

