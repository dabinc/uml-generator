package TestFiles.AcceptanceTestProjects.LargeProject.states;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;

public class FeastPlayState extends CardPlayState {

	@Override
	public void run(Turn turn) {
		turn.trashMostRecentlyPlayedCard();

		int coins = 5;
		Card card = null;

		while (true) {
			card = turn.player.forcedBuy(turn.supplyPiles, "guiActionPhase", coins);

			if (card.getCost() <= coins) {
				turn.player.gainCard(card);
				turn.supplyPiles.decrementPile(card);
				break;
			}
		}
	}

}
