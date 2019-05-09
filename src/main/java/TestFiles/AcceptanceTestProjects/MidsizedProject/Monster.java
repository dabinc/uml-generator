package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.Image;

/**
 * 
 * Monster is a type of sprite that is created in a level. It can be a shooter
 * or just a whlae monster and can be trapped in hero bubbles. This class
 * handles a monster's collisions, movement, and image iteration.
 * 
 * @author Hailey
 *
 */
public abstract class Monster extends Sprite {
	protected static final long MAX_TIME = 15000;

	protected Level level;
	protected boolean shooter;
	protected boolean trapped;
	protected long startTime;
	protected Image trappedGraphic;

	public Monster(int x, int y, Level level) {
		super(x, y);
		setGraphics();
		this.level = level;
		this.enemy = true;

		if (Math.random() < .5) {
			this.xVel = -NORM_SPEED;
		} else {
			this.xVel = NORM_SPEED;
		}
	}

	/**
	 * Monsters move, jump, and shoot (if a shooter) randomly while not trapped in a
	 * bubble. If trapped, they float to the top of the screen where they wait until
	 * they are popped by the player or the max trap time is reached
	 * 
	 * @param
	 */
	@Override
	public void move() {
		if (!this.trapped) {
			super.move();
			switchDirection();
			shouldJump();
			shootFireball();
		}

		else {
			this.yLocation += this.yVel;
			if (System.currentTimeMillis() - this.startTime > MAX_TIME) {
				this.xVel = NORM_SPEED;
				this.trapped = false;
			}
		}
	}

	/**
	 * Causes the monster to "bounce" off the wall it collides with
	 * 
	 * @param
	 */
	@Override
	public void wallHit(int newXPos) {
		this.xLocation = newXPos;
		this.xVel *= -1;
	}

	/**
	 * If the monster is not trapped in a bubble, it bounces off the ceiling. If it
	 * is trapped, the monster "floats" at the top of the screen
	 * 
	 * @param
	 */
	@Override
	public void hitCeiling(Ceiling ceiling) {
		if (!this.trapped) {
			super.hitCeiling(ceiling);
		} else {
			this.yLocation = ceiling.y + Block.BLOCK_SIZE;
			this.yVel = 0;
		}
	}

	/**
	 * Shoots a fireball in the direction the monster in facing
	 * 
	 * @param
	 */
	private void shootFireball() {
		boolean direction = true;
		if (Math.random() < .05 && this.shooter) {
			if (this.xVel < 0) {
				direction = false;
			}
			this.level.addFireBall(this.xLocation, this.yLocation, direction);
		}

	}

	/**
	 * Checks if the monster should jump
	 * 
	 * @param
	 */
	public void shouldJump() {
		if (Math.random() < .01)
			jump();
	}

	/**
	 * Checks if the monster should switch direction
	 * 
	 * @param
	 */
	public void switchDirection() {
		if (Math.random() < .01)
			this.xVel = -this.xVel;
	}

	/**
	 * Removes monster from the level and creates a new fruit in its location
	 * 
	 * @param
	 */
	@Override
	public void die() {
		this.remove = true;
		this.level.addFruit(new Fruit(this.xLocation, this.yLocation));
	}

	/**
	 * Traps monster in bubble if hit with a bubble
	 * 
	 * @param
	 */
	@Override
	public void hitProj() {
		if (!this.trapped)
			bubbleTrap();
	}

	/**
	 * Monster floats to the top of the screen when trapped
	 * 
	 * @param
	 */
	protected void bubbleTrap() {
		this.falling = false;
		this.trapped = true;
		this.xVel = 0;
		this.yVel = -20;
		this.startTime = System.currentTimeMillis();
	}

	/**
	 * Updates the monsters graphic based on its type of movement
	 * 
	 * @param
	 */
	@Override
	public void updateGraphics() {
		if (!this.trapped) {
			super.updateGraphics();
		} else {
			this.currentGraphic = this.trappedGraphic;
		}
	}

	/**
	 * If the monster is not trapped and it collides with a player, the player dies.
	 * Otherwise the monster dies.
	 * 
	 * @param
	 */
	@Override
	public boolean handleCollision(Player bub) {
		if (this.boundingBox().intersects(bub.boundingBox())) {
			if (this.trapped) {
				die();
			} else {
				bub.die();
				return true;
			}
		}
		return false;
	}

	/**
	 * Abstract function to be implemented by the subclasses
	 */
	@Override
	public abstract void setGraphics();
}
