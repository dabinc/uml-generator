package TestFiles.AcceptanceTestProjects.LargeProject.states;

import java.util.ArrayList;
import java.util.List;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;
import TestFiles.AcceptanceTestProjects.LargeProject.gameComponents.Player;
import TestFiles.AcceptanceTestProjects.LargeProject.gameComponents.Supply;

public class Turn {
	Player player;
	List<Player> subsequentPlayers;
	int buys;
	int actions;
	int coins;
	TurnState state;
	Supply supplyPiles;
	public List<Card> playArea;

	public Turn(Player player, Supply supplyPiles, List<Player> subsequentPlayers) {
		this.player = player;
		this.buys = 1;
		this.actions = 1;
		this.coins = 0;
		this.state = new TurnActionState();
		this.supplyPiles = supplyPiles;
		this.playArea = new ArrayList<>();
		this.subsequentPlayers = subsequentPlayers;
	}

	public void run() {
		this.state.run(this);
	}

	public Class<? extends TurnState> getCurrentStateType() {
		return this.state.getClass();
	}

	public void trashMostRecentlyPlayedCard() {
		if (!playArea.isEmpty()) {
			playArea.remove(playArea.size() - 1);
		}
	}

}
