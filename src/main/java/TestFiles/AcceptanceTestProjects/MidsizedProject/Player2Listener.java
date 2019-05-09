package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * Creates KeyListeners for Player 2 so that he can go left, right, jump, up a
 * level, down a level, or pause the game.
 * 
 * @author
 *
 */
public class Player2Listener implements KeyListener {
	private Player player;
	private Game game;

	public Player2Listener(Player hero, Game game) {
		this.player = hero;
		this.game = game;
	}

	/**
	 * Takes user input and causes player 2 to react approriately
	 * 
	 * @param
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_A:
			this.player.left();
			break;
		case KeyEvent.VK_D:
			this.player.right();
			break;
		case KeyEvent.VK_W:
			this.player.jump();
			break;
		case KeyEvent.VK_S:
			this.game.addBubble(this.player.xLocation, this.player.yLocation, this.player.direction);
			break;
		case KeyEvent.VK_SHIFT:
			if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) {
				if (this.player.hasBoomerang) { 
					this.game.addBoomerang(this.player);
					this.player.hasBoomerang = false;
				}
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_A:
			this.player.stop();
			break;
		case KeyEvent.VK_D:
			this.player.stop();
			break;
		default:
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
