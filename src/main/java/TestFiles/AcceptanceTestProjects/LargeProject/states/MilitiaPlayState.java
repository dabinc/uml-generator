package TestFiles.AcceptanceTestProjects.LargeProject.states;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Moat;
import TestFiles.AcceptanceTestProjects.LargeProject.gameComponents.Player;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class MilitiaPlayState extends CardPlayState {

	public void run(Turn turn) {
		for (Player player : turn.subsequentPlayers) {
			boolean ignore = false;
			if (player.hasCardInHand(Moat.class)) {
				ignore = player.promptYesNo(GameConstants.MOATPROMPT);
			}
			while (!ignore && player.sizeOfHand() > 3) {
				Card card = player.chooseCardFromHand(GameConstants.DISCARDPROMPTKEY, turn.actions, turn.buys,
						turn.coins);
				player.discardCardFromHand(card.getClass());
			}
		}

	}
}
