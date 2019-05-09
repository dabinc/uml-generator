package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * Creates a hero projectile. The time the bubble exists before popping must be
 * Must be given a direction, x and y positions, time intervals, and if an enemy
 * is in the bubble.
 * 
 * @author
 *
 */

public class Bubble extends Projectile {
	private static final double MAX_TIME = 5000;
	private boolean reachCeiling;
	private long startTime;

	public Bubble(int x, int y, boolean direction) {
		super(x, y, direction);
		this.reachCeiling = false;
		this.enemy = false;
		try {
			this.graphic = ImageIO.read(new File("graphics/Bubble.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void finishRemoval() {
		
	}

	/**
	 * Bubble shoots forward until the max distance is traveled. The bubble then
	 * floats to the top of the screen, where it stays until the max existence time
	 * has been reached, at which point the bubble pops and is removed
	 * 
	 * @param
	 */
	@Override
	public void move() {
		if (!this.reachCeiling) {
			this.floatUp();
			this.xLocation += this.xVel;
			this.yLocation -= this.yVel;
		} else {
			this.remove = System.currentTimeMillis() - this.startTime > MAX_TIME;
		}
	}

	/**
	 * Checks if the bubble has traveled its max horizontal distance. If true, the
	 * bubble switches to float mode.
	 * 
	 * @param
	 */
	private void floatUp() {
		if (Math.abs(this.xLocation - this.startXLoc) > MAX_DIST) {
			this.xVel = 0;
			this.yVel = VELOCITY / 2;
			this.startTime = System.currentTimeMillis();
		}
	}

	/**
	 * Prevents bubble from floating through the ceiling.
	 * 
	 * @param
	 */
	@Override
	public void hitCeiling(Ceiling ceiling) {
		this.yLocation = ceiling.y + Block.BLOCK_SIZE;
		this.reachCeiling = true;
	}

	/**
	 * Checks if the bubble collides with an enemy sprite. If true, calls the
	 * hitProj method for the enemy and the bubble is removed from the level.
	 * 
	 * @param sprite
	 */
	@Override
	public boolean checkCollision(Sprite sprite) {
		if (this.boundingBox().intersects(sprite.boundingBox())) {
			if (sprite.enemy)
				sprite.hitProj();
			remove();
		}
		return false;
	}
}
