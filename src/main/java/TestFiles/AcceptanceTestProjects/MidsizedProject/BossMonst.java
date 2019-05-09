package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BossMonst extends Monster {

	private int life;
	private static final int DEFAULT_LIFE = 3;
	private static final int BOSS_SPEED = 25;

	public BossMonst(int x, int y, Level level) {
		super(x, y, level);
		this.life = DEFAULT_LIFE;

		if (Math.random() < .5) { 
			this.xVel = -BOSS_SPEED;
		} else {
			this.xVel = BOSS_SPEED;
		}

		setGraphics();
	}

	@Override
	public void setGraphics() {
		try {
			this.rightGraphics.add(ImageIO.read(new File("graphics/Boss.png")));
			this.leftGraphics.add(ImageIO.read(new File("graphics/Boss.png")));
			this.fallGraphics.add(ImageIO.read(new File("graphics/Boss.png")));
			this.jumpGraphics.add(ImageIO.read(new File("graphics/Boss.png")));
			this.defaultGraphic = ImageIO.read(new File("graphics/Boss.png"));
			this.trappedGraphic = ImageIO.read(new File("graphics/MonsterTrappedInBubble.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void move() {
		if (!this.trapped) {
			super.move();
			switchDirection();
			shouldJump();
		}

		else {
			this.yLocation += this.yVel;
			if (System.currentTimeMillis() - this.startTime > MAX_TIME) {
				// Boss monster moves slightly faster
				this.xVel = BOSS_SPEED;
				this.trapped = false;
			}
		}
	}

	@Override
	public void hitProj() {
		this.life--;
		if (!this.trapped && this.life < 0) {
			bubbleTrap();
		}
	}

}
