package model;
// Sergio Sauceda, Taven Hathaway, Lugh Dunlap, Kevin Hernandez
// Chess Game - CSCI-3331-001
// 11/8/2024
// Sets up board and stage

import java.util.ArrayList;
import java.util.List;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

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
    public boolean movePiece(Position from, Position to) {
        ChessPiece piece = getPieceAt(from.getRow(), from.getCol());

        if (piece != null && piece.getColor().equals(currentTurn) && piece.isValidMove(to, this) && isPathClear(from, to)) {
            if (board[to.getRow()][to.getCol()] != null) {
                capturePiece(to); // Capture opponent piece if present
            }
            board[to.getRow()][to.getCol()] = piece; // Move piece to new position
            board[from.getRow()][from.getCol()] = null; // Clear old position
            piece.setPosition(to); // Update piece position
            toggleTurn(); // Switch turn to the other player
            return true;
        }
        return false;
    }

    // Checks if the path is clear for pieces that move along a path (Rook, Bishop, Queen)
    private boolean isPathClear(Position from, Position to) {
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
    private void toggleTurn() {
        currentTurn = currentTurn.equals("white") ? "black" : "white";
    }

    // Checks if the move is valid based on piece rules and board state
    public boolean isMoveValid(Position from, Position to) {
        ChessPiece piece = getPieceAt(from.getRow(), from.getCol());
        return piece != null && piece.isValidMove(to, this) && isPathClear(from, to);
    }

    // Getter for current turn
    public String getCurrentTurn() {
        return currentTurn;
    }

    public boolean checkForCheck(String color){
        Position kingPosition = findKingPosition(color);
        for(ChessPiece[] row : board){
            for(ChessPiece piece: row){
                if(piece !=null && !piece.getColor().equals(color)){
                    if(piece.isValidMove(kingPosition, this)){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    //finding king position method 

    private Position findKingPosition(String color){
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
        ChessPiece originalPiece = board[to.getRow()][to.getCol()];
        board[to.getRow()][to.getCol()] = board[from.getRow()][from.getCol()];
        board[from.getRow()][from.getCol()] = null;

        boolean inCheck = checkForCheck(board[to.getRow()][to.getCol()].getColor());
        board[from.getRow()][from.getCol()] = board[to.getRow()][to.getCol()];
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

   
}
