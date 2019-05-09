package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * Takes a player and controls its graphics based on his direction. Keeps track
 * of player 1 lives, handles him dying, and updates his graphics. Also his
 * collisions are handled in this class.
 * 
 * @author
 *
 */
public class Player extends Sprite {

	int lives;
	boolean direction;
	boolean hasBoomerang = true;
	String playerType;

	public Player(int x, int y, String playerType) {
		super(x, y);
		setType(playerType);
		setGraphics();
		this.lives = 5;
		this.enemy = false;
	}

	/**
	 * Player moves to the left with the appropriate graphic
	 * 
	 * @param
	 */
	public void left() {
		switchLeftGraphic();
		this.direction = false;
		this.xVel = -1.25 * Sprite.NORM_SPEED;
	}

	/**
	 * Player moves to the right with the appropriate graphic
	 * 
	 * @param
	 */
	public void right() {
		this.switchRightGraphic();
		this.direction = true;
		this.xVel = 1.25 * Sprite.NORM_SPEED;
	}

	/**
	 * Stops the players movement and sets the appropriate graphic
	 * 
	 * @param
	 */
	public void stop() {
		this.xVel = 0;
		if (this.direction) {
			this.rightIndex = 0;
			this.currentGraphic = this.rightGraphics.get(this.rightIndex);
		} else {
			this.leftIndex = 0;
			this.currentGraphic = this.leftGraphics.get(this.leftIndex);
		}
	}

	/**
	 * Sets location of the hero to a given point
	 * 
	 * @param x
	 * @param y
	 */
	public void setLoc(int x, int y) {
		this.intitialX = x;
		this.intitialY = y;
		this.xLocation = x;
		this.yLocation = y;
		this.falling = true;
	}

	/**
	 * Prevents the hero from running through walls
	 * 
	 * @param
	 * @param
	 */
	@Override
	public void wallHit(int newXPos) {
		this.xLocation = newXPos;
		this.xVel = 0;
	}

	/**
	 * Decrements the players lives. If the lives reach zero, the player is removed
	 * from the level
	 * 
	 * @param
	 */
	@Override
	public void die() {
		this.hasBoomerang = true;
		if (this.lives <= 0) {
			this.remove = true;
		} else {
			this.lives--;
		}
	}

	/**
	 * Resets the players lives to 5
	 * 
	 * @param
	 */
	public void livesReset() {
		this.lives = 5;
	}

	/**
	 * If the player hits and enemy projectile, the player dies
	 * 
	 * @param
	 */
	@Override
	public void hitProj() {
		die();
	}

	/**
	 * Collisions with other players do not matter
	 * 
	 * @param
	 */
	@Override
	public boolean handleCollision(Player player) {
		return false;
	}

	/**
	 * Sets the graphics for the player
	 * 
	 * @param
	 */
	@Override
	public void setGraphics() {
		try {
			for (int i = 3; i <= 32; i++) {
				this.rightGraphics
						.add(ImageIO.read(new File("graphics/" + playerType + "ForwardFacing" + i / 3 + ".png")));
			}
			for (int i = 3; i <= 32; i++) {
				this.leftGraphics
						.add(ImageIO.read(new File("graphics/" + playerType + "BackwardsFacing" + i / 3 + ".png")));
			}
			for (int i = 3; i <= 8; i++) {
				this.fallGraphics.add(ImageIO.read(new File("graphics/" + playerType + "Falling" + i / 3 + ".png")));
			}
			this.jumpGraphics.add(ImageIO.read(new File("graphics/" + playerType + "ForwardJumping.png")));
			this.jumpGraphics.add(ImageIO.read(new File("graphics/" + playerType + "BackwardsJumping.png")));
			this.defaultGraphic = ImageIO.read(new File("graphics/" + playerType + "ForwardFacing1.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public void restoreBoomerang() {
		this.hasBoomerang = true;
	}

	public void setType(String type) {
		this.playerType = type;
	}
}
