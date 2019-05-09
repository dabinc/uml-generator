package TestFiles.AcceptanceTestProjects.LargeProject.states;

import java.util.Optional;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;
import TestFiles.AcceptanceTestProjects.LargeProject.gameComponents.Player;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;

public class TurnActionState extends TurnState {

	private Turn turn;
	private Player player;

	@Override
	public void run(Turn turn) {
		this.turn = turn;
		this.player = turn.player;

		while (true) {
			Optional<Card> potentialCard = this.player.chooseCardToPlay("guiActionPhase", turn.actions, turn.buys,
					turn.coins);
			if (potentialCard.isPresent()) {
				Card card = potentialCard.get();
				if (card.getType().contains(CardType.ACTION)) {
					if (this.turn.actions > 0) {
						this.handleCard(card);
						this.turn.actions--;
					}

				} else {
					this.handleCard(card);
				}
			} else {
				break;
			}
		}

		this.turn.state = new TurnBuyState();
		this.turn.run();
	}

	private void handleCard(Card card) {

		this.turn.actions += card.getActionsAdded();
		this.turn.buys += card.getBuysAdded();
		this.turn.coins += card.getCoinsAdded();
		int cardsAdded = card.getCardsAdded();
		for (int i = 0; i < cardsAdded; i++) {
			this.player.drawACard();
		}

		this.turn.playArea = this.player.playCard(card, this.turn.playArea);
		this.turn.state = card.getPlayState();
		this.turn.run();

	}

}
