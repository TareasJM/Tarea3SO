import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Console;

public class Game {
	char map[][] = new char[20][40];
	Integer life;
	Integer flags;
	int posV[];
	int posE[][];
	
  public Game(char[][] map, int[] posV, int[][] posE) {
  	this.map = map;
		this.posV = posV;
		this.posE = posE;
		this.life = 1;
		this.flags = 5;
  }

  public int play() {
  	Car v = new Car(this, posV);
  	Thread vThread = new Thread(v);
  	vThread.start();

  	while(getLife()>0){
			try {
		  	Runtime.getRuntime().exec("clear");
		  	printMap();
        Console console = System.console();
        System.out.println("Movimientos: W (up) - S (down) - A (left) - D (right)");
        String input = console.readLine("Ingrese movimiento:\n");
        v.setDir(input);    
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
		System.out.println("Vidas:"+Integer.toString(life)+" | Banderas:"+Integer.toString(flags));
  	for (int i=0; i < m.length; i++ ) {
			for (int j=0; j < m[i].length; j++ ) {
				System.out.print(m[i][j]);
			}
			System.out.println();
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