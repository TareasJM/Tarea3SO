import java.io.BufferedReader;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class Game extends JFrame{
  char map[][] = new char[20][40];
  Integer life;
  Integer flags;
  int posV[];
  int posE[][];
  JPanel main;
  JLabel blocks[];
  
  public Game(char[][] map, int[] posV, int[][] posE) throws IOException{
    this.map = map;
    this.posV = posV;
    this.posE = posE;
    this.life = 1;
    this.flags = 5;
    blocks = new JLabel[800];
    setTitle("Rali equis");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(new GridLayout(1,2));
    main = new JPanel();
    main.setLayout(new GridLayout(20,40)); //FILA / COLUMNAS
    for (int i=0; i < 20; i++ ) {
      for (int j=0; j < 40; j++ ) {
        blocks[i*20 + j] = new JLabel(map[i][j]+"");
        main.add(blocks[i*20 + j]);
      }
    } 
    add(main);
    pack();
    setVisible(true);
  }

  public int play() {
    Car v = new Car(this, posV, true);
    Thread vThread = new Thread(v);
    vThread.start();

    while(getLife()>0){
      try {
        Runtime.getRuntime().exec("clear");
        printMap();
        Thread.sleep(150);
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    return 0;
  }

  public void printMap() {
    char[][] m = getMap();
    int l = getLife();
    int f = getFlags();
    //System.out.println("Vidas:"+Integer.toString(life)+" | Banderas:"+Integer.toString(flags));
    //for (int i=0; i < m.length; i++ ) {
    //  for (int j=0; j < m[i].length; j++ ) {
    //    System.out.print(m[i][j]);
    //  }
    //  System.out.println();
    //}
    for (int i=0; i < 20; i++ ) {
      for (int j=0; j < 40; j++ ) {
        blocks[i*20 + j].setText(m[i][j]+"");
      }
    }  
  }

  public char[][] getMap(){
    synchronized(this.map){
      return this.map;
    }
  }

  public int getLife(){
    synchronized(this.life){
      return this.life;
    }
  }

  public void decLife(){
    synchronized(this.life){
      this.life--;
    }
  }

  public int getFlags(){
    synchronized(this.flags){
      return this.flags;
    }
  }

  public void decFlags(){
    synchronized(this.flags){
      this.flags--;
    }
  }
} 