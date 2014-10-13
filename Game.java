import java.io.BufferedReader;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements KeyListener{
  char map[][] = new char[20][40];
  Boolean reset[];
  Integer life;
  Integer flags;
  int posV[];
  int posE[][];
  JPanel main;
  JLabel blocks[];
  Car carV;
  Car carE[];
  
  public Game(char[][] map, int[] posV, int[][] posE) throws IOException{
    this.map = map;
    this.posV = posV;
    this.posE = posE;
    this.life = 2;
    this.flags = 4;
    this.reset = new Boolean[5];
    this.reset[0] = this.reset[1] = this.reset[2] = this.reset[3] = this.reset[4] = false;
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
        blocks[i*40 + j] = new JLabel(map[i][j]+"");
        main.add(blocks[i*40 + j]);
      }
    } 
    add(main);
    pack();
    setVisible(true);
  }

  public int play() {
    this.carV = new Car(this, posV, true,0);
    this.carE = new Car[4];
    this.carE[0] = new Car(this, posE[0], false,1);
    this.carE[1] = new Car(this, posE[1], false,2);
    this.carE[2] = new Car(this, posE[2], false,3);
    this.carE[3] = new Car(this, posE[3], false,4);
    Thread vThread = new Thread(carV);
    Thread e0Thread = new Thread(carE[0]);
    Thread e1Thread = new Thread(carE[1]);
    Thread e2Thread = new Thread(carE[2]);
    Thread e3Thread = new Thread(carE[3]);
    vThread.start();
    e0Thread.start();
    e1Thread.start();
    e2Thread.start();
    e3Thread.start();

    while(getLife()>0 && getFlags()>0){

      try {
        printMap();
        Thread.sleep(150);
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    System.out.println("salio while");
    setVisible(false);
    dispose();
    
    return 0;
  }

  public void printMap() {
    char[][] m = getMap();
    int l = getLife();
    int f = getFlags();
    setTitle("Rali equis     "  + "Banderas = " + f + " Vidas = " + l);
    //System.out.println("Vidas:"+Integer.toString(life)+" | Banderas:"+Integer.toString(flags));
    //for (int i=0; i < m.length; i++ ) {
    //  for (int j=0; j < m[i].length; j++ ) {
    //    System.out.print(m[i][j]);
    //  }
    //  System.out.println();
    //}
    for (int i=0; i < 20; i++ ) {
      for (int j=0; j < 40; j++ ) {
        blocks[i*40 + j].setText(m[i][j]+"");
      }
    }  
    pack();
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

  public void reset(){
   
    synchronized(this.reset){

     this.reset[0] = this.reset[1] = this.reset[2] = this.reset[3] = this.reset[4] = true;
    }
  }
  public void setReset(int n, Boolean r){
    synchronized(this.reset){
      this.reset[n] = r;
    }
  }


  public Boolean getReset(int n){
    synchronized(this.reset){
      return this.reset[n];
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

  public int[] getPosV()
  {
    return this.carV.getPos();
  }
} 