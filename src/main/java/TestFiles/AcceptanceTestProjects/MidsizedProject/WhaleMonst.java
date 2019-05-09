package TestFiles.AcceptanceTestProjects.MidsizedProject;

/**
 * Type of monster that cannot shoot bubbles or fireballs. Takes in
 * an image and iterates through the list to make it appear as if it is moving.
 * Like other monsters, movement is random and collisions are handled by each
 * object.
 * 
 * @author Hailey Heidecker & Elle Nowakowski
 * 
 */
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WhaleMonst extends Monster {

	public WhaleMonst(int x, int y, Level level) {
		super(x, y, level);
		setGraphics();
		this.shooter = false;
	}

	/**
	 * Sets appropriate graphics for whale monsters
	 * 
	 * @param
	 */
	@Override
	public void setGraphics() {
		try {
			for (int i = 3; i <= 23; i++) {
				this.rightGraphics.add(ImageIO.read(new File("graphics/WhaleForwardFacing" + i / 3 + ".png")));
			}
			for (int i = 3; i <= 23; i++) {
				this.leftGraphics.add(ImageIO.read(new File("graphics/WhaleBackwardsFacing" + i / 3 + ".png")));
			}
			for (int i = 0; i < 3; i++) {
				this.fallGraphics.add(ImageIO.read(new File("graphics/WhaleForwardFalling.png")));
			}
			for (int i = 0; i < 3; i++) {
				this.fallGraphics.add(ImageIO.read(new File("graphics/WhaleBackwardsFalling.png")));
			}
			this.jumpGraphics.add(ImageIO.read(new File("graphics/WhaleForwardJumping.png")));
			this.jumpGraphics.add(ImageIO.read(new File("graphics/WhaleBackwardsJumping.png")));
			this.defaultGraphic = ImageIO.read(new File("graphics/WhaleForwardFacing1.png"));
			this.trappedGraphic = ImageIO.read(new File("graphics/WhaleTrappedInBubble.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
