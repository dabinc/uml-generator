package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * Given a size and a fall speed, a fruit is added to the frame when a monster
 * bubble is popped. Like other objects, when it collides with a block,
 * collisions are checked and the fruit stops falling. When the fruit collides
 * with a hero, then the fruit disappears and the final score is updated.
 * 
 * @author
 *
 */
public class Fruit implements Renderable {
	public static final int FRUIT_SIZE = 30;
	private static final double FALL_SPEED = 15;
	boolean remove;
	boolean landed;
	boolean platformChecked;
	int points;
	private int xLocation;
	private int yLocation;
	private double yVel;
	private Image graphic;

	public Fruit(int x, int y) {
		this.xLocation = x;
		this.yLocation = y;
		this.yVel = FALL_SPEED;
		this.landed = false;
		setGraphic();
	}

	/**
	 * Causes fruit to fall
	 * 
	 * @param
	 */
	public void move() {
		this.yLocation += this.yVel;
	}

	/**
	 * Allows fruit to land on platforms rather than falling through
	 * 
	 * @param platform
	 */
	public void land(Platform platform) {
		this.yVel = 0;
		this.yLocation = platform.y - FRUIT_SIZE;
		this.landed = true;
	}

	/**
	 * Randomly sets graphic and score value for newly constructed fruit
	 * 
	 * @param
	 */
	public void setGraphic() {
		String filename;
		double fileNum = Math.random();

		if (fileNum < .25) {
			filename = "graphics/Orange.png";
			this.points = 10;
		} else if (fileNum >= .25 && fileNum < .5) {
			filename = "graphics/Raspberry.png";
			this.points = 20;
		} else if (fileNum >= .5 && fileNum < .75) {
			filename = "graphics/Strawberry.png";
			this.points = 30;
		} else {
			filename = "graphics/Banana.png";
			this.points = 40;
		}
		try {
			this.graphic = ImageIO.read(new File(filename));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Checks collision between the player and fruit. If true, the fruit is
	 * "collected" and the points associated with the fruit are returned to be added
	 * to the score
	 * 
	 * @param bub
	 */
	public boolean handleCollision(Player player) {
		if (boundingBox().intersects(player.boundingBox()) && this.landed) {
			this.remove = true;
			return true;
		}
		return false;
	}

	/**
	 * Returns a rectangle representing a bounding box of the fruit
	 * 
	 * @param
	 */
	public Rectangle boundingBox() {
		return new Rectangle(this.xLocation, this.yLocation, FRUIT_SIZE, FRUIT_SIZE);
	}

	@Override
	public Image getCurrentGraphic() {
		return this.graphic;
	}

	public int getXLocation() {
		return this.xLocation;
	}

	public int getYLocation() {
		return this.yLocation;
	}
}