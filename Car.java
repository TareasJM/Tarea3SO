import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Car implements Runnable{
	private Game g;
	private int[] pos;
	private char dir;
	private boolean player;

	public Car(Game g, int[] pos, boolean player){
		this.g = g;
		this.pos = pos;
		this.dir = 'U';
		this.player = player;
	}

	public void run(){
		while(drive() == 0){
			try {
				Thread.sleep(300);
			} catch (Exception e) {
		    	System.out.println(e);
			}
		}
	}

	private int drive(){
		char prev = ' ';
		synchronized(g.map){
			if (dir == 'U') {
				prev = g.map[pos[0]-1][pos[1]];
				g.map[pos[0]-1][pos[1]] = g.map[pos[0]][pos[1]];
				g.map[pos[0]][pos[1]] = ' '; 
				pos[0]--;
			}
			else if (dir == 'D') {
				prev = g.map[pos[0]+1][pos[1]];
				g.map[pos[0]+1][pos[1]] = g.map[pos[0]][pos[1]];
				g.map[pos[0]][pos[1]] = ' '; 
				g.map[pos[0]+1][pos[1]] = 'V'; 
				pos[0]++;
			}
			else if (dir == 'L') {
				prev = g.map[pos[0]][pos[1]-1];
				g.map[pos[0]][pos[1]-1] = g.map[pos[0]][pos[1]];
				g.map[pos[0]][pos[1]] = ' '; 
				g.map[pos[0]][pos[1]-1] = 'V'; 
				pos[1]--;
			}
			else if (dir == 'R') {
				prev = g.map[pos[0]][pos[1]+1];
				g.map[pos[0]][pos[1]+1] = g.map[pos[0]][pos[1]];
				g.map[pos[0]][pos[1]] = ' '; 
				g.map[pos[0]][pos[1]+1] = 'V'; 
				pos[1]++;
			}

			if (prev == '#') {
				g.decLife();
				return 1;
			}
		}
		return 0;
	}

	public void setDir(char dir){
		this.dir = dir;
	}
}