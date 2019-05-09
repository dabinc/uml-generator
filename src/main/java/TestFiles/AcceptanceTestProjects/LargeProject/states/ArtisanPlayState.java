package TestFiles.AcceptanceTestProjects.LargeProject.states;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;

public class ArtisanPlayState extends CardPlayState {
	public void run(Turn turn) {
		int coins = 5;
		Card card = null;
		while (true) {
			card = turn.player.forcedBuy(turn.supplyPiles, "guiActionPhase", coins);

			if (card.getCost() <= coins) {
				turn.player.gainCardToHand(card);
				turn.supplyPiles.decrementPile(card);
				break;
			}

		}
		card = turn.player.chooseCardFromHand("guiActionPhase", turn.actions, turn.buys,
				turn.coins);
		turn.player.placeOnDrawPile(card);
		turn.player.trashCardFromHand(card.getClass());

	}
}
