package Models;

import java.util.Scanner;

public class Game {
    public void startgame(){
        Scanner in=new Scanner(System.in);
        System.out.println("Введите имя игрока");
        Player player1=new Player(in.nextLine());
        System.out.println("1-Одиночная игра");
        System.out.println("2-Игра с напарником");

    }
}
