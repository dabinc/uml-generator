package TestFiles.AcceptanceTestProjects.MidsizedProject;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Boomerang extends Projectile {
	boolean changedDirection = false;
	private Player player;
	int distance;

	public Boomerang(int x, int y, boolean direction) {
		super(x, y, direction);
		try {
			this.graphic = ImageIO.read(new File("graphics/boomerang.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Boomerang(Player player) {
		this(player.xLocation, player.yLocation, player.direction);
		this.player = player;
	}

	@Override
	public void finishRemoval() {
		this.player.restoreBoomerang();
	}

	@Override
	public void move() {
		if (!changedDirection) {
			checkChangeDirection();
		}
		if (changedDirection) {
			this.xLocation -= this.xVel;
		} else {
			this.xLocation += this.xVel;
		}
	}

	private void checkChangeDirection() {
		this.changedDirection = Math.abs(this.xLocation - this.startXLoc) > BOOMERANG_MAX_DIST;
	}

	@Override
	public void hitCeiling(Ceiling ceiling) {
		// Does nothing
	}

	@Override
	public boolean checkCollision(Sprite sprite) {
		if (this.boundingBox().intersects(sprite.boundingBox())) {
			if (sprite.enemy) {
				sprite.hitProj();
				changedDirection = true;
			}
		}
		return false;
	}
}
