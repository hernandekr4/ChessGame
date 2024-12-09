import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import Model.Model;
import Pieces.ChessPiece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.util.Duration;
import Model.Position;
import java.util.function.Consumer;





    
public class View {
    private GridPane boardUI;
    private Model model; 
    private VBox root;
    private Label statusLabel;
    private Button quitButton;
    private Button restartButton;
    private HBox buttonBox;


    public View(Model model) {
        this.model = model;
        boardUI = new GridPane();
        statusLabel = new Label("White Turn's");
        statusLabel.setFont(new Font("Arial", 20));

              
        quitButton = new Button("Quit");
        restartButton = new Button("Restart");

        restartButton.setOnAction(e -> restartGame());
        quitButton.setOnAction(e -> quitGame());
    

         HBox buttonBox = new HBox(10, restartButton, quitButton); 
         buttonBox.setAlignment(Pos.CENTER);

        
        root =  new VBox(10, boardUI, statusLabel, buttonBox);
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
     private ImageView getPieceName(ChessPiece piece ) {
        String fileName = piece.getColor() + "_" + piece.getClass().getSimpleName().toLowerCase() + ".png";
        Image image = new Image(getClass().getResource("/resources/images/" + fileName).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(75);
        imageView.setFitWidth(75);
        return imageView;
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

public void setRestartAction(Runnable restartAction) {
    restartButton.setOnAction(e -> restartAction.run());
}

public void setQuitAction(Runnable quitAction) {
    quitButton.setOnAction(e -> quitAction.run());
}

private void restartGame() {
    model.reset(); // Reset model
    refreshBoard(model); // Refresh the board
    statusLabel.setText("White Turn's");
    boardUI.setDisable(false); // Enable board interactions
}

private void quitGame() {
    Platform.exit();
}



    
}

