import java.io.BufferedReader;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.applet.*;


public class Game extends JFrame implements KeyListener{
  char map[][] = new char[20][40];
  Boolean reset[];
  Integer life;
  Integer flags;
  int posV[];
  int posE[][];
  JPanel main;
  // JLabel blocks[];
  JBackgroundPanel blocks[];
  Car carV;
  Car carE[];
  BufferedImage[] imagenes;
  Sound aClip;
  Sound aClip2;
  Sound aClip3;
  Sound aClip4;
  ImageIcon icon1;
  ImageIcon icon2;

  public Game(char[][] map, int[] posV, int[][] posE) throws IOException{
    this.map = map;
    this.posV = posV;
    this.posE = posE;
    this.life = 2;
    this.flags = 4;
    this.reset = new Boolean[5];
    this.reset[0] = this.reset[1] = this.reset[2] = this.reset[3] = this.reset[4] = false;
    blocks = new JBackgroundPanel[800];
    addKeyListener(this);
    setTitle("Rali equis");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(new GridLayout(1,2));
    main = new JPanel();
    main.setLayout(new GridLayout(20,40)); //FILA / COLUMNAS
    // this.aClip = Applet.newAudioClip(
    //         new java.net.URL("file://"+System.getProperty("user.dir")+"/resources/rally.wav"));
    // aClip.play();
    imagenes = new BufferedImage[6];

    imagenes[0]=ImageIO.read(new File("./resources/carV.jpg"));
    imagenes[1]=ImageIO.read(new File("./resources/carE.jpg"));
    imagenes[2]=ImageIO.read(new File("./resources/wall.jpg"));
    imagenes[3]=ImageIO.read(new File("./resources/grass.jpg"));
    imagenes[4]=ImageIO.read(new File("./resources/bomb.jpg"));
    imagenes[5]=ImageIO.read(new File("./resources/flag.jpg"));
    this.icon1  = new ImageIcon("./resources/hf.jpg");
    this.icon2  = new ImageIcon("./resources/go.jpg");
    this.aClip4 = new Sound(this,"crash.wav",1);
    this.aClip = new Sound(this,"music.wav",0);
    this.aClip2 = new Sound(this,"go.wav",1);
    this.aClip3 = new Sound(this,"win.wav",1);

    for (int i=0; i < 20; i++ ) {
      for (int j=0; j < 40; j++ ) {
        blocks[i*40+j] = new JBackgroundPanel();
        blocks[i*40+j].setPreferredSize(new Dimension(30,30));
        if(map[i][j] == 'V')
        {
          blocks[i*40 + j].setBackgroundPanel(imagenes[0]);
        }
        else if(map[i][j] == 'E')
        {
          blocks[i*40 + j].setBackgroundPanel(imagenes[1]);
        }
        else if(map[i][j] == '#')
        {
          blocks[i*40 + j].setBackgroundPanel(imagenes[2]);
        }
        else if(map[i][j] == ' ')
        {
          blocks[i*40 + j].setBackgroundPanel(imagenes[3]);
        }
        else if(map[i][j] == 'O')
        {
          blocks[i*40 + j].setBackgroundPanel(imagenes[4]);
        }
        else if(map[i][j] == 'B')
        {
          blocks[i*40 + j].setBackgroundPanel(imagenes[5]);
        }
        blocks[i*40+j].setVisible(true);
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
    // Thread sThread = new Thread(aClip);
    vThread.start();
    e0Thread.start();
    e1Thread.start();
    e2Thread.start();
    e3Thread.start();
    this.aClip.run();

    while(getLife()>0 && getFlags()>0){

      try {

        printMap();
        Thread.sleep(150);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    // sThread.stop();
    this.aClip.stop();
    setVisible(false);
    dispose();
    if(getLife() < 1)
    { 
      this.aClip2.run();
      String message = "<html><body><div width='200px' align='center'>Has perdido Rali equis</div></body></html>";
      JLabel messageLabel = new JLabel(message);
      JOptionPane.showMessageDialog(null,  messageLabel, "Que pena :(", JOptionPane.ERROR_MESSAGE, icon2);
      this.aClip2.stop();
    }
    else
    { 
      this.aClip3.run();
      String message = "<html><body><div width='200px' align='center'>Has ganado Rali equis!</div></body></html>";
      JLabel messageLabel = new JLabel(message);
      JOptionPane.showMessageDialog(null, messageLabel, "yupi :)", JOptionPane.INFORMATION_MESSAGE,icon1);
      this.aClip3.stop();
      
    }
    
    
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

        if(m[i][j] == 'V')
        {
          blocks[i*40 + j].setBackgroundPanel(this.imagenes[0]);
        }
        else if(m[i][j] == 'E')
        {
          blocks[i*40 + j].setBackgroundPanel(this.imagenes[1]);
        }
        else if(m[i][j] == ' ')
        {
          blocks[i*40 + j].setBackgroundPanel(this.imagenes[3]);
        }

         else if(m[i][j] == 'B')
        {
          blocks[i*40 + j].setBackgroundPanel(this.imagenes[5]);
        }
     
      }
    }  
    revalidate();
    repaint();

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
    

    aClip4.run();
    synchronized(this.reset){


     this.reset[0] = this.reset[1] = this.reset[2] = this.reset[3] = this.reset[4] = true;

    }
    try
    {
      Thread.sleep(1000);
    }catch (Exception e) {
        System.out.println(e);
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

  public class JBackgroundPanel extends JPanel {
    private BufferedImage img;
    private int color;

    public void setBackgroundPanel(BufferedImage img) {
      this.img = img;
    }

    public int getColor(){
      return this.color;
    }

    public void setColor(int color){
      this.color = color;
    }

    @Override
      protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img, 0, 0, 30, 30, null);
    }
    }

} 