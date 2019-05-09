package TestFiles.AcceptanceTestProjects.LargeProject.states;

import TestFiles.AcceptanceTestProjects.LargeProject.gameComponents.Player;

public class CouncilRoomPlayState extends CardPlayState {

	public void run(Turn turn) {
		for (Player player2 : turn.subsequentPlayers) {
			player2.drawACard();
		}
	}

}
