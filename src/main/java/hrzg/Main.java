package hrzg;

import java.util.Scanner;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

class Battlefield {
    final int SIZE = 10;
    final int AIRCRAFT_SIZE = 5;
    final int BATTLESHIP_SIZE = 4;
    final int SUBMARINE_SIZE = 3;
    final int CRUISER_SIZE = 3;
    final int DESTROYER_SIZE = 2;
    final char EMPTY = '~';
    final char MISSED = 'M';
    final char HIT = 'X';
    final char SHIP = 'O';
    char[][] filed = new char[SIZE][SIZE];
    Ship[] ships;
    Scanner scanner = new Scanner (System.in);


    public Battlefield() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                filed[i][j] = EMPTY;
            }
        }
    }


    public void initfield() {
        System.out.println (this.toString ());
        ships = new Ship[5];
        ships[0] = new Ship (AIRCRAFT_SIZE, "Aircraft Carrier");
        ships[1] = new Ship (BATTLESHIP_SIZE, "Battleship");
        ships[2] = new Ship (SUBMARINE_SIZE, "Submarine");
        ships[3] = new Ship (CRUISER_SIZE, "Cruiser");
        ships[4] = new Ship (DESTROYER_SIZE, "Destroyer");
        for (Ship ship : ships) {
            System.out.printf ("Enter the coordinates of the %s (%d cells):\n", ship.getName (), ship.getSize ());
            while (true) {
                String[] coordinates = scanner.nextLine ().split (" ");
                int rowBegin = coordinates[0].charAt (0) - 65;
                int columnBegin = Integer.parseInt (coordinates[0].substring (1));
                int rowEnd = coordinates[1].charAt (0) - 65;
                int columnEnd = Integer.parseInt (coordinates[1].substring (1));
                if (rowBegin > rowEnd) {
                    int tmp = rowEnd;
                    rowEnd = rowBegin;
                    rowBegin = tmp;
                }
                if (columnBegin > columnEnd) {
                    int tmp = columnEnd;
                    columnEnd = columnBegin;
                    columnBegin = tmp;
                }
                if (ship.setCoordinates (rowBegin, columnBegin, rowEnd, columnEnd)) {
                    if (putShipOnField (rowBegin, columnBegin, rowEnd, columnEnd, ship)) {
                        System.out.println (this.toString ());
                        break;
                    }
                }
            }
        }
    }


    public boolean putShipOnField(int _rowBegin, int _columnBegin, int _rowEnd, int _columnEnd, Ship _ship) {
        for (Ship ship : ships) {
            if (ship != _ship && ship.isPlaced ()) {
                for (int i = _rowBegin - 1; i <= _rowEnd + 1; i++) {
                    for (int j = _columnBegin - 1; j <= _columnEnd + 1; j++) {
                        if ((i == ship.getRowBegin () && j == ship.getColumnBegin ()) ||
                                (i == ship.getRowEnd () && j == ship.getColumnEnd ())) {
                            System.out.println ("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            }
        }

        if (_rowBegin == _rowEnd) {
            for (int i = _columnBegin; i <= _columnEnd; i++) {
                this.filed[_rowBegin][i - 1] = _ship.getCells ()[i - _columnBegin];
            }
        } else {
            for (int i = _rowBegin; i <= _rowEnd; i++) {
                this.filed[i][_columnBegin - 1] = _ship.getCells ()[i - _rowBegin];
            }
        }
        return true;
    }


    public boolean makeShot() {
        String shotCell = scanner.nextLine ();
        int shotRow = shotCell.charAt (0) - 65;
        int shotColumn = Integer.parseInt (shotCell.substring (1));
        if (shotRow < 0 || shotRow > 9 || shotColumn < 1 || shotColumn > 10) {
            System.out.println ("Error! You entered the wrong coordinates! Try again:");
        } else {

            boolean isHitOnShip = false;
            boolean isSankAShip = false;
            boolean isEndGame = true;

            for (Ship ship : ships) {
                if (shotRow == ship.rowBegin && shotRow == ship.rowEnd) {
                    if (shotColumn >= ship.columnBegin && shotColumn <= ship.columnEnd) {
                        isHitOnShip = true;
                        isSankAShip = ship.isFinalHit (shotColumn - ship.columnBegin, HIT);
                        break;
                    }
                } else if (shotColumn == ship.columnBegin && shotColumn == ship.columnEnd) {
                    if ((shotRow >= ship.rowBegin && shotRow <= ship.rowEnd)) {
                        isHitOnShip = true;
                        isSankAShip = ship.isFinalHit (shotRow - ship.rowBegin, HIT);
                        break;
                    }
                }
            }

            if (isHitOnShip && !isSankAShip) {
                System.out.println ("You hit a ship!");
            } else if (isSankAShip) {
                for (Ship ship : ships) {
                    if (!ship.isDead) {
                        System.out.println ("You sank a ship! Specify a new target:");
                        isEndGame = false;
                        break;
                    }
                }
                if (isEndGame) {
                    System.out.println ("You sank the last ship. You won. Congratulations!");
                    return true;
                }

            } else {
                this.filed[shotRow][shotColumn - 1] = MISSED;
                printBattlefield (true);
                System.out.println ("You missed!");
            }
        }
        return false;
    }


    public void printBattlefield(boolean _fogOfWar) {
        for (Ship ship : ships) {
            if (ship.rowBegin == ship.rowEnd) {
                for (int i = ship.columnBegin; i <= ship.columnEnd; i++) {
                    if (_fogOfWar) {
                        this.filed[ship.rowBegin][i - 1] = ship.getCells ()[i - ship.columnBegin] == SHIP ? EMPTY : ship.getCells ()[i - ship.columnBegin];
                    } else {
                        this.filed[ship.rowBegin][i - 1] = ship.getCells ()[i - ship.columnBegin];
                    }
                }
            } else {
                for (int i = ship.rowBegin; i <= ship.rowEnd; i++) {
                    if (_fogOfWar) {
                        this.filed[i][ship.columnBegin - 1] = ship.getCells ()[i - ship.rowBegin] == SHIP ? EMPTY : ship.getCells ()[i - ship.rowBegin];
                    } else {
                        this.filed[i][ship.columnBegin - 1] = ship.getCells ()[i - ship.rowBegin];
                    }
                }
            }
        }
        System.out.println (this.toString ());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder ("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < SIZE; i++) {
            result.append (Character.toChars (65 + i));
            for (int j = 0; j < SIZE; j++) {
                result.append (" ").append (filed[i][j]);
            }
            result.append ("\n");
        }
        return String.valueOf (result);
    }
}

class Ship {
    final char SHIP_SYMBOL = 'O';
    int size;
    String name;
    char[] cells;
    boolean isPlaced = false;
    boolean isDead = false;
    int rowBegin;
    int rowEnd;
    int columnBegin;
    int columnEnd;


    public Ship(int _size, String _name) {
        this.size = _size;
        this.name = _name;
        this.cells = new char[_size];
        Arrays.fill(this.cells, SHIP_SYMBOL);
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }


    public boolean setCoordinates(int _rowBegin, int _columnBegin, int _rowEnd, int _columnEnd) {
        if(_rowBegin == _rowEnd || _columnBegin == _columnEnd) {
            if(_rowEnd - _rowBegin != this.size - 1 && _columnEnd - _columnBegin != this.size - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", this.name);
                return false;
            }
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        this.rowBegin = _rowBegin;
        this.rowEnd = _rowEnd;
        this.columnBegin = _columnBegin;
        this.columnEnd = _columnEnd;
        this.isPlaced = true;

        return true;
    }


    public boolean isFinalHit(int _index, char _HIT) {
        this.cells[_index] = _HIT;
        for (char each : cells) {
            if (each != _HIT) {
                return false;
            }
        }
        this.isDead = true;
        return true;
    }

    public char[] getCells() {
        return cells;
    }

    public int getRowBegin() {
        return rowBegin;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getColumnBegin() {
        return columnBegin;
    }

    public int getColumnEnd() {
        return columnEnd;
    }

    public boolean isPlaced(){
        return isPlaced;
    }
}

class Player {
    Battlefield battlefield;
    String name;

    public Player(String _name) {
        this.battlefield = new Battlefield();
        this.name = _name;
    }
}

class Game {

    public void start() {

        boolean isGameOver = false;
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        System.out.println("Player 1, place your ships on the game field");
        player1.battlefield.initfield();
        changeTurn();

        System.out.println("Player 2, place your ships on the game field");
        player2.battlefield.initfield();
        changeTurn();

        while (!isGameOver) {
            player2.battlefield.printBattlefield(true);
            System.out.println("---------------------");
            player1.battlefield.printBattlefield(false);
            System.out.println("Player 1, it's your turn:");
            isGameOver = player2.battlefield.makeShot();
            if (isGameOver) {
                break;
            }
            changeTurn();
            player1.battlefield.printBattlefield(true);
            System.out.println("---------------------");
            player2.battlefield.printBattlefield(false);
            System.out.println("Player 2, it's your turn:");
            isGameOver = player1.battlefield.makeShot();
            if (isGameOver) {
                break;
            }
            changeTurn();
        }
    }

    public void changeTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }
}