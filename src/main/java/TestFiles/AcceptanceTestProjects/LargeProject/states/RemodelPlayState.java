package TestFiles.AcceptanceTestProjects.LargeProject.states;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;

public class RemodelPlayState extends CardPlayState {

	@Override
	public void run(Turn turn) {
		Card card = turn.player.chooseCardFromHand("guiActionPhase", turn.actions, turn.buys,
				turn.coins);
		turn.player.trashCardFromHand(card.getClass());
		int coins = card.getCost() + 2;

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