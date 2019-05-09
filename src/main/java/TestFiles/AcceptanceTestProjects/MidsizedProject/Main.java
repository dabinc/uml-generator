package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The main class for our arcade game.
 * 
 * Creates a new frame, adds the players, and starts the timer when called to
 * start the game.
 * 
 * @author
 *
 */

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		URL url = null;
		try {
			url = new URL("https://cdn.dribbble.com/users/212842/screenshots/3278382/bub_bob_v2.gif");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		GUI gui = new GUI(url);

		gui.createGUI();
	}

}
