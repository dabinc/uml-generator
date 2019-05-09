package TestFiles.AcceptanceTestProjects.LargeProject.states;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;
import TestFiles.AcceptanceTestProjects.LargeProject.gameComponents.Player;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class ChapelPlayState extends CardPlayState {

	@Override
	public void run(Turn turn) {
		Player player = turn.player;

		for (int i = 0; i < 4 && player.sizeOfHand() > 0; i++) {
			if (player.promptYesNo("chapelPrompt")) {
				Card toTrash = player.chooseCardFromHand(GameConstants.CHAPELPROMPTKEY, turn.actions, turn.buys, turn.coins);
				player.trashCardFromHand(toTrash.getClass());
			} else {
				break;
			}
		}
	}

}
