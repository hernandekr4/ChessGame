# **Chess Game**

**Chess Game** This is an interactive chess application built with **JavaFX**. It features a classic 8x8 chessboard, image-based chess pieces, and fully validated piece movement according to official chess rules. Players can click to select and move pieces, with support for all chess pieces (pawns, rooks, knights, bishops, queens, and kings). The game enforces turn-based play, handles valid/invalid moves, and dynamically updates the visual board after every move.

Its officially recognized as a strategy board game where two players take turns moving pieces on a checkered board to checkmate their opponent's king. 

---

## **What the Code Does**

This **Chess Game** code implements a **fully interactive chess game** using **JavaFX**. Here's a breakdown of **what the code does**:

---

### **1. Game Setup**
- The chessboard is created as an 8x8 grid with alternating brown and beige tiles.
- Chess pieces (pawns, rooks, knights, bishops, queens, and kings) are initialized in their starting positions using **image files** located in `src/resources/images/`.

---

### **2. Game Structure**
This project follows a **Model-View-Controller (MVC) Design** pattern:
- **Model**: Handles the game logic (piece movement, turn-based logic, board state).
- **View**: Renders the chessboard and pieces using JavaFX.
- **Controller**: Handles user input (clicking pieces and destinations) and coordinates updates between the Model and View.

---

### **3. Piece Movement**
- Players click on a chess piece to select it, then click on a square to move it.
- Each piece (pawn, rook, knight, bishop, queen, king) has its own movement logic according to chess rules.
- **Path-checking logic** ensures that rooks, bishops, and queens cannot "jump" over other pieces.
- **Knights** can "jump" to valid L-shaped positions, and **pawns** have special movement rules for first moves and diagonal captures.

---

### **4. Turn System**
- The game enforces player turns, alternating between **white** and **black**.
- Each player can only move their own pieces, and the system prevents players from moving the opponent's pieces.

---

### **5. Visual Updates**
- After each move, the **board is refreshed**, and the images are updated to reflect the current board state.
- Pieces that are captured are removed from the board.

---

### **6. Error Handling**
- If a player tries to move a piece to an invalid square, an error message is printed in the console.

- The game handles edge cases like:
  - Attempting to move off the board.
  - Selecting empty squares.
  - Attempting to move a piece that does not belong to the current player.
  - Blocking paths for pieces like rooks, bishops, and queens.

---

### **7. Classes and Files**
This project consists of several files, each with a specific purpose.

| **File**             | **Description**                                       |
|---------------------|-------------------------------------------------------|
| **App.java**         | Entry point for the game. Initializes View, Model, and Controller. |
| **Controller.java**  | Handles user input and controls the flow of the game.  |
| **View.java**        | Displays the chessboard and pieces using JavaFX.       |
| **Model.java**       | Manages board state, tracks turns, and validates moves. |
| **ChessPiece.java**  | Abstract base class for chess pieces (Rook, Knight, etc.). |
| **Knight.java**      | Movement logic specific to knights (L-shaped moves).   |
| **Bishop.java**      | Movement logic specific to bishops (diagonal moves).   |
| **Rook.java**        | Movement logic specific to rooks (horizontal/vertical moves). |
| **Queen.java**       | Combines movement logic of rooks and bishops.          |
| **King.java**        | Handles king movement (one square in any direction).   |
| **Pawn.java**        | Handles pawn movement (forward, captures diagonally, double move from start). |
| **Position.java**    | Represents the (row, column) position of each piece.  |

---

With this code, users can play chess following all the basic rules, and the system ensures all moves are valid. The game can be further expanded to include check, checkmate, and other chess-specific rules. 

## Image Credits
The chess piece images used in this project were sourced from [PNGEgg](https://www.pngegg.com/en/png-pdjoy).

If you are the creator of these images and require additional attribution or licensing clarification, please contact us.

---

## **Table of Contents**
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [How to Play](#how-to-play)
- [Design Choices](#design-choices)
- [Known Bugs](#known-bugs)
- [Credits](#credits)

---

## **Features**
- **Interactive Chessboard**: 8x8 board with classic chess layout.
- **Chess Pieces**: Visual images for pawns, rooks, knights, bishops, queens, and kings.
- **Move Validation**: Movement logic follows official chess rules.
- **Click-to-Move System**: Click a piece, then click the target square.
- **Dynamic Board Refresh**: Board updates after every move.
- **Turn System**: Tracks the turn of each player.
- **Path-Checking Logic**: Ensures rooks, bishops, and queens cannot jump over pieces.
- **Knight Movement**: Knights "jump" correctly and are not blocked by path logic.

---

## **Technologies Used**
- **Java 22**: Core programming language.
- **JavaFX 22**: Used to create the graphical user interface (GUI).
- **Git/GitHub**: Version control and collaboration.

---
### **Prerequisites**S
- **Java Development Kit (JDK 22)**
- **JavaFX SDK 22**
- **Git** (to clone the repository)
- **VS Code** (used)


