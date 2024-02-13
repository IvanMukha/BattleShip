package Models;


import Models.Utils.Converter;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private final String name;
    private Board board;
    private Board opponentBoard;
    private List<Ship> ships;
    private Converter converter;


    public Player(String name) {
        this.name = name;
        this.board = new Board();
        this.ships = new ArrayList<>();
        this.converter = new Converter();
        board.initializeBoard();
    }
    public void setOpponentBoard(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
    }
    public Board getBoard() {
        return board;
    }
    public List<Ship> getShips(){
        return ships;
    }
    public void autoPlaceShips() {
        Random random = new Random();

        for (ShipType shipType : ShipType.values()) {
            int shipSize = shipType.getSize();

            for (int i = 0; i < shipType.getCount(); i++) {
              //  String startX, startY;
                Orientation orientation = random.nextBoolean() ? Orientation.HORIZONTAL : Orientation.VERTICAL;

                // Генерация случайных координат
                int startXNumber = 1+random.nextInt(board.getSIZE()-1);
                int startYNumber =1+ random.nextInt(board.getSIZE()-1);

//                 Проверка возможности размещения корабля
                while (!checkShipPlacement(startXNumber, startYNumber, orientation, shipSize)) {
                    startXNumber =1+ random.nextInt(board.getSIZE()-1);
                    startYNumber =1+ random.nextInt(board.getSIZE()-1);
               }

                // Создание корабля и добавление на игровое поле
                Ship ship = new Ship(converter.convertNumberToLetter(startXNumber), String.valueOf(startYNumber), orientation, shipSize);
                // System.out.println("Данные по кораблям из атаки"+"Данные по кораблю: "+"startX: "+ship.getStartX()+" startY: "+ship.getStartY()+" orientation "+ship.getOrientation()+" shipsize "+ship.getSize());


                placeShipOnBoard(startXNumber,startYNumber,orientation,shipSize);
                board.markNearShips(board);

            }
        }
        board.printBoard();
    }
    public void placeShipsManual() {
        Scanner scanner = new Scanner(System.in);

        for (ShipType shipType : ShipType.values()) {
            int shipSize = shipType.getSize();
            System.out.println("Расставьте корабли длиной " + shipSize);

            for (int i = 0; i < shipType.getCount(); i++) {
                String startX, startY;

                // Проверка ввода координаты X
                do {
                    System.out.println("Введите координаты начальной точки X (от A до P):");
                    startX = scanner.next().toUpperCase();
                    if (!isValidXCoordinate(startX)) {
                        System.out.println("Некорректная координата Y. Повторите ввод.");
                    }
                } while (!isValidXCoordinate(startX));

                do {
                    System.out.println("Введите координаты начальной точки Y (от 1 до 16):");
                    startY = scanner.next();
                    if (!isValidYCoordinate(startY)) {
                        System.out.println("Некорректная координата Y. Повторите ввод.");
                    }
                } while (!isValidYCoordinate(startY));

                int startXNumber = converter.convertLetterToNumber(startX);
                int startYNumber = Integer.parseInt(startY);

                System.out.println("Введите ориентацию (H - горизонтальная, V - вертикальная):");
                String orientationInput = scanner.next();
                Orientation orientation;

                if (orientationInput.equalsIgnoreCase("H")) {
                    orientation = Orientation.HORIZONTAL;
                } else if (orientationInput.equalsIgnoreCase("V")) {
                    orientation = Orientation.VERTICAL;
                } else {
                    System.out.println("Некорректная ориентация. Повторите ввод.");
                    i--;
                    continue;
                }


                // Проверка на допустимые значения координат
                if (startXNumber < 1 || startYNumber < 1 || startXNumber > board.getSIZE() || startYNumber > board.getSIZE()) {
                    System.out.println("Некорректные координаты. Повторите ввод.");
                    i--;
                    continue;
                }

                // Проверка на перекрытие кораблей и на наличие соседних кораблей
                if (!checkShipPlacement(startXNumber, startYNumber, orientation, shipSize)) {
                    System.out.println("Некорректное расположение корабля. Повторите ввод.");
                    i--;
                    continue;
                }


                placeShipOnBoard(startXNumber,startYNumber,orientation,shipSize);
                  board.markNearShips(board);
                board.printBoard(); // Display the board after placing each ship
                System.out.println("Ship was placed"); // Display the ship that has been placed
            }
        }

    }
    public void placeShipOnBoard(int startXNumber,int startYNumber,Orientation orientation,int shipSize){
       String startX=converter.convertNumberToLetter(startXNumber);
       String startY=String.valueOf(startYNumber);
        Ship ship = new Ship(startX, startY, orientation, shipSize);
       //ф System.out.println("Данные по кораблю: "+"startX: "+startXNumber+" startY: "+startYNumber+" orientation "+orientation+" shipsize "+shipSize);
        ships.add(ship);

        if (orientation == Orientation.HORIZONTAL) {
            for (int j = startXNumber - 1; j < startXNumber + shipSize - 1; j++) {
                int x = j;
                int y = startYNumber - 1;
                board.updateCell(x, y, 'O');
            }
        } else {

            for (int j = startYNumber - 1; j < startYNumber + shipSize - 1; j++) {
                int x = startXNumber - 1;
                int y = j;
                board.updateCell(x, y, 'O');


            }
        }

    }

    public void attack(String sx, String sy,List<Ship> opponentShips) {
        int x=converter.convertLetterToNumber(sx);
        int y=Integer.parseInt(sy);
        System.out.println("sx: "+sx+"x: "+x);
        System.out.println("sy: "+sy+"y:"+y);
        if (opponentBoard == null) {
            System.out.println("Ошибка: поле соперника не установлено.");
            return;
        }
        // Проверяем, что координаты находятся в пределах игрового поля соперника
        if (x < 0 || x > opponentBoard.getSIZE() || y < 0 || y > opponentBoard.getSIZE()) {
            System.out.println("Некорректные координаты. Введите другие координаты.");
            return;
        }

        // Получаем символ из игрового поля соперника по указанным координатам
        char attackResult = opponentBoard.getCell(x-1, y-1);

        // Обрабатываем результат атаки в зависимости от содержимого клетки
        switch (attackResult) {
            case ' ':
                System.out.println("Мимо.");
                opponentBoard.updateCell(x-1, y-1, '.');
                break;
            case '*':
                System.out.println("Мимо.");
                opponentBoard.updateCell(x-1, y-1, '.');
                break;
            case 'O':
                   opponentBoard.updateCell(x-1, y-1, 'X'); // Отмечаем клетку как пораженную
                for (Ship ship : opponentShips) {
                    if (ship.containsPoint(x, y)) {
                        if (ship.isShipDestroyed(opponentBoard)) {
                           opponentBoard.surroundDestroyedShip(opponentBoard,ship);
                            System.out.println("Корабль убит!");
                        } else {
                            System.out.println("Ранил.");
                        }
                        break;

                    }

                }

                break;

            case 'X':
                System.out.println("Уже атаковано по этим координатам.");
                break;
            case '.':
                System.out.println("Уже атаковано по этим координатам.");
                break;
            default:
                System.out.println("Ошибка: неожиданный символ на игровом поле соперника.");
                break;
        }
    }


    private boolean isValidXCoordinate(String x) {
        return x.length() == 1 && x.charAt(0) >= 'A' && x.charAt(0) <= 'P';
    }
    private boolean isValidYCoordinate(String y) {
        try {
            int yNumber = Integer.parseInt(y);
            return yNumber >= 1 && yNumber <= 16;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean checkShipPlacement(int startXNumber, int startYNumber, Orientation orientation, int shipSize) {
       // System.out.println("START X: "+startXNumber+" START Y: "+startYNumber);
        // Проверка на пересечение с другими кораблями
        int boardSize = board.getSIZE(); // Размер игрового поля (пример)

        if (orientation == Orientation.HORIZONTAL) {
            if (startXNumber + shipSize-1 > boardSize) {
               // System.out.println("Корабль выходит за пределы игрового поля по горизонтали");
                return false;
            }
            for (int i = startXNumber-1; i < startXNumber + shipSize-1; i++) {
                    if (board.getCell(i, startYNumber-1) != ' ') {
                        return false;
                    }
            }
        } else if (orientation == Orientation.VERTICAL) {
            if (startYNumber + shipSize-1 > boardSize) {
              //  System.out.println("Корабль выходит за пределы игрового поля по вертикали");

                return false;
            }
            for (int j = startYNumber-1; j < startYNumber + shipSize-1; j++) {
               // System.out.println("J:"+j+" i: "+(startXNumber-1));

                if (board.getCell(startXNumber-1, j) != ' ') {

                    return false;
                }
            }
        }
        return true;
    }





}

