import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class Car implements Runnable{
	private Game g;
	private int[] pos;
	private int[] initPos;
	private char dir;
	private boolean player;
	private char playerChar;
	private int playerInt;

	public Car(Game g, int[] pos, boolean player, int n){
		this.g = g;
		this.pos = pos;
		this.initPos = new int[2];
		this.initPos[0] = pos[0];
		this.initPos[1] = pos[1];
		this.dir = 'U';
		this.player = player;
		this.playerInt = n;
		if (player) {
			this.playerChar = 'V';
		}
		else{
			this.playerChar = 'E';
		}
	}

	public void run(){
		while(drive() == 0 && g.getLife() > 0 && g.getFlags() > 0){
      		if (this.g.getReset(this.playerInt)) {
      			resetPos();
        		this.g.setReset(this.playerInt,false);
      		}	
			try {
				Thread.sleep(300);
			} catch (Exception e) {
		    	System.out.println(e);
			}
		}
		
		System.out.println("Termino "+this.playerInt);
	}
	

	private int drive(){
		if (!player) {
			autoDrive();
			return 0;			
		}
		char next = '#';
		int newx, newy;
		newx = pos[1];
		newy = pos[0];
		
		synchronized(g.map){
			while(next == '#')
			{	
				newx = pos[1];
				newy = pos[0];
				if (dir == 'U') {
					next = g.map[pos[0]-1][pos[1]];
					newy--;
					
				}
				else if (dir == 'D') {
					next = g.map[pos[0]+1][pos[1]];
					newy++;
				}
				else if (dir == 'L') {
					next = g.map[pos[0]][pos[1]-1];
					newx--;
				}
				else if (dir == 'R') {
					next = g.map[pos[0]][pos[1]+1];
					newx++;
				}

				if (next == '#') {
					if(dir == 'L')
					{
						this.setDir('U');
					}
					else if(dir == 'U')
					{
						this.setDir('R');
					}
					else if(dir == 'R')
					{
						this.setDir('D');
					}
					else if(dir == 'D')
					{
						this.setDir('L');
					}
				}
			}
			if(next == 'B')
			{
				g.decFlags();
				
			}
			else if(next == 'O' || next == 'E')
			{	
				g.reset();
				resetPos();
				g.decLife();
				return 0;
			}
			g.map[pos[0]][pos[1]] = ' ';
			pos[1] = newx;
			pos[0] = newy;
			g.map[newy][newx] = playerChar;
			
			
		}
		return 0;
	}

	public void resetPos(){
		synchronized(g.map){
			g.map[pos[0]][pos[1]] = ' ';
			this.pos[0] = this.initPos[0];
			this.pos[1] = this.initPos[1];
			g.map[pos[0]][pos[1]] = playerChar;
		}
	}

	public void setDir(char dir){
		this.dir = dir;
	}
	public void autoDrive(){
		char next = '#';
		int newx, newy;
		newx = pos[1];
		newy = pos[0];
		int[] posV = new int[2];
		posV = this.g.getPosV();
		if(posV[0] > this.pos[0] )
		{
			setDir('D');
		}
		else if(posV[0] < this.pos[0] )
		{
			setDir('U');
		}
		else if(posV[1] < this.pos[1] )
		{
			setDir('L');
		}
		else if(posV[1] > this.pos[1] )
		{
			setDir('R');
		}
		synchronized(g.map){
			while(next == '#' || next == 'B' || next ==  'O' || next ==  'E')
			{	
				newx = pos[1];
				newy = pos[0];
				if (dir == 'U') {
					next = g.map[pos[0]-1][pos[1]];
					newy--;
					
				}
				else if (dir == 'D') {
					next = g.map[pos[0]+1][pos[1]];
					newy++;
				}
				else if (dir == 'L') {
					next = g.map[pos[0]][pos[1]-1];
					newx--;
				}
				else if (dir == 'R') {
					next = g.map[pos[0]][pos[1]+1];
					newx++;
				}

				if (next == '#' || next == 'B' || next ==  'O' || next ==  'E') {
					if(dir == 'L')
					{
						this.setDir('U');
					}
					// else if((dir == 'L' || dir == 'R') && posV[0] < this.pos[0])
					// {
					// 	this.setDir('D');
					// }
					else if(dir == 'U')
					{
						this.setDir('R');
					}
					// else if((dir == 'U' || dir == 'D') && posV[1] >zzz this.pos[1])
					// {
					// 	this.setDir('R');
					// }
					else if(dir == 'R')
					{
						this.setDir('D');
					}
					else if(dir == 'D')
					{
						this.setDir('L');
					}
				}
			}
			if(next == 'V')
			{
				this.g.reset();
				this.g.decLife();
				return;
			}
			g.map[pos[0]][pos[1]] = ' ';
			pos[1] = newx;
			pos[0] = newy;
			g.map[newy][newx] = playerChar;
			
			
		}
	}

	public int[] getPos()
	{
		return this.pos;
	}
}