package Models;

import Models.Utils.Converter;

public class Board {
    public int getSIZE() {
        return SIZE;
    }

    private final int SIZE=16;

    public char[][] getBoard() {
        return cells;
    }

    private    char[][] cells;

    public char getCell(int x, int y) {
            return cells[x][y];
    }


    public Board(){
    cells=new char[SIZE][SIZE];
    initializeBoard();
  }

  public void initializeBoard(){
      for(int i=0;i<SIZE;i++){
          for(int j=0;j<SIZE;j++){
              cells[i][j]=' ';
          }
      }
  }

  public void printBoard(){
      System.out.print("   ");
      for (int i = 0; i < SIZE; i++) {
          System.out.print(" " + (char)('A' + i) + " ");
      }
      System.out.println();

      for (int i = 0; i < SIZE; i++) {
          int rowNumber = i + 1;
          String rowLabel = (rowNumber < 10) ? " " + rowNumber : String.valueOf(rowNumber);
          System.out.print(rowLabel + " ");
          for (int j = 0; j < SIZE; j++) {
              int columnNumber = j + 1;
              String columnLabel = (columnNumber < 10) ? " " + columnNumber : String.valueOf(columnNumber);
              if(getCell(j,i)=='O'){
                  System.out.print("[O]");
              }
              else if (getCell(j, i) == '*') {
                  System.out.print("[ ]"); // Если клетка содержит корабль или 'X', выводим '[X]'
              } else if(getCell(j,i)==' '){
                  System.out.print("[ ]"); // В остальных случаях выводим пробел
              }else if(getCell(j,i)=='X'){
                  System.out.print("[X]");
              }
          }
          System.out.println();
      }
  }
    public void updateCell(int x, int y, char value) {
        cells[x][y] = value;
    }

    public void markNearShips(Board board) {
        int boardSize = board.getSIZE();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.getCell(i, j) == 'O') {
                    markNearCells(board, i, j);
                }
            }
        }
    }

    private void markNearCells(Board board, int x, int y) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        int boardSize = board.getSIZE();

        for (int k = 0; k < dx.length; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];

            if (nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize && board.getCell(nx, ny) == ' ') {
                board.updateCell(nx, ny, '*');
            }
        }
    }
    private void markNearDestroyShip(Board board, int x, int y) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        int boardSize = board.getSIZE();

        for (int k = 0; k < dx.length; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];

            if (nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize && board.getCell(nx, ny) == '*') {
                board.updateCell(nx, ny, '.');
            }
        }
    }
    public void surroundDestroyedShip(Board board,Ship ship) {
        Converter converter=new Converter();
        int sx=converter.convertLetterToNumber(ship.getStartX());
        int sy=Integer.parseInt(ship.getStartY());
        if (ship.getOrientation() == Orientation.HORIZONTAL) {
            for (int j = sx - 1; j < sx + ship.getSize() - 1; j++) {
               int x = j;
              int y = sy - 1;
               markNearDestroyShip(board,x,y);
            }
        } else {

            for (int j = sy - 1; j < sy + ship.getSize() - 1; j++) {
                int x = sx - 1;
                int y = j;
                markNearDestroyShip(board,x,y);


            }
        }

    }










}



