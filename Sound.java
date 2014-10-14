
import java.applet.*;
import java.net.*;
import sun.audio.*;    //import the sun.audio package
import java.io.*;

public class Sound implements Runnable{

	AudioClip clip;
	Game g;
	int n;

	public Sound(Game g, String name, int n)
	{	
		this.g = g;
		this.n = n;
		try{
			this.clip = Applet.newAudioClip(new URL("file://"+System.getProperty("user.dir")+"/resources/"+name));
		}catch(MalformedURLException ex){
		//do exception handling here
		}
	}
	public void run()
	{	
		if(this.n == 0)
		{
			this.clip.loop();
		}
		else
		{	
		
			this.clip.play();

		}

	}
	public void stop()
	{
		this.clip.stop();
	}
}