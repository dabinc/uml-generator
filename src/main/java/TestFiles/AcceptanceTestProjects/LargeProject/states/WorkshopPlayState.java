package TestFiles.AcceptanceTestProjects.LargeProject.states;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;

public class WorkshopPlayState extends CardPlayState {

	public void run(Turn turn) {

		int coins = 4;
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
