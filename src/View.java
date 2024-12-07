

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import Model.Model;
import Pieces.ChessPiece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
    
public class View {
    private GridPane boardUI;
    private Model model; 


    public View() {
        boardUI = new GridPane();
        model = new Model(); // Create a new model for initial positions

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
      private ImageView getPieceImage(int row, int col) {
        String pieceName = getPieceName(row, col);
        
        if (pieceName == null) {
            return null; // No piece on this tile
        }
        
        // Load image using the class loader
        Image image = new Image(getClass().getResource("/resources/images/" + pieceName + ".png").toExternalForm());
        
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

    private ImageView getPieceImage(ChessPiece piece) {
        // Build the file name dynamically
        String fileName = piece.getColor() + "_" + piece.getClass().getSimpleName().toLowerCase() + ".png";

        // Load the image
        Image image = new Image(getClass().getResource("/resources/images/" + fileName).toExternalForm());

        // Set image properties
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
    
}

