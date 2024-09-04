# Battleship Game

This is a simple **Battleship** game implemented in Java. Two players place their ships on a 10x10 battlefield and take turns trying to sink each other's fleet. The game ends when one player successfully sinks all the ships of their opponent.

## Features

- **Two Player Gameplay:** Players alternate turns to place their ships and take shots at each other's battlefield.
- **Dynamic Ship Placement:** Players must strategically place their ships on the grid while following the game rules (ships cannot overlap or be adjacent).
- **Fog of War:** The game hides opponent ships and displays only missed shots and hits during turns.
- **Victory Condition:** The game ends when one player sinks all of the opponent's ships.

## Game Rules

1. **Battlefield Size:** The battlefield is a 10x10 grid.
2. **Ship Types and Sizes:**
   - Aircraft Carrier: 5 cells
   - Battleship: 4 cells
   - Submarine: 3 cells
   - Cruiser: 3 cells
   - Destroyer: 2 cells
3. **Game Flow:**
   - Both players place their ships on their battlefield.
   - Players take turns to guess where the opponent’s ships are by selecting a grid location to "fire" upon.
   - The game displays "hit" or "miss" depending on whether the shot hits a ship.
   - If a ship is completely hit (all cells occupied by the ship are hit), the ship sinks.
   - The game ends when all ships of one player are sunk.

## How to Play

1. **Setup:**
   - Clone the repository:
     ```bash
     git clone https://github.com/DrRaZar/battleship.git
     ```
   - Open the project in your favorite IDE.
   - Compile and run the `Main` class to start the game.

2. **Place Your Ships:**
   - Players are prompted to place their ships by entering coordinates.
   - Ships must be placed either vertically or horizontally (no diagonal placement).
   - Ships cannot be placed adjacent to one another.

3. **Take Turns:**
   - After ship placement, players take turns firing shots at the opponent’s battlefield.
   - Enter the coordinates of the shot, and the game will tell if it was a hit or a miss.
   - The first player to sink all the opponent’s ships wins the game.

4. **Game End:**
   - The game will announce the winner after all the ships of one player are sunk.

## Example

### Ship Placement

```
Enter the coordinates of the Aircraft Carrier (5 cells):
> A1 A5

Enter the coordinates of the Battleship (4 cells):
> B1 B4

...
```

### Shooting

```
Player 1, it's your turn:
Enter shot coordinates:
> D5
You hit a ship!

Player 2, it's your turn:
Enter shot coordinates:
> A2
You missed!
```

### Victory

```
You sank the last ship. You won. Congratulations!
```

## Classes

- **Main:** The entry point for the game.
- **Battlefield:** Represents the player's battlefield where ships are placed and attacks are recorded.
- **Ship:** Defines the attributes and behaviors of a ship, such as its size, name, and placement.
- **Player:** Holds player-specific information, including their battlefield.
- **Game:** Manages the game logic, including turns and game-over conditions.
