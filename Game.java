import java.io.BufferedReader;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements KeyListener{
  char map[][] = new char[20][40];
  Integer life;
  Integer flags;
  int posV[];
  int posE[][];
  JPanel main;
  JLabel blocks[];
  Car carV;
  
  public Game(char[][] map, int[] posV, int[][] posE) throws IOException{
    this.map = map;
    this.posV = posV;
    this.posE = posE;
    this.life = 1;
    this.flags = 5;
    blocks = new JLabel[800];
    addKeyListener(this);
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
    this.carV = new Car(this, posV, true);
    Thread vThread = new Thread(carV);
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

  public void keyTyped(KeyEvent e) {
  }

  public void keyPressed(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
    if(e.getKeyCode()== KeyEvent.VK_RIGHT)
      this.carV.setDir('R');
    else if(e.getKeyCode()== KeyEvent.VK_LEFT)
      this.carV.setDir('L');
    else if(e.getKeyCode()== KeyEvent.VK_DOWN)
      this.carV.setDir('D');
    else if(e.getKeyCode()== KeyEvent.VK_UP)
      this.carV.setDir('U');
  }
} 