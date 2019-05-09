package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Draws all of the blocks (walls, platforms, and ceilings) on the frame. To do
 * this, an image must be imported or an exception will be thrown.
 * 
 * Also checks collisions of the blocks with projectiles, fruit and sprites.
 * 
 * @author
 */

public abstract class Block implements Renderable {
	static final int BLOCK_SIZE = 60;
	private Image graphic;
	protected int x, y;

	public Block(int x, int y) {
		this.x = x;
		this.y = y;
		try {
			this.graphic = ImageIO.read(new File("graphics/Block.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public Rectangle boundingBox() {
		return new Rectangle(x, y, BLOCK_SIZE, BLOCK_SIZE);
	}

	@Override
	public Image getCurrentGraphic() {
		return this.graphic;
	}

	/**
	 * Abstract methods to be implemented by subclasses
	 */
	abstract void checkCollision(Projectile proj);

	abstract void checkCollision(Sprite sprite);

	abstract void checkCollision(Fruit fruit);

}
