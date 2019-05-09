package TestFiles.AcceptanceTestProjects.LargeProject.gameComponents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.states.Turn;

public class Game {
	Player[] players;
	int currentPlayer;
	Supply supplyPiles;

	public Game(Supply supply, Player... players) throws IllegalArgumentException {
		if (players.length > 4 || players.length < 2) {
			throw new IllegalArgumentException();
		}
		this.players = players;
		this.supplyPiles = supply;
		this.currentPlayer = 0;
	}

	public Set<Player> runGame() {

		this.initializePlayers();

		while (true) {
			Turn currentTurn = this.makeNewTurn();
			currentTurn.run();

			if (this.supplyPiles.isGameOver()) {
				break;
			}

			this.endTurn();
		}

		return this.endGame();
	}

	private void initializePlayers() {
		for (int i = 0; i < players.length; i++) {
			players[i].drawNewHand();
		}
	}

	public Set<Player> endGame() {
		int maxPoints = 0;
		Set<Player> winners = new HashSet<>();

		for (int i = 0; i < this.players.length; i++) {
			if (players[i].getPoints() > maxPoints) {
				maxPoints = players[i].getPoints();
			}
		}
		boolean tieBreaker = false;
		if (this.currentPlayer == 0) {
			tieBreaker = true;
		}
		for (int i = 0; i < players.length; i++) {
			if (players[i].getPoints() == maxPoints && winners.isEmpty()) {
				winners.add(players[i]);
			} else if (i >= this.currentPlayer && this.players[i].getPoints() == maxPoints && !tieBreaker) {
				winners.clear();
				winners.add(players[i]);
				tieBreaker = true;
			} else if (players[i].getPoints() == maxPoints) {
				winners.add(players[i]);
			}

		}
		return winners;

	}

	public void endTurn() {
		this.currentPlayer++;
		this.currentPlayer = this.currentPlayer % this.players.length;
	}

	public Turn makeNewTurn() {
		ArrayList<Player> subsequentPlayers = new ArrayList<Player>();
		for (int i = this.currentPlayer + 1; i < players.length; i++) {
			subsequentPlayers.add(this.players[i]);
		}
		for (int i = 0; i < this.currentPlayer; i++) {
			subsequentPlayers.add(this.players[i]);
		}
		return new Turn(this.players[this.currentPlayer], this.supplyPiles, subsequentPlayers);
	}

}
