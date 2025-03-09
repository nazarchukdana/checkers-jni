# Checkers Game with Java JNI

## Project Objective
This project is a **exercise in JNI (Java Native Interface) and unit testing**. It involves implementing a **Checkers game** with a graphical user interface (GUI) in Java and game logic in C++.

## Features
- **Graphical User Interface (Java)**
  - Displays the checkers board and pieces.
  - Interactive movement of pieces using **mouse and keyboard**.
- **JNI Communication**
  - The board state and game logic are entirely managed in **C++**.
  - Java fetches the board state in real-time via **JNI methods**.
- **Game Logic (C++)**
  - Stores the board state and piece positions.
  - Implements movement rules and captures.
  - Enforces Checkers rules (valid moves, jumps, etc.).
- **Two-Player Gameplay**
  - Allows two players to play against each other on the same system.
- **JUnit 5 Unit Testing**
  - Verifies correct board initialization.
  - Checks valid movement of pieces.
  - Ensures game mechanics follow the rules.
  - Tests correct capturing of opponent's pieces.

## Contributors
- **Author:** Dana Nazarchuk
- Created as a school project for Polish Japanese Academy of Information Technologies

