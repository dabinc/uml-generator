package TestFiles.AcceptanceTestProjects.LargeProject.states;

import TestFiles.AcceptanceTestProjects.LargeProject.gameComponents.Player;

public class ChancellorPlayState extends CardPlayState {
	
	@Override
	public void run(Turn turn){
		Player player = turn.player;
		
		if(player.promptYesNo("chancellorPrompt")){
			player.discardDrawPile();
		}
	}

}
