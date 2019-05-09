package TestFiles.AcceptanceTestProjects.MidsizedProject;
/**
 * 
 * Platform is a type of block that is a B when read in the level files. A
 * sprite can jump up through a platform, but it cannot fall through the top of
 * the platform. Platform checks collisions with each object.
 * 
 * @author
 *
 */
public class Platform extends Block {

	public Platform(int x, int y) {
		super(x, y);
	}

	/**
	 * If a sprite collides while falling, the sprite lands. If the sprite is not
	 * already landed on another platform, the sprite continues to fall
	 * 
	 * @param
	 */
	@Override
	public void checkCollision(Sprite sprite) {
		if (this.boundingBox().intersectsLine(sprite.baseLine()) && sprite.baseLine().getY1() >= this.y
				&& sprite.baseLine().getY1() < this.y + 35 && !sprite.jumping) {
			sprite.land(this);
		} else if (!sprite.platformChecked) {
			sprite.fall();
		}
	}

	/**
	 * If a fruit falls onto a platform, it lands.
	 * 
	 * @param
	 */
	@Override
	public void checkCollision(Fruit fruit) {
		if (this.boundingBox().intersects(fruit.boundingBox())) {
			fruit.land(this);
		}
	}

	/**
	 * Collisions with projectiles do not matter, so this function is not
	 * implemented
	 * 
	 * @param
	 */
	@Override
	void checkCollision(Projectile proj) {
		// do nothing
	}

}
