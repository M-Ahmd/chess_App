# Android Chess Game

A simple two-player chess game built using **Java** and **Android Studio**. The game supports:

- A complete 8x8 interactive chessboard.
- Piece movement with rules for all standard chess pieces.
- Castling logic and king safety.
- Win detection via checkmate.
- Move history tracking.
- End-game dialog asking the player to restart or exit.

## 📱 Screenshots

*(Add screenshots of your app here once available)*

---

## 🔧 Features

- **Two-player support**: Local hot-seat game (both players use the same device).
- **Castling**: Full castling logic with rook and king conditions.
- **Checkmate detection**: Ends the game and declares the winner.
- **Move log**: A scrollable area to track all moves.
- **Restart game dialog**: Prompts the user to restart or close after checkmate.

---

## 🧠 How It Works

- The chessboard is rendered using a `GridLayout` with `ImageView` cells.
- Each piece is represented as a custom class (e.g., `King`, `Pawn`, `Rook`...).
- Clicking on a piece highlights it.
- Clicking a valid destination executes the move.
- The `MoveManager` handles game logic, turns, and rules.
- After checkmate, a `Dialog` appears asking the player to play again or quit.

---

## 🛠️ Technologies Used

- **Java**
- **Android SDK**
- **Android Studio**
- `GridLayout`, `ImageView`, `AlertDialog`

---

## 📂 Project Structure

com.example.myapplication/
├── MainActivity.java # Main UI & Game logic
├── MoveManager.java # Turn control & validation
├── Cell.java # Cell representation on the board
├── Piece.java (abstract) # Base class for all pieces
├── King.java / Queen.java / etc. # Piece logic
└── res/
└── drawable/ # Piece icons
└── layout/activity_main.xml

---

## ✅ TODO / Future Improvements

- Add player profile support.
- Save game history using SQLite or Room DB.
- Add timers for each player.
- Implement undo/redo.
- Support AI mode (play against computer).
- Add en passant and pawn promotion.
- Add animations and sound effects.

---

## 💡 Getting Started

1. Clone this repository.
2. Open in **Android Studio**.
3. Run on emulator or physical device.
4. Enjoy a game of chess!

---

## 🙌 Author

Developed by **Muhammad Ahmed**  
Chess enthusiast & Android developer 💙

---

## 📃 License

This project is open-source. Use it, improve it, and enjoy it!  
