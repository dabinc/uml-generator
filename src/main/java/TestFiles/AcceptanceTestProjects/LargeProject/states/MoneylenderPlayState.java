package TestFiles.AcceptanceTestProjects.LargeProject.states;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Copper;
import TestFiles.AcceptanceTestProjects.LargeProject.gameComponents.Player;

public class MoneylenderPlayState extends CardPlayState {

	@Override
	public void run(Turn turn){
		Player player = turn.player;
		
		if(player.promptYesNo("moneylenderPrompt") && player.trashCardFromHand(Copper.class)){
			turn.coins += 3;
		}
	}
	
}
