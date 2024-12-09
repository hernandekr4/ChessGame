package Model;
// Sergio Sauceda,  Kevin Hernandez, Taven Hathaway. 
// Chess Game - CSCI-3331-001
// 11/8/2024
// Sets up board and stage

import java.util.ArrayList;
import java.util.List;

import Pieces.Bishop;
import Pieces.ChessPiece;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Queen;
import Pieces.Rook;

public class Model {
    private ChessPiece[][] board;
    private String currentTurn = "white"; // Alternates between "white" and "black"

    public Model() {
        board = new ChessPiece[8][8];
        initializePieces();
    }

    // Initializes the board with all pieces in their starting positions
    private void initializePieces() {
        // Place pawns
        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn(new Position(1, col), "white");
            board[6][col] = new Pawn(new Position(6, col), "black");
        }
        
        // Place other pieces
        board[0][0] = new Rook(new Position(0, 0), "white");
        board[0][7] = new Rook(new Position(0, 7), "white");
        board[7][0] = new Rook(new Position(7, 0), "black");
        board[7][7] = new Rook(new Position(7, 7), "black");

        board[0][1] = new Knight(new Position(0, 1), "white");
        board[0][6] = new Knight(new Position(0, 6), "white");
        board[7][1] = new Knight(new Position(7, 1), "black");
        board[7][6] = new Knight(new Position(7, 6), "black");

        board[0][2] = new Bishop(new Position(0, 2), "white");
        board[0][5] = new Bishop(new Position(0, 5), "white");
        board[7][2] = new Bishop(new Position(7, 2), "black");
        board[7][5] = new Bishop(new Position(7, 5), "black");

        board[0][3] = new Queen(new Position(0, 3), "white");
        board[7][3] = new Queen(new Position(7, 3), "black");

        board[0][4] = new King(new Position(0, 4), "white");
        board[7][4] = new King(new Position(7, 4), "black");
    }

    public ChessPiece getPieceAt(int row, int col) {
        return board[row][col];
    }

    // Moves a piece if valid, otherwise returns false
    public String movePiece(Position from, Position to) {
        ChessPiece piece = getPieceAt(from.getRow(), from.getCol());

        if (piece != null && piece.getColor().equals(currentTurn) && piece.isValidMove(to, this) && isPathClear(from, to)) {
            if (board[to.getRow()][to.getCol()] != null) {
                capturePiece(to); // Capture opponent piece if present
            }
            board[to.getRow()][to.getCol()] = piece; // Move piece to new position
            board[from.getRow()][from.getCol()] = null; // Clear old position
            piece.setPosition(to); // Update piece position

            if(piece instanceof Pawn){
                int lastRow = piece.getColor().equals("white") ? 7:0;
                if(to.getRow() == lastRow){
                    return "promote";
                }
            }

            toggleTurn(); // Switch turn to the other player
            return "moved";
        }
        return "invalid";
    }

    // Checks if the path is clear for pieces that move along a path (Rook, Bishop, Queen)
    private boolean isPathClear(Position from, Position to) {
        if (from == null || to == null) {
            return false; // Invalid positions
        }
    
        ChessPiece piece = getPieceAt(from.getRow(), from.getCol());


              // **Knights do not need a clear path check**
    if (piece instanceof Pieces.Knight) {
        //Skipping path check for knight
        return true;
    }
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();


        int rowStep = Integer.signum(rowDiff);
        int colStep = Integer.signum(colDiff);

        // Check each square along the path to see if it's occupied
        int row = from.getRow() + rowStep;
        int col = from.getCol() + colStep;

        while (row != to.getRow() || col != to.getCol()) {
            if (board[row][col] != null) {
                return false; // Path is blocked
            }
            row += rowStep;
            col += colStep;
        }
        return true;
    }

    // Captures a piece at the given position
    private void capturePiece(Position position) {
        ChessPiece capturedPiece = board[position.getRow()][position.getCol()];
        if (capturedPiece != null) {
            // Capture logic could be added here (e.g., adding to a list of captured pieces)
            System.out.println(capturedPiece.getColor() + " " + capturedPiece.getClass().getSimpleName() + " captured!");
        }
        board[position.getRow()][position.getCol()] = null;
    }

    // Switches the turn to the other player
    public void toggleTurn() {
        currentTurn = currentTurn.equals("white") ? "black" : "white";
    }

    // Checks if the move is valid based on piece rules and board state
    public boolean isMoveValid(Position from, Position to) {
        /*
        ChessPiece piece = getPieceAt(from.getRow(), from.getCol());
        return piece != null && piece.isValidMove(to, this) && isPathClear(from, to);

        */

        // Ensure the move is within bounds
    if (to.getRow() < 0 || to.getRow() >= 8 || to.getCol() < 0 || to.getCol() >= 8) {
        System.out.println("Move is out of bounds!");
        return false;
    }

    ChessPiece piece = getPieceAt(from.getRow(), from.getCol());
    ChessPiece targetPiece = getPieceAt(to.getRow(), to.getCol());

    // Ensure the piece exists and belongs to the current player
    if (piece == null || !piece.getColor().equals(currentTurn)) {
        System.out.println("Invalid piece selection or not your turn!");
        return false;
    }

    // Ensure the target square is not occupied by a piece of the same color
    if (targetPiece != null && targetPiece.getColor().equals(piece.getColor())) {
        System.out.println("Target square occupied by your own piece!");
        return false;
    }

    // Debug: Validate the move using the piece's specific rules
    boolean isValid = piece.isValidMove(to, this);
    System.out.println("Knight move validation result: " + isValid);

    return isValid;



    }

    // Getter for current turn
    public String getCurrentTurn() {
        return currentTurn;
    }

    public boolean checkForCheck(String color){
        Position kingPosition = findKingPosition(color);
        if (kingPosition == null) {
            return false; // No king found
        }
        for(ChessPiece[] row : board){
            for(ChessPiece piece: row){
                if(piece !=null && !piece.getColor().equals(color)){
                    if (piece.isValidMove(kingPosition, this) &&
                    (!(piece instanceof Pieces.Knight) && isPathClear(piece.getPosition(), kingPosition) || piece instanceof Pieces.Knight)) {
                    System.out.println(piece.getClass().getSimpleName() + " threatens the King!");
                    return true;
                }
                }
            }
        }
        return false;
    }


    //finding king position method 

    public Position findKingPosition(String color){
        for (int row =0; row < 8; row++){
            for(int col =0; col < 8; col++){
                ChessPiece piece = board[row][col];
                if(piece instanceof King && piece.getColor().equals(color)){
                    return new Position(row, col);
                }
            }

        }
        return null;
    }

    


    //creating method for cheking checkmate, by returning boolean 

    public boolean isCheckmate(String color) {
        // Check if all possible moves of the player still leave their king in check
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    List<Position> validMoves = getValidMovesForPiece(new Position(row, col));
                    for (Position move : validMoves) {
                        if (!wouldKingBeInCheckAfterMove(new Position(row, col), move)) {
                            return false; // There's at least one move to get out of check
                        }
                    }
                }
            }
        }
        return true;
    }
    


    private boolean wouldKingBeInCheckAfterMove(Position from, Position to){
        if (from == null || to == null) {
            return false; // Invalid positions
        }
        ChessPiece movingPiece = board[from.getRow()][from.getCol()];
        ChessPiece originalPiece = board[to.getRow()][to.getCol()];
        
        //temporarily make the move 
        board[to.getRow()][to.getCol()] = movingPiece;
        board[from.getRow()][from.getCol()] = null;


        boolean inCheck = checkForCheck(movingPiece.getColor());

        //undo the move 
        board[from.getRow()][from.getCol()] = movingPiece;
        board[to.getRow()][to.getCol()] = originalPiece;

        return inCheck;
    }

    public List<Position> getValidMovesForPiece(Position position) {
    List<Position> validMoves = new ArrayList<>();
    ChessPiece piece = getPieceAt(position.getRow(), position.getCol());
    
    if (piece == null) {
        return validMoves; // No piece at this position
    }
    
    // Loop through all possible squares on the board
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Position targetPosition = new Position(row, col);
            if (piece.isValidMove(targetPosition, this) && isPathClear(position, targetPosition)) {
                validMoves.add(targetPosition);
            }
        }
    }
    return validMoves;
}

public ChessPiece[][] getBoard() {
    return board;
}

public String isKingCaptured() {
    boolean whiteKingPresent = false;
    boolean blackKingPresent = false;
    
    for (ChessPiece[] row : board) {
        for (ChessPiece piece : row) {
            if (piece instanceof King) {
                if (piece.getColor().equals("white")) {
                    whiteKingPresent = true;
                } else if (piece.getColor().equals("black")) {
                    blackKingPresent = true;
                }
            }
        }
    }

    if (!whiteKingPresent) {
        return "black";
    } else if (!blackKingPresent) {
        return "white";
    } else {
        return null; // No king has been captured
    }
}

public void promotePawn(Position position, String pieceType) {
    ChessPiece newPiece = null;
    String color = currentTurn.equals("white") ? "white" : "black";

    switch (pieceType.toLowerCase()) {
        case "queen":
            newPiece = new Queen(position, color);
            break;
        case "rook":
            newPiece = new Rook(position, color);
            break;
        case "bishop":
            newPiece = new Bishop(position, color);
            break;
        case "knight":
            newPiece = new Knight(position, color);
            break;
        default:
            System.out.println("Invalid promotion choice: " + pieceType);
            return; // Exit method if invalid promotion piece is given
    }

    board[position.getRow()][position.getCol()] = newPiece; // Replace pawn with new piece
}



public void reset() {
    board = new ChessPiece[8][8];
    currentTurn = "white";
    initializePieces();
}






   
}
