package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * Creates a timer for the game which allows the component to update as time
 * goes on.
 * 
 * @author Hailey
 *
 */
public class TimerListener implements ActionListener {
	private Game game;

	public TimerListener(Game game) {
		this.game = game;
	}

	/**
	 * While the game is not paused, updates the screen once every delay period
	 * 
	 * @param
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!this.game.isPaused()) {
			this.game.updateScreen();
		}
	}
}