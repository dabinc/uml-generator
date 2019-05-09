package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * 
 * Adds KeyListeners to Game, updates the screen, and calls functions that pause
 * the game and update the screen.
 * 
 * @author
 *
 */
@SuppressWarnings("serial")
public class GameComponent extends JComponent {

	private int currentLevelIndex;
	private Level currentLevel;
	private int score;
	private boolean isTwoPlayer;
	private Player player1, player2;

	private Image startScreen;
	private Image gameOver;
	private Image instructions;

	public GameComponent() {
		setFocusable(true);
		try {
			this.startScreen = ImageIO.read(new File("graphics/StartScreen.png"));
			this.gameOver = ImageIO.read(new File("graphics/GameOverScreen.png"));
			this.instructions = ImageIO.read(new File("graphics/instructions.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Updates the current game screen
	 * 
	 * @param
	 */
	public void updateScreen(int currentLevelIndex, Level currentLevel, int score, boolean isTwoPlayer, Player player1,
			Player player2) {
		this.currentLevel = currentLevel;
		this.currentLevelIndex = currentLevelIndex;
		this.score = score;
		this.isTwoPlayer = isTwoPlayer;
		this.player1 = player1;
		this.player2 = player2;
		this.repaint();
	}

	/**
	 * Draws current game level and all it's components
	 * 
	 * @param
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, 2000, 1500);
		drawCurrentLevel(g2);
	}

	private void drawCurrentLevel(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Consolas", Font.PLAIN, 30));

		if (this.currentLevelIndex == 0) {
			g2.drawImage(this.startScreen, 160, 175, 800, 800, null);
			g2.drawRect(800, 340, 250, 50);
			g2.drawRect(800, 400, 250, 50);
			g2.drawRect(800, 460, 250, 50);
			g2.drawString("Single Player", 815, 375);
			g2.drawString("Two Player", 840, 435);
			g2.drawString("Instructions", 820, 495);
		} else if (this.currentLevelIndex == 13) {
			g2.drawImage(this.gameOver, 200, 150, 700, 700, null);
			g2.drawString("Final Score: " + this.score, 440, 40);
			g2.drawRect(850, 340, 185, 50);
			g2.drawString("New Game", 875, 375);
		} else if (this.currentLevelIndex == 14) {
			g2.drawImage(this.instructions, 160, 150, 800, 800, null);
			g2.drawRect(850, 340, 200, 50);
			g2.drawString("Return", 900, 375);
		} else {
			if (!this.isTwoPlayer) {
				g2.drawString("Lives: " + this.player1.lives, 100, 40);
			} else {
				g2.drawString("Lives: P1: " + this.player1.lives + " P2: " + this.player2.lives, 60, 40);
			}
			g2.drawString("Level: " + this.currentLevelIndex, 300, 40);
			g2.drawString("Score: " + this.score, 700, 40);
			g2.drawString("Time: " + GameTimer.getInstance().getTime().getSeconds(), 900, 40);
		}
		drawLevel(g2);
	}

	private void drawLevel(Graphics2D g2) {
		if (this.currentLevel != null) {
			for (Block block : this.currentLevel.getBlocks()) {
				drawBlock(block, g2);
			}
			for (Sprite sprite : this.currentLevel.getSprites()) {
				drawSprite(sprite, g2);
			}
			for (Projectile projectile : this.currentLevel.getProjectiles()) {
				drawProjectile(projectile, g2);
			}
			for (Fruit fruit : this.currentLevel.getFruits()) {
				drawFruit(fruit, g2);
			}
		}
	}

	private void drawBlock(Block block, Graphics2D g2) {
		g2.drawImage(block.getCurrentGraphic(), block.x, block.y, Block.BLOCK_SIZE, Block.BLOCK_SIZE, null);
	}

	private void drawSprite(Sprite sprite, Graphics2D g2) {
		g2.drawImage(sprite.getCurrentGraphic(), sprite.xLocation, sprite.yLocation, Sprite.SPRITE_SIZE,
				Sprite.SPRITE_SIZE, null);
	}

	private void drawProjectile(Projectile projectile, Graphics2D g2) {
		g2.drawImage(projectile.getCurrentGraphic(), projectile.xLocation, projectile.yLocation, Projectile.PROJ_SIZE,
				Projectile.PROJ_SIZE, null);
	}

	private void drawFruit(Fruit fruit, Graphics2D g2) {
		g2.drawImage(fruit.getCurrentGraphic(), fruit.getXLocation(), fruit.getYLocation(), Fruit.FRUIT_SIZE,
				Fruit.FRUIT_SIZE, null);
	}

	/**
	 * Sets size of component
	 * 
	 * @param
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1140, 1020);
	}
}
