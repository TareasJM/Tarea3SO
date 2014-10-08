import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
  public static void main(String []args) throws IOException{
		char map[][] = new char[20][40];
		int posV[] = new int[2];
		int posE[][] = new int[4][2];
  	int e = 0;
  	BufferedReader in = new BufferedReader(new FileReader("input.txt")); 
		for (int i = 0; i < 20; i++) {
			String line = in.readLine();
			for (int j=0; j < line.length(); j++) {
				char c = map[i][j] = line.charAt(j);
				if (c == 'V') {
					posV[0] = i;
					posV[1] = j;
				}
				else if (c == 'E') {
					posE[e][0] = i;
					posE[e++][1] = j;
				}
			}
		}
		in.close();

		Game g = new Game(map, posV, posE);

		g.play();
		
  }

} 