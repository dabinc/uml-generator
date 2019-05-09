package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * Makes the fire monster when given its level, position, and its shooter
 * capabilities. Monster is read as an image and attached to the frame and
 * changes images as time goes on and the monster moves. if the image does not
 * exist, then an exception is thrown.
 * 
 * @author
 *
 */
public class FireMonst extends Monster {
	public FireMonst(int x, int y, Level level) {
		super(x, y, level);
		this.level = level;
		this.shooter = true;
	}

	/**
	 * Sets the various graphics for fire shooting monsters
	 * 
	 * @param
	 */
	@Override
	public void setGraphics() {
		try {
			for (int i = 3; i <= 14; i++) {
				this.rightGraphics.add(ImageIO.read(new File("graphics/MonsterForwardFacing" + i / 3 + ".png")));
			}

			for (int i = 3; i <= 14; i++) {
				this.leftGraphics.add(ImageIO.read(new File("graphics/MonsterBackwardsFacing" + i / 3 + ".png")));
			}

			for (int i = 0; i < 3; i++) {
				this.fallGraphics.add(ImageIO.read(new File("graphics/MonsterForwardFalling.png")));
			}

			for (int i = 0; i < 3; i++) {
				this.fallGraphics.add(ImageIO.read(new File("graphics/MonsterBackwardsFalling.png")));
			}

			this.jumpGraphics.add(ImageIO.read(new File("graphics/MonsterForwardJumping.png")));
			this.jumpGraphics.add(ImageIO.read(new File("graphics/MonsterBackwardsJumping.png")));
			this.defaultGraphic = ImageIO.read(new File("graphics/MonsterForwardFacing1.png"));
			this.trappedGraphic = ImageIO.read(new File("graphics/MonsterTrappedInBubble.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
