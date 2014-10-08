import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
  public static void main(String []args) throws IOException{
  	BufferedReader in = new BufferedReader(new FileReader("input.txt")); 
  	char map[][] = new char[20][40];
		for (int i = 0; i < 20; i++) {
			String line = in.readLine();
			for (int j=0; j < line.length(); j++) {
				map[i][j]=line.charAt(j);
			}
		}
		in.close();

		for (int i=0; i < map.length; i++ ) {
			for (int j=0; j < map[i].length; j++ ) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
  }
} 