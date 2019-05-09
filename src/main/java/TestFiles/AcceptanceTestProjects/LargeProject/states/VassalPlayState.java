package TestFiles.AcceptanceTestProjects.LargeProject.states;

import java.util.Optional;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;
import TestFiles.AcceptanceTestProjects.LargeProject.gameComponents.Player;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;

public class VassalPlayState extends CardPlayState {

	@Override
	public void run(Turn turn) {
		Player player = turn.player;
		Optional<Card> cardPossibility = player.discardTopCardOfDrawPile();

		if (!cardPossibility.isPresent()) {
			return;
		} else {
			Card card = cardPossibility.get();
			if (card.getType().contains(CardType.ACTION)) {
				if (player.promptYesNo("vassalPrompt")) {
					turn.state = card.getPlayState();
					turn.run();
				}
			}
		}
	}

}
