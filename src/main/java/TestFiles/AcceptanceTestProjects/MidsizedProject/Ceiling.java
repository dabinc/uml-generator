package TestFiles.AcceptanceTestProjects.MidsizedProject;
/**
 * 
 * Takes a block ceiling and checks collisions with projectiles and sprites.
 * 
 * @author
 *
 */
public class Ceiling extends Block {
	public Ceiling(int x, int y) {
		super(x, y);
	}

	/**
	 * Checks if a projectile collides with the ceiling. If true, calls hitCeiling
	 * method for the projectile.
	 * 
	 * @param proj
	 */
	@Override
	public void checkCollision(Projectile proj) {
		if (this.boundingBox().intersects(proj.xLocation, proj.yLocation, Projectile.PROJ_BOUNDS, Projectile.PROJ_BOUNDS)) {
			proj.hitCeiling(this);
		}
	}

	/**
	 * Checks if a sprite collides with the ceiling. If true, calls the hitCeiling
	 * method for the sprite so the collision can be handled properly
	 * 
	 * @param sprite
	 */
	@Override
	public void checkCollision(Sprite sprite) {
		if (this.boundingBox().intersects(sprite.xLocation, sprite.yLocation, Sprite.SPRITE_SIZE, Sprite.SPRITE_SIZE)) {
			sprite.hitCeiling(this);
		}
	}

	/**
	 * A fruit object will never collide with a ceiling object so this function does
	 * nothing
	 * 
	 * @param fruit
	 */
	@Override
	void checkCollision(Fruit fruit) {
		// do nothing
	}

}
