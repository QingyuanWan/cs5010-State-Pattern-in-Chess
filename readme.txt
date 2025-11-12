cs5010-State-Pattern-in-Chess/
└─ src/
└─ main/
├─ Board.java
├─ ChessDriver.java
├─ ChessGame.java
├─ Node.java
├─ pieces/
│ ├─ King.java
│ ├─ Logic.java
│ ├─ Pawn.java
│ ├─ Piece.java
│ └─ Rook.java
└─ states/
├─ CheckState.java
├─ CheckmateState.java
├─ GameOverState.java
├─ GameStartState.java
├─ NormalPlayBlackState.java
├─ NormalPlayState.java
├─ NormalPlayWhiteState.java
└─ State.java


Open terminal and run:
mkdir bin
$src = Get-ChildItem -Recurse src\main -Filter *.java
javac -d bin $src; java -cp bin ChessDriver
