package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Game takes a score, all of the level info, screen images, players, and the
 * type of game being played (single or two player). This is where levels are
 * iterated through/created and the game over and start screens are created.
 * Levels are drawn, the score is updated along with the screen, and it is made
 * possible to go up and down between levels. This class also allows for the
 * player to pause the game.
 * 
 * @author
 *
 */
public class Game {
	private static final int TOTAL_LEVELS = 14;
	private static final long TIMER_BONUS_SECONDS = 30;
	private static final int TIMER_BONUS_VALUE = 15;

	private int score;
	private int currentLevelIndex;
	private Level currentLevel;
	private List<Level> levels = new ArrayList<>();
	private Player player1, player2;
	private boolean twoPlayer;
	boolean paused;
	private GameComponent gameComponent;

	public Game(Player player1, Player player2, GameComponent gameComponent) {
		this.score = 0;
		this.player1 = player1;
		this.player2 = player2;
		this.gameComponent = gameComponent;
		for (int i = 0; i <= TOTAL_LEVELS; i++) {
			this.levels.add(new Level("gamelevels/level" + i, this.player1, this.player2));
		}
		this.currentLevelIndex = 0;
		this.currentLevel = this.levels.get(this.currentLevelIndex);
	}

	public boolean isPaused() {
		return this.paused;
	}
	
	private boolean shouldGetTimerBonus(){
		return GameTimer.getInstance().getTime().getSeconds() < TIMER_BONUS_SECONDS;
	}

	/**
	 * Increments players score
	 * 
	 * @param
	 */
	public void updateScore() {
		this.score += this.currentLevel.getScore();
	}

	private void updateAndResetLevel() {
		this.currentLevel = this.levels.get(this.currentLevelIndex);
		this.currentLevel.resetLevel();
	}

	/**
	 * Switches to the next level, reseting it as it does so
	 * 
	 * @param
	 */
	public void nextLevel() {
		if (this.currentLevelIndex < TOTAL_LEVELS - 1) {
			this.currentLevelIndex++;
			updateAndResetLevel();
		}
	}

	/**
	 * Switches to the previous level, reseting it as it does so
	 * 
	 * @param
	 */
	public void previousLevel() {
		if (this.currentLevelIndex > 0) {
			this.currentLevelIndex--;
			updateAndResetLevel();
		}
	}

	/**
	 * Updates all objects on the screen. Also checks if the current level is
	 * complete or the game is over.
	 * 
	 * @param
	 */
	public void updateScreen() {
		this.currentLevel.updateSprites();
		this.currentLevel.updateItems();
		updateScore();
		checkCollision();

		if (this.currentLevel.levelComplete() && this.currentLevelIndex != 0) {
			if(shouldGetTimerBonus()){
				this.score += TIMER_BONUS_VALUE;
			}
			nextLevel();
		}

		if ((!this.twoPlayer && this.player1.lives == 0)
				|| (this.twoPlayer && this.player1.lives == 0 && this.player2.lives == 0)) {
			gameOver();
		}

		this.gameComponent.updateScreen(this.currentLevelIndex, this.currentLevel, this.score, this.paused,
				this.player1, this.player2);
	}

	/**
	 * Checks collisions in the current level
	 * 
	 * @param
	 */
	public void checkCollision() {
		this.currentLevel.checkCollision();
	}

	/**
	 * Switches current level to the game over screen
	 * 
	 * @param
	 */
	public void gameOver() {
		this.currentLevelIndex = 13;
		this.currentLevel = this.levels.get(this.currentLevelIndex);
	}

	/**
	 * Adds a bubble to the current level at the given location and moving in the
	 * given direction
	 * 
	 * @param xLocation, yLocation, direction
	 */
	public void addBubble(int xLocation, int yLocation, boolean direction) {
		this.currentLevel.addBubble(xLocation, yLocation, direction);
	}

	public void addBoomerang(Player player) {
		this.currentLevel.addBoomerang(player);
	}

	/**
	 * Resets the current level to the state it was when created
	 * 
	 * @param
	 */
	public void resetLevel() {
		this.currentLevel.resetLevel();
	}

	/**
	 * Starts a new game
	 * 
	 * @param
	 */
	public void newGame() {
		this.currentLevelIndex = 0;
		this.currentLevel = this.levels.get(this.currentLevelIndex);
		this.score = 0;
		this.player1.livesReset();
		this.player2.livesReset();
	}

	/**
	 * Gets the location of the click with respect to various "buttons" on the level
	 * screen
	 * 
	 * @param click
	 */
	public int clickLoc(Point click) {
		if (this.currentLevelIndex == 0) {
			if (new Rectangle(800, 340, 250, 50).contains(click)) {
				return 1;
			} else if (new Rectangle(800, 400, 250, 50).contains(click)) {
				return 2;
			} else if (new Rectangle(800, 460, 250, 50).contains(click)) {
				return 3;
			}
		}
		if (this.currentLevelIndex == 13 || this.currentLevelIndex == 14) {
			if (new Rectangle(800, 340, 250, 50).contains(click)) {
				return 4;
			}
		}
		return 0;
	}

	/**
	 * Sets game mode to single player for game and all levels
	 * 
	 * @param
	 */
	public void singlePlayer() {
		this.twoPlayer = false;
		for (Level level : this.levels) {
			level.setSinglePlayer();
		}
		nextLevel();
	}

	/**
	 * Sets game mode to two player for game and all levels
	 * 
	 * @param
	 */
	public void twoPlayer() {
		this.twoPlayer = true;
		for (Level level : this.levels) {
			level.setTwoPlayer();
		}
		nextLevel();
	}

	/**
	 * Displays game instructions
	 * 
	 * @param
	 */
	public void instructions() {
		this.currentLevelIndex = 14;
		this.currentLevel = this.levels.get(this.currentLevelIndex);

	}

	/**
	 * Pauses game
	 * 
	 * @param
	 */
	public void pause() {
		this.paused = !this.paused;
	}
}
