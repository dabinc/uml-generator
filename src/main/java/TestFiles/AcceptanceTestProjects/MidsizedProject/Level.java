package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Takes a file with Ws, Bs, Hs, hs, Es, and Ss to create a level. All of the
 * objects added to the level are added to an ArrayList. Players are added to
 * the level at the H or h positions, shooters at the S, whale monsters at the
 * E, platforms at B, walls at W, and ceilings at C. Score is also incremented
 * in this class. When levels are reset, everything is reset in the level.
 * 
 * @author
 *
 */
public class Level {

	private String file;
	private List<Sprite> sprites = new ArrayList<>();
	private List<Player> players = new ArrayList<>();
	private List<Block> blocks = new ArrayList<>();
	private List<Projectile> projectiles = new ArrayList<>();
	private List<Fruit> fruits = new ArrayList<>();
	private List<Object> toRemove = new ArrayList<>();
	private Player player1;
	private Player player2;
	private boolean twoPlayer;
	private int scoreIncrement;

	public Level(String fileName, Player player1, Player player2) {
		this.file = fileName;
		this.player1 = player1;
		this.player2 = player2;
		this.scoreIncrement = 0;
		constructLevel();
	}

	/**
	 * Reads level file and adds the necessary level components to the level
	 * 
	 */
	@SuppressWarnings("resource")
	public void constructLevel() {
		FileReader file1 = null;
		try {
			file1 = new FileReader(this.file);
		} catch (FileNotFoundException exception) {
			System.out.println("Level does not exist");
			exception.printStackTrace();
		}
		Scanner s = new Scanner(file1);

		int x = 0;
		int y = 60;

		while (s.hasNextLine()) {
			String levelRow = s.nextLine();
			for (int i = 0; i < levelRow.length(); i++) {
				switch (levelRow.charAt(i)) {
				case 'H':
					this.player1.setLoc(x, y);
					this.sprites.add(this.player1);
					this.players.add(this.player1);
					break;
				case 'h':
					if (this.twoPlayer) {
						this.player2.setLoc(x, y);
						this.sprites.add(this.player2);
						this.players.add(this.player2);
					}
					break;
				case 'B':
					Platform newPlatform = new Platform(x, y);
					this.blocks.add(newPlatform);
					break;
				case 'W':
					Wall newWall = new Wall(x, y);
					this.blocks.add(newWall);
					break;
				case 'C':
					Ceiling newCeiling = new Ceiling(x, y);
					this.blocks.add(newCeiling);
					break;
				case 'Z': 
					BossMonst bossMonst = new BossMonst(x, y, this);
					this.sprites.add(bossMonst);
					break;
				case 'S':
					FireMonst newShooter = new FireMonst(x, y, this);
					this.sprites.add(newShooter);
					break;
				case 'E':
					WhaleMonst newWhale = new WhaleMonst(x, y, this);
					this.sprites.add(newWhale);
					break;
				default:
					break;
				}
				x += 60;
			}
			x = 0;
			y += 60;
		}
	}

	public List<Block> getBlocks() {
		return this.blocks;
	}

	public List<Sprite> getSprites() {
		return this.sprites;
	}

	public List<Projectile> getProjectiles() {
		return this.projectiles;
	}

	public List<Fruit> getFruits() {
		return this.fruits;
	}

	/**
	 * Updates position of sprites
	 * 
	 * @param
	 */
	public void updateSprites() {
		for (Sprite sprite : this.sprites) {
			if (sprite.remove) {
				this.toRemove.add(sprite);
			}
			sprite.move();
			sprite.updateGraphics();
		}

		for (Object remove : this.toRemove) {
			this.sprites.remove(remove);
			this.players.remove(remove);
		}
	}

	/**
	 * Updates position of items and removes any popped bubbles
	 * 
	 * @param
	 */
	public void updateItems() {
		for (Projectile proj : this.projectiles) {
			if (proj.remove) {
				this.toRemove.add(proj);
			} else {
				proj.move();
			}
		}

		for (Fruit fruit : this.fruits) {
			if (fruit.remove) {
				this.toRemove.add(fruit);
			} else {
				fruit.move();
			}
		}

		for (Object remove : this.toRemove) {
			this.projectiles.remove(remove);
			this.fruits.remove(remove);
		}
	}

	/**
	 * Checks the collision between sprites and level components and handles them
	 * appropriately
	 * 
	 * @param
	 */
	public void checkCollision() {
		for (Sprite sprite : this.sprites) {
			for (Player player : this.players) {
				if (sprite.handleCollision(player) && player.lives != 0) {
					resetObj();
				}
			}
			for (Projectile proj : this.projectiles) {
				if (proj.checkCollision(sprite)) {
					resetObj();
				}
			}

			sprite.platformChecked = false;
			for (Block block : this.blocks) {
				block.checkCollision(sprite);
			}
		}

		for (Projectile proj : this.projectiles) {
			for (Block block : this.blocks) {
				block.checkCollision(proj);
			}
			for (Projectile proj2 : this.projectiles) {
				proj.checkCollision(proj2);
			}
		}

		for (Fruit fruit : this.fruits) {
			for (Player player : this.players) {
				if (fruit.handleCollision(player)) {
					this.scoreIncrement += fruit.points;
				}
			}
			fruit.platformChecked = false;
			for (Block block : this.blocks) {
				block.checkCollision(fruit);
			}
		}
	}

	/**
	 * Resets sprite position and clears projectiles from the screen
	 * 
	 * @param
	 */
	private void resetObj() {
		for (Sprite sprite : this.sprites) {
			sprite.resetLoc();
		}
		this.projectiles = new ArrayList<>();
		GameTimer.getInstance().startTimer();
	}

	/**
	 * Adds bubbles to the level when constructed
	 * 
	 * @param xLocation
	 * @param yLocation
	 * @param bubbleType
	 */
	public void addBubble(int xLocation, int yLocation, boolean direction) {
		this.projectiles.add(new Bubble(xLocation, yLocation, direction));

	}

	/**
	 * Adds fire ball to the level
	 * 
	 * @param xLocation
	 * @param yLocation
	 * @param direction
	 */
	public void addFireBall(int xLocation, int yLocation, boolean direction) {
		this.projectiles.add(new Fireball(xLocation, yLocation, direction));
	}

	/**
	 * Adds fruit to the level
	 * 
	 * @param fruit
	 */
	public void addFruit(Fruit fruit) {
		this.fruits.add(fruit);
	}

	/**
	 * Indicates the level is complete if all the monsters have been killed
	 * 
	 * @param
	 */
	public boolean levelComplete() {
		if (onlyHeros() && this.fruits.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the sprites remaining in the level are only heros
	 * 
	 * @param
	 */
	private boolean onlyHeros() {
		if (this.sprites.size() == this.players.size()) {
			return true;
		}
		return false;
	}

	/**
	 * Resets the level to the state in which it was created
	 * 
	 * @param
	 */
	public void resetLevel() {
		this.sprites = new ArrayList<>();
		this.players = new ArrayList<>();
		this.blocks = new ArrayList<>();
		this.projectiles = new ArrayList<>();
		this.toRemove = new ArrayList<>();
		this.scoreIncrement = 0;
		GameTimer.getInstance().startTimer();
		constructLevel();
	}

	/**
	 * Returns the score for level
	 * 
	 * @param
	 */
	public int getScore() {
		int score = this.scoreIncrement;
		this.scoreIncrement = 0;
		return score;
	}

	/**
	 * Sets game mode to single player
	 * 
	 * @param
	 */
	public void setSinglePlayer() {
		this.twoPlayer = false;
	}

	/**
	 * Sets game mode to two player
	 * 
	 * @param
	 */
	public void setTwoPlayer() {
		this.twoPlayer = true;
	}

	public void addBoomerang(Player player) {
		this.projectiles.add(new Boomerang(player));
	}
}
