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

    public String getName() {
        return name;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    private Board board;
    private Board opponentBoard;
    private List<Ship> ships;

    public List<Ship> getOpponentShips() {
        return opponentShips;
    }

    public void setOpponentShips(List<Ship> opponentShips) {
        this.opponentShips = opponentShips;
    }

    private List<Ship> opponentShips;
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

    public void attack(String sx, String sy) {
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





}

