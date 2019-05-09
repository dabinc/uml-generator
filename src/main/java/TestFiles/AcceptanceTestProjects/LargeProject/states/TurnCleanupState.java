package TestFiles.AcceptanceTestProjects.LargeProject.states;

public class TurnCleanupState extends TurnState {

	@Override
	public void run(Turn turn) {
		turn.player.cleanup(turn.playArea);
	}

}
