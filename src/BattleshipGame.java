import Models.Board;
import Models.BotPlayer;
import Models.Player;
import Models.Utils.Converter;

import java.util.*;

public class BattleshipGame {
    public static void main(String[] args) {
        Converter converter=new Converter();
    BotPlayer botPlayer=new BotPlayer("bot");
    Player player=new Player("Player1");
    botPlayer.setBoard(new Board());
    player.setBoard(new Board());
    player.getBoard().autoPlaceShips(player.getBoard(),player.getShips());
    botPlayer.getBoard().autoPlaceShips(botPlayer.getBoard(),botPlayer.getShips());
        botPlayer.setOpponentBoard(player.getBoard());
        botPlayer.setOpponentShips(player.getShips());
        player.setOpponentBoard(botPlayer.getBoard());
        player.setOpponentShips(botPlayer.getShips());
     printGame(player.getBoard(),botPlayer.getBoard());
     Scanner in=new Scanner(System.in);
     String x;
     String y;


             //  Random random=new Random();

while (true){
                     int j = in.nextInt();
                     int i=in.nextInt();


                     botPlayer.botAttack(j, i);

                     printGame(player.getBoard(), botPlayer.getBoard());
                }
             }








//        String x;
//        String y;
//        while(!stop.equals("Стоп")){
//           System.out.println("X:");
//            x=in.nextLine();
//            System.out.println("Y:");
//            y=in.nextLine();
//            player.attack(x,y,player2.getShips());
//            printGame(player.getBoard(),player2.getBoard());
//
//            }
//
//       printGame(player.getBoard(),player2.getBoard());
    //            allShipsDestroyed(player.getBoard(),player2.getBoard());
//            }






public static void allShipsDestroyed(Board playerBoard,Board opponentBoard){
        int amountOfCellShips=0;
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
              if(opponentBoard.getCell(i,j)=='O');
              amountOfCellShips++;
            }
            if(amountOfCellShips==0){
                System.out.println("Все корабли были уничтожены");

            }
        }
}
    public static void printGame(Board playerBoard, Board opponentBoard) {
        int boardSize = playerBoard.getSIZE();

        // Выводим заголовок для своего поля
        System.out.print("    Ваше игровое поле:                    ");
        System.out.println("                     Поле противника:");;
        // Выводим буквы от A до P с отступом
        System.out.print("   ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(" " + (char)('A' + i) + " ");
        }
        System.out.print("             ");

        // Выводим буквы сверху поля противника с отступом
        System.out.print("   ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(" " + (char)('A' + i) + " ");
        }
        System.out.println();

        // Выводим строки и клетки своего поля и поля противника
        for (int i = 0; i < boardSize; i++) {
            // Выводим номер строки слева
            int rowNumber = i + 1;
            System.out.print((rowNumber < 10 ? " " : "") + rowNumber + " ");

            // Выводим строку своего поля
            for (int j = 0; j < boardSize; j++) {
                if(playerBoard.getCell(j,i)=='O'){
                    System.out.print("[O]");
                }
                else if (playerBoard.getCell(j, i) == '.') {
                    System.out.print("[.]"); // Если клетка содержит корабль или 'X', выводим '[X]'
                } else if(playerBoard.getCell(j,i)==' '||playerBoard.getCell(j,i)=='*'){
                    System.out.print("[ ]"); // В остальных случаях выводим пробел
                }else if(playerBoard.getCell(j,i)=='X'){
                    System.out.print("[X]");
                }
                //System.out.print("[" + playerBoard.getCell(j, i) + "]");
            }

            // Отступ между полями
            System.out.print("             ");

            // Выводим строку поля противника
            System.out.print((rowNumber < 10 ? " " : "") + rowNumber + " ");
            for (int j = 0; j < boardSize; j++) {
                if(opponentBoard.getCell(j,i)=='O'){
                    System.out.print("[ ]");
                }
                else if (opponentBoard.getCell(j, i) == '.') {
                    System.out.print("[.]"); // Если клетка содержит корабль или 'X', выводим '[X]'
                } else if(opponentBoard.getCell(j,i)==' '||opponentBoard.getCell(j,i)=='*'){
                    System.out.print("[ ]"); // В остальных случаях выводим пробел
                }else if(opponentBoard.getCell(j,i)=='X'){
                    System.out.print("[X]");
                }
            }

            // Переходим на следующую строку
            System.out.println();
        }
    }


}




/*
6КЛX1 5КЛX2 4КЛX3 3КЛX4 2КЛX5 1КЛX6
B1 VERTICAL OR HORIZONTAL KOL-VO KLETOK
1-16A 1-16B
16*16=256 UNIQUE VALUE
ARRAY [][] 16X16
13I=
HASHMAP=1P=0 -korablya net
HASHMAP=2K=1 korabl est





    A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P
1
2   0   1   1   1   1   0   1   0
3   0   0   0   0   0   0   0   0
4
5
6
7
8
9
10
11
12
13
14
15
16
 */