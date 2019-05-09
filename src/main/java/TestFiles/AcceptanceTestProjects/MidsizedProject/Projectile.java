package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * Given a velocity, max distance, and a size, a projectile is formed. This
 * class needs to know whether the projectile is shot from an enemy or from a
 * hero. Also, this needs to know where the bubble is shot from and the graphic
 * to be used.
 * 
 * @author
 * 
 */
public abstract class Projectile implements Renderable {
	protected static final double VELOCITY = 20;
	protected static final int MAX_DIST = 150;
	protected static final int BOOMERANG_MAX_DIST = 300;
	protected static final int PROJ_SIZE = 30;
	protected static final int PROJ_BOUNDS = 50;

	protected boolean remove;
	protected boolean enemy;
	protected double xVel;
	protected double yVel;
	protected int xLocation;
	protected int yLocation;
	protected int startXLoc;
	protected Image graphic;

	public Projectile(int x, int y, boolean direction) {
		if (direction) {
			this.xVel = VELOCITY;
			this.xLocation = x + Sprite.SPRITE_SIZE;
			this.startXLoc = x + Sprite.SPRITE_SIZE;
		} else {
			this.xVel = -VELOCITY;
			this.xLocation = x - PROJ_SIZE;
			this.startXLoc = x - PROJ_SIZE;
		}
		this.yLocation = y;
		this.yVel = 0;
	}

	/**
	 * Indicates if a projectile should be removed from the level
	 * 
	 * @param
	 */
	public void remove() {
		this.remove = true;
		this.finishRemoval();
	}

	public abstract void finishRemoval();

	/**
	 * Returns a bounding box for the projectile
	 * 
	 * @param
	 */
	public Rectangle boundingBox() {
		return new Rectangle(this.xLocation, this.yLocation, PROJ_SIZE, PROJ_SIZE);
	}

	/**
	 * If a bubble and fireball collide, the two cancel each other out and are
	 * removed
	 * 
	 * @param
	 */
	public void checkCollision(Projectile proj) {
		if (this.boundingBox().intersects(proj.boundingBox()) && this.enemy != proj.enemy) {
			this.remove();
			proj.remove();
		}
	}

	@Override
	public Image getCurrentGraphic() {
		return this.graphic;
	}

	/**
	 * Abstract methods to be implemented by subclasses
	 */
	public abstract void move();

	public abstract void hitCeiling(Ceiling ceiling);

	public abstract boolean checkCollision(Sprite sprite);

}
