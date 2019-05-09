package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Sprite is an abstract class that creates all of ther players and monsters in
 * the game. Size, speed, and other information like if the character is
 * falling, or on a platform or an enemy are needed to create the character.
 * Also, this class changes the graphics as the characters moved based on their
 * velocities. Collisions are handles for each individual character.
 * 
 * @author
 *
 */
public abstract class Sprite implements Renderable {
	public static final double NORM_SPEED = 15;
	public static final double NORM_JUMP_VEL = -30;
	public static final int SPRITE_SIZE = 50;
	public static final double GRAVITY = 2;

	protected int xLocation;
	protected int yLocation;
	protected int intitialX;
	protected int intitialY;

	protected double xVel;
	protected double yVel;

	protected boolean jumping;
	protected boolean falling;
	protected boolean landed;
	protected boolean platformChecked;
	protected boolean enemy;
	protected boolean remove;

	protected int rightIndex;
	protected int leftIndex;
	protected int fallIndex;
	protected int jumpIndex;
	protected ArrayList<Image> leftGraphics = new ArrayList<>();
	protected ArrayList<Image> rightGraphics = new ArrayList<>();
	protected ArrayList<Image> jumpGraphics = new ArrayList<>();
	protected ArrayList<Image> fallGraphics = new ArrayList<>();
	protected Image currentGraphic;
	protected Image defaultGraphic;

	public Sprite(int x, int y) {
		this.xLocation = x;
		this.yLocation = y;
		this.intitialX = x;
		this.intitialY = y;
		this.yVel = 20;
		this.jumping = false;
		this.falling = true;
		this.remove = false;
		this.currentGraphic = this.defaultGraphic;
	}

	/**
	 * Resets sprite to the location it was created at
	 * 
	 * @param
	 */
	public void resetLoc() {
		this.xLocation = this.intitialX;
		this.yLocation = this.intitialY;
		this.currentGraphic = this.defaultGraphic;
	}

	/**
	 * Updates sprites position
	 * 
	 * @param
	 */
	public void move() {
		this.xLocation += this.xVel;
		this.yLocation += this.yVel;
		if (this.falling) {
			if (this.yVel <= 20) {
				this.yVel += GRAVITY;
				if (this.yVel > 0) {
					this.jumping = false;
				}
			}
		} else
			this.yVel = 0;
	}

	/**
	 * Allows sprite to jump at a given velocity, which is affected by gravity
	 * 
	 * @param
	 */
	public void jump() {
		if (this.landed) {
			this.yVel = NORM_JUMP_VEL;
			this.jumping = true;
			this.falling = true;
			this.landed = false;
		}
	}

	/**
	 * Ensures the sprite is falling when it is not on a platform
	 * 
	 * @param
	 */
	public void fall() {
		this.falling = true;
		this.landed = false;
	}

	/**
	 * Prevents sprite from passing through platforms when landing on the top
	 * 
	 * @param
	 */
	public void land(Platform platform) {
		this.falling = false;
		this.jumping = false;
		this.landed = true;
		this.platformChecked = true;
		this.yLocation = platform.y - SPRITE_SIZE;
	}

	/**
	 * Returns a line corresponding to the base line of the sprite
	 * 
	 * @param
	 */
	public Line2D baseLine() {
		return new Line2D.Double(this.xLocation, this.yLocation + SPRITE_SIZE + 8, this.xLocation + SPRITE_SIZE,
				this.yLocation + SPRITE_SIZE + 8);
	}

	/**
	 * Returns bounding box for the sprite
	 * 
	 * @param
	 */
	public Rectangle boundingBox() {
		return new Rectangle(this.xLocation, this.yLocation, SPRITE_SIZE, SPRITE_SIZE);
	}

	/**
	 * Prevents sprite form passing through the ceiling by making it bounce off
	 * 
	 * @param
	 */
	public void hitCeiling(Ceiling ceiling) {
		this.yVel = 10;
		this.falling = true;
		this.yLocation = ceiling.y + Block.BLOCK_SIZE;
	}

	/**
	 * Updates the graphic for the sprite based on movement
	 * 
	 * @param
	 */
	public void updateGraphics() {
		if (!this.jumping && !this.falling) {
			if (this.xVel > 0) {
				switchRightGraphic();
			} else if (this.xVel < 0) {
				switchLeftGraphic();
			}
		} else if (this.jumping) {
			switchJumpGraphic();
		} else {
			switchFallGraphic();
		}
		if (this.landed && this.fallGraphics.contains(this.currentGraphic)) {
			this.currentGraphic = this.defaultGraphic;
		}
	}

	/**
	 * Iterates through moving right graphics
	 * 
	 * @param
	 */
	public void switchRightGraphic() {
		if (this.rightIndex == this.rightGraphics.size() - 1) {
			this.rightIndex = 0;
		} else {
			this.rightIndex++;
		}
		this.currentGraphic = this.rightGraphics.get(this.rightIndex);
	}

	/**
	 * Iterates through moving left graphics
	 * 
	 * @param
	 */
	public void switchLeftGraphic() {
		if (this.leftIndex == this.leftGraphics.size() - 1) {
			this.leftIndex = 0;
		} else {
			this.leftIndex++;
		}
		this.currentGraphic = this.leftGraphics.get(this.leftIndex);
	}

	/**
	 * Iterates through fall graphics
	 * 
	 * @param
	 */
	public void switchFallGraphic() {
		if (this.fallIndex == this.fallGraphics.size() - 1) {
			this.fallIndex = 0;
		} else {
			this.fallIndex++;
		}
		this.currentGraphic = this.fallGraphics.get(this.fallIndex);
	}

	/**
	 * Switches jump graphic based on the direction the sprite is facing
	 * 
	 * @param
	 */
	public void switchJumpGraphic() {
		if (this.xVel > 0) {
			this.jumpIndex = 0;
		} else if (this.xVel < 0) {
			this.jumpIndex = 1;
		}
		this.currentGraphic = this.jumpGraphics.get(this.jumpIndex);
	}

	@Override
	public Image getCurrentGraphic() {
		return this.currentGraphic;
	}

	/**
	 * Abstract methods to be implement by subclasses
	 */

	public abstract void setGraphics();

	public abstract void wallHit(int newXPos);

	public abstract void die();

	public abstract void hitProj();

	public abstract boolean handleCollision(Player bub);

}
