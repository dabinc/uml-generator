package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * Creates KeyListeners for Player 1 so that he can go left, right, jump, up a
 * level, down a level, or pause the game.
 * 
 * @author
 *
 */
public class Player1Listener implements KeyListener {

	private Player player;
	private Game game;

	public Player1Listener(Player hero, Game game) {
		this.player = hero;
		this.game = game;
	}

	/**
	 * Takes key press input from the user and causes the player to react
	 * appropriately
	 * 
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_LEFT:
			this.player.left();
			break;
		case KeyEvent.VK_RIGHT:
			this.player.right();
			break;
		case KeyEvent.VK_UP:
			this.player.jump();
			break;
		case KeyEvent.VK_DOWN:
			this.game.addBubble(this.player.xLocation, this.player.yLocation, this.player.direction);
			break;
		case KeyEvent.VK_U:
			this.game.nextLevel();
			break;
		case KeyEvent.VK_B:
			this.game.previousLevel();
			break;
		case KeyEvent.VK_R:
			this.game.newGame();
			break;
		case KeyEvent.VK_P:
			this.game.pause();
			break;
		case KeyEvent.VK_SHIFT:
			if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
				if (this.player.hasBoomerang) {
					this.game.addBoomerang(this.player);
					this.player.hasBoomerang = false; 
				}
			}
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_LEFT:
			this.player.stop();
			break;
		case KeyEvent.VK_RIGHT:
			this.player.stop();
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
