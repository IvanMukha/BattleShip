package Models;

import Models.Utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotPlayer {
    public BotPlayer(String name) {
        this.name = name;
        this.board = new Board();
        this.ships = new ArrayList<>();
        this.converter = new Converter();
        board.initializeBoard();
    }

    private final String name;
    private Board board;
    private Board opponentBoard;
    private List<Ship> ships;
    private Converter converter;
    public boolean isAttacked() {
        return isAttacked;
    }

    private boolean isAttacked;
private int lastX=-1;
private int lastY=-1;
    public void botAttack(int x,int y,Board opponentBoard, List<Ship> opponentShips) {

;        char attackResult = opponentBoard.getCell(y,x);
        if (attackResult == 'X' || attackResult == '.') {
            return;
        }
        switch (attackResult) {
            case ' ':
                System.out.println("Бот: мимо.");
                opponentBoard.updateCell(y , x , '.');
                break;
            case '*':
                System.out.println("Бот: мимо.");
                opponentBoard.updateCell(y , x, '.');

                break;
            case 'O':
                opponentBoard.updateCell(y, x, 'X'); // Отмечаем клетку как пораженную
                System.out.println("X and Y from BotAttack"+y+"  "+x);
               // isAttacked=true;
                for (Ship ship : opponentShips) {
                    if (ship.containsPoint(y, x)) {

                        if (ship.isShipDestroyed(opponentBoard)) {
                            opponentBoard.surroundDestroyedShip(opponentBoard, ship);
                            System.out.println("Бот: корабль убит!");

                        } else {
                            System.out.println("Бот: ранил.");
                            attackNeighbors(x,y,opponentBoard,opponentShips);

                        }
                        break;
                    }
                }
                break;
        }}

    private void attackNeighbors(int x, int y, Board opponentBoard, List<Ship> opponentShips) {
        // Первая известная клетка
        int firstX = x;
        int firstY = y;

        // Проверяем, есть ли вторая известная клетка
        if (lastX != -1 && lastY != -1) {
            // Если есть, то атакуем в направлении двух известных клеток
            attackInDirection(firstX, firstY, lastX, lastY, opponentBoard, opponentShips);
        } else {
            // Иначе атакуем во всех направлениях
            attackAllDirections(x, y, opponentBoard, opponentShips);
        }
    }

    private void attackInDirection(int firstX, int firstY, int lastX, int lastY, Board opponentBoard, List<Ship> opponentShips) {
        int dx = lastX - firstX;
        int dy = lastY - firstY;

        if (dx == 0) { // Если направление вертикальное
            int direction = dy > 0 ? 1 : -1; // Определяем направление атаки
            int newY = lastY + direction;
            if (isValidCell(firstX, newY, opponentBoard)) {
                botAttack(firstX, newY, opponentBoard, opponentShips);
            }
        } else { // Если направление горизонтальное
            int direction = dx > 0 ? 1 : -1; // Определяем направление атаки
            int newX = lastX + direction;
            if (isValidCell(newX, firstY, opponentBoard)) {
                botAttack(newX, firstY, opponentBoard, opponentShips);
            } else {
                // Если клетка недоступна, изменяем направление на противоположное
                attackInDirection(lastX, lastY, firstX, firstY, opponentBoard, opponentShips);
            }
        }
    }

    private void attackAllDirections(int x, int y, Board opponentBoard, List<Ship> opponentShips) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // Направления: {влево, вправо, вверх, вниз}

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int newX = x + dx;
            int newY = y + dy;
            if (isValidCell(newX, newY, opponentBoard)) {
                botAttack(newX, newY, opponentBoard, opponentShips);
            }
        }
    }

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
        if(x >= 0 && x < 16 && y >= 0 && y < 16&&opponentBoard.getCell(x,y)!='.'&&opponentBoard.getCell(x,y)!='X')
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







