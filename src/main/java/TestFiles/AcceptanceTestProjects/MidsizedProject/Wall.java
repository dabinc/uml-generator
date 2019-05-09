package TestFiles.AcceptanceTestProjects.MidsizedProject;
/**
 * 
 * Type of block that does not allow a character to run through it on the left
 * or right sides. Collisions are checked.
 * 
 * @author
 *
 */

public class Wall extends Block {

	public Wall(int x, int y) {
		super(x, y);
	}

	/**
	 * Checks if a sprite collides with a wall. If so, calls wallHit based on which
	 * side the sprite hits the wall from
	 * 
	 * @param
	 */
	@Override
	public void checkCollision(Sprite sprite) {
		if (this.boundingBox().intersects(sprite.xLocation, sprite.yLocation, Sprite.SPRITE_SIZE, Sprite.SPRITE_SIZE)) {
			if (sprite.xVel < 0)
				sprite.wallHit(this.x + BLOCK_SIZE);
			else if (sprite.xVel > 0)
				sprite.wallHit(this.x - Sprite.SPRITE_SIZE);
		}
	}

	/**
	 * Checks if a projectile collides with a wall. If so, the projectile is removed
	 * from the level
	 * 
	 * @param
	 */
	@Override
	void checkCollision(Projectile proj) {
		if (this.boundingBox().intersects(proj.xLocation, proj.yLocation, Projectile.PROJ_BOUNDS,
				Projectile.PROJ_BOUNDS))
			proj.remove();
	}

	/**
	 * Collisions between fruit and wall do not matter so this function is not
	 * implemented
	 * 
	 * @param
	 */
	@Override
	void checkCollision(Fruit fruit) {
		// do nothing
	}

}
