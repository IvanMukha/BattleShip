package Models;

import Models.Utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BotPlayer {
    public BotPlayer(String name) {
        this.name = name;
        this.board = new Board();
        this.ships = new ArrayList<>();
        this.converter = new Converter();
        board.initializeBoard();
    }
    private int firstXAttacked=-1;
    private int firstYAttacked=-1;
    private final String name;

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public void setOpponentBoard(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    private Board board;
    private Board opponentBoard;
    private List<Ship> ships;
    private Converter converter;

    public List<Ship> getOpponentShips() {
        return opponentShips;
    }

    public void setOpponentShips(List<Ship> opponentShips) {
        this.opponentShips = opponentShips;
    }

    private List<Ship>opponentShips;
    public boolean isAttacked() {
        return isAttacked;
    }

    private boolean isAttacked;

    public void botAttack(int x,int y) {

        opponentBoard.printBoard();
        System.out.println("From Bot Attack X:"+x+" Y: "+y);
        char attackResult = opponentBoard.getCell(x, y);
        if (attackResult == 'X' || attackResult == '.') {
            return;
        }
        switch (attackResult) {
            case ' ':
                System.out.println("Мимо.");
                opponentBoard.updateCell(x , y , '.');
                break;
            case '*':
                System.out.println("Мимо.");
                opponentBoard.updateCell(x, y, '.');
                break;
            case 'O':

                opponentBoard.updateCell(x , y , 'X'); // Отмечаем клетку как пораженную

                for (Ship ship : opponentShips) {

                    if (ship.containsPoint(x+1, y+1)) {
                        if (ship.isShipDestroyed(opponentBoard)) {

                            opponentBoard.surroundDestroyedShip(opponentBoard, ship);
                            System.out.println("Корабль убит!");
                        } else {
                            System.out.println("Ранил.");
                            attackNeighbors(x, y);

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

    private void attackNeighbors(int x, int y) {
//        if(firstXAttacked!=-1&&firstYAttacked!=-1){
//            x=firstXAttacked;
//            y=firstYAttacked;
//        }
        // Проверяем соседние клетки на наличие кораблей (X)
        boolean hasLeft = hasShipNeighbor(x, y, -1, 0);
        boolean hasRight = hasShipNeighbor(x, y, 1, 0);
        boolean hasUp = hasShipNeighbor(x, y, 0, -1);
        boolean hasDown = hasShipNeighbor(x, y, 0, 1);

        // Проверяем, что все соседние клетки уже атакованы или находятся за пределами поля
          if (!(hasLeft || hasRight || hasUp || hasDown)) {
        //  firstXAttacked=x;
          //  firstYAttacked=y;
            // Если нет соседних клеток с кораблями или все они уже атакованы, атакуем рандомно
            attackAllDirections(x,y);
        } else {
            // Атакуем в соответствии с описанной логикой
            if (hasUp) {
                attackInDirection(x, y, 0, 1);
            } else if (hasDown) {
                attackInDirection(x, y, 0, -1);
            } else if (hasLeft) {
                attackInDirection(x, y, 1, 0);
            } else if (hasRight) {
                attackInDirection(x, y, -1, 0);
            }
        }
    }



    private boolean hasShipNeighbor(int x, int y, int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;
        return isValidCell(newX, newY, opponentBoard) && opponentBoard.getCell(newX, newY) == 'X';
    }

//ошибка где-то снизу вроде бы.
    private void attackInDirection(int firstX, int firstY, int dx, int dy) {
        int newX=firstX+dx;
        int newY=firstY+dy;
        if(isValidCell(newX,newY,opponentBoard)){
            botAttack(newX,newY);
            System.out.println("bot Attack In DIrection"+" x: "+newX+"y: "+newY);
        }



//        while (dx != 0 || dy != 0) {
//            if (dx == 0) { // Если направление вертикальное
//                int newY = dx + firstY;
//                if (isValidCell(firstX, newY, opponentBoard)) {
//                    System.out.println("Attack vertical Direction" + "X: " + firstX + "Y: " + newY);
//                    botAttack(firstX, newY);
//                }
//
//            } else { // Если направление горизонтальное
//                int direction = dx > 0 ? 1 : -1; // Определяем направление атаки
//                int newX = dx + direction;
//                if (isValidCell(newX, firstY, opponentBoard)) {
//                    System.out.println("Attack Horizontal Direction" + "X: " + newX + "Y: " + firstY);
//                    botAttack(newX, firstY);
//                }
//
//            }

            // Проверяем, если мы дошли до конца, но не нашли пустую клетку, то запоминаем первую точку атаки
//            if (dx == 0 && dy == 0) {
//                if (!isValidCell(firstX, firstY, opponentBoard)) {
//                    startX = firstX;
//                    startY = firstY;
//                }
//            }
        }

//        // Если мы дошли до конца и не нашли пустую клетку, атакуем в другом направлении от первой точки
//        if (dx == 0 && dy == 0) {
//            attackFromStartingPoint(startX, startY);
//        }
//    private void attackInDirection(int firstX, int firstY, int lastX, int lastY) {
//        int dx = lastX - firstX;
//        int dy = lastY - firstY;
//
//        if (dx == 0) { // Если направление вертикальное
//            int direction = dy > 0 ? 1 : -1; // Определяем направление атаки
//            int newY = lastY + direction;
//            if (isValidCell(firstX, newY, opponentBoard)) {
//                System.out.println("Attack vertical Direction"+"X: "+firstX+"Y: "+newY);
//                botAttack(firstX, newY);
//            }
//        } else { // Если направление горизонтальное
//            int direction = dx > 0 ? 1 : -1; // Определяем направление атаки
//            int newX = lastX + direction;
//            if (isValidCell(newX, firstY, opponentBoard)) {
//                System.out.println("Attack Horizontal Direction"+"X: "+newX+"Y: "+firstY);
//                botAttack(newX, firstY);
//            } else {
//                // Если клетка недоступна, изменяем направление на противоположное
//                attackInDirection(lastX, lastY, firstX, firstY);
//            }
//        }
//    }

    private void attackAllDirections(int x, int y) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // Направления: {влево, вправо, вверх, вниз}

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int newX = x + dx;
            int newY = y + dy;
            if (isValidCell(newX, newY, opponentBoard)) {
                System.out.println("Attack ALL Direction"+"X: "+newX+"Y: "+newY);
                botAttack(newX, newY);

            }
        }
    }

//    private void attackNeighbors(int x, int y) {
//        // Первая известная клетка
//        int firstX = x;
//        int firstY = y;
//
//        // Проверяем, есть ли вторая известная клетка
//        if (lastX != -1 && lastY != -1) {
//            // Если есть, то атакуем в направлении двух известных клеток
//            attackInDirection(firstX, firstY, lastX, lastY);
//        } else {
//            // Иначе атакуем во всех направлениях
//            attackAllDirections(x, y);
//        }
//    }

//    private void attackNeighbors1(int x, int y, Board opponentBoard,List<Ship>opponentShips) {
//        int newX;
//        int newY;
//        boolean isHit=false;
//        int[] dx = {-1, 1, 0, 0};
//        int[] dy = {0, 0, -1, 1};
//
//        for (int i = 0; i < 4; i++) {
//            if (lastx != -1) {
//                newX = lastx + dx[i];
//            } else {
//                newX = x + dx[i];
//            }
//            if (lasty != -1) {
//                newY = lasty + dy[i];
//            } else {
//                newY = y + dy[i];
//            }
//
//            // Проверяем, находится ли новая клетка в пределах поля и не была ли атакована ранее
//            if (isValidCell(newX, newY) && (opponentBoard.getCell(newX, newY) == '*' || opponentBoard.getCell(newX, newY) == 'O')) {
//                if (opponentBoard.getCell(newX, newY) == 'O') {
//                    isHit=true;
//                    System.out.println("ПОПАДАНИЕ ВОЗЛЕ КЛЕТКИ "+newX+"     "+newY);
//                    opponentBoard.updateCell(newX, newY, 'X');
//
//                    for (Ship ship : opponentShips) {
//                        if (ship.containsPoint(x, y)) {
//                            if (ship.isShipDestroyed(opponentBoard)) {
//                                opponentBoard.surroundDestroyedShip(opponentBoard, ship);
//                                System.out.println("Бот: корабль убит!");
//                            } else {
//                                System.out.println("Бот: ранил.");
//                            }
//                        }
//                    }
//                    lastx = newX;
//                    lasty = newY;
//                } else if (opponentBoard.getCell(newX, newY) == '*') {
//                    System.out.println("НЕПАДАНИЕ ВОЗЛЕ КЛЛЕТКИ"+newX+"  "+newY);
//                    opponentBoard.updateCell(newX, newY, '.');
//
//                    isHit=false;
//                }
//
//            }
//        }
//
//          if(isHit)
//              attackNeighbors(lastx, lasty,opponentBoard,opponentShips);
//
//        }


        private boolean isValidCell(int x, int y,Board opponentBoard) {
            if(x >= 0 && x < 16 && y >= 0 && y < 16&&opponentBoard.getCell(x,y)!='.')
                return true;
            else return false;


        /* int y=0;

        for(int i=5;i>=0;i--){
            int j=y;
                board.updateCell(i,j,'X');
                y++;
        }
        y=0;
        for(int i=11;i>=0;i--){
            int j=y;
            board.updateCell(i,j,'X');
            y++;
        }
        y=2;
        for(int i=15;i>=2;i--){
            int j=y;
            board.updateCell(i,j,'X');
            y++;
        }
        y=9;
        for(int i=15;i>=10;i--){
            int j=y;
            board.updateCell(i,j,'X');
            y++;
        }
        }*/
//        int size = board.getSIZE();
//        for(int i=5;i<=0;i++){
//            for(int j=0;j<board.getSIZE();j++){
//            if(board.getCell(i,j)=='O'){
//            board.updateCell(i,j,'X');}
//            if(board.getCell(i,j)=='*'){
//                board.updateCell(i,j,'.');
//
//            }
//
//        }
//   }
        }}







    // Возвращаем null, если не удалось найти корабль







