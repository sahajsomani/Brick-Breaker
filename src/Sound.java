import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;

import javax.sound.sampled.Clip;

import sun.audio.*;

public class Sound {

	Clip backgroundMusic;
	

	public void playSound() { // adding music when the ball touches the brick
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("pop final.wav"));
			Clip clip = AudioSystem.getClip( );
			clip.open(audioInputStream);
			clip.start( );
			
		}
		catch(Exception e)  {
			System.out.println(e);
		} 


	}

	public void backgroundMusic() { // adding a song as background music
		try {
						
			File song = new File("HFTW.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(song);
			backgroundMusic = AudioSystem.getClip( );
			backgroundMusic.open(audioInputStream);
			backgroundMusic.start();
			// File song = new File("HFTW.wav");


		}
		catch(Exception e)  {
			System.out.println(e);
		} 



	}
	public void stopBackgroundMusic() { // method for stopping background music when the the user wins or loses
		backgroundMusic.stop();
	}

	public void sparkleMusic() { // sound when the user wins the game
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sparkle_sound.wav"));
			Clip clip = AudioSystem.getClip( );
			clip.open(audioInputStream);
			clip.start( );
			
		}
		catch(Exception e)  {
			System.out.println(e);
		} 


	}

	public void failureMusic() { // sound when the user loses the game
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("failure sound.wav"));
			Clip clip = AudioSystem.getClip( );
			clip.open(audioInputStream);
			clip.start( );
			

		}
		catch(Exception e)  {
			System.out.println(e);
		} 


	}
}

