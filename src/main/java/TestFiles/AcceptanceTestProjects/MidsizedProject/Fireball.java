package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * Enemy projectile class. Needs a position, and an enemy. Takes in a fireball
 * image or throws an exception if it does not exist. Moves with respect to the
 * enemy shooting the bubble and checks collisions with itself and other
 * objects.
 * 
 * @author
 *
 */

public class Fireball extends Projectile {
	public Fireball(int x, int y, boolean direction) {
		super(x, y, direction);
		this.enemy = true;
		try {
			this.graphic = ImageIO.read(new File("graphics/Fireball.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void finishRemoval() {

	}

	/**
	 * Fireball moves in a horizontal direction until the max distance is reached
	 * and the fireball is removed.
	 * 
	 * @param
	 */
	@Override
	public void move() {
		if (Math.abs(this.xLocation - this.startXLoc) <= MAX_DIST) {
			this.xLocation += this.xVel;
		} else {
			remove();
		}
	}

	/**
	 * A fireball object will never collide with a ceiling object so this method
	 * does nothing
	 * 
	 * @param ceiling
	 */
	@Override
	public void hitCeiling(Ceiling ceiling) {
		// do nothing
	}

	/**
	 * Checks if a player collides with a fire ball. If true, calls the hitProj
	 * method for the sprite and the fireball is removed
	 * 
	 * @param sprite
	 */
	@Override
	public boolean checkCollision(Sprite sprite) {
		if (!sprite.enemy && this.boundingBox().intersects(sprite.boundingBox())) {
			sprite.hitProj();
			remove();
			return true;
		}
		return false;
	}
}
