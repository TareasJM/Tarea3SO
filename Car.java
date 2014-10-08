public class Car implements Runnable{
	private Game g;
	private int[] pos;
	private char dir;

	public Car(Game g, int[] pos){
		this.g = g;
		this.pos = pos;
		this.dir = 'U';
	}

	public void run(){
		while(drive() == 0){
			try {
			  Thread.sleep(300);
			} catch (Exception e) {
		    System.out.println(e);
			}
		}
		System.out.println("end while");
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

			if (prev == '#') {
				g.decLife();
				return 1;
			}
		}
		return 0;
	}
}