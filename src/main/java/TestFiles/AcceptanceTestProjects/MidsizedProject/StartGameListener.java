package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 
 * Starts a single player game when "single player" is pressed. Starts a two
 * player game when "two player" is pressed. Goes to the instructions screen
 * when "instructions" is pressed and returns to the main screen when "return"
 * is selected.
 * 
 * @author Hailey
 *
 */
public class StartGameListener implements MouseListener {
	private Game game;

	public StartGameListener(Game game) {
		this.game = game;
	}

	/**
	 * Tracks mouse clicks and responds based on which "button" is pressed
	 * 
	 * @param
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Point click = e.getPoint();

		switch (this.game.clickLoc(click)) {
		case 1:
			this.game.singlePlayer();
			break;
		case 2:
			this.game.twoPlayer();
			break;
		case 3:
			this.game.instructions();
			break;
		case 4:
			this.game.newGame();
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// does nothing
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// does nothing
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// does nothing
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// does nothing
	}
}
