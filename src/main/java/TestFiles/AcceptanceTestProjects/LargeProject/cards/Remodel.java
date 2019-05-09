package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.Set;
import java.util.TreeSet;

import TestFiles.AcceptanceTestProjects.LargeProject.states.CardPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.states.RemodelPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Remodel extends Card {

	@Override
	public Set<CardType> getType() {
		TreeSet<CardType> ans = new TreeSet<CardType>();
		ans.add(CardType.ACTION);
		return ans;
	}

	@Override
	public int getCost() {
		return GameConstants.REMODELCOST;
	}

	@Override
	public CardPlayState getPlayState() {
		return new RemodelPlayState();
	}

	@Override
	public String getName() {
		return GameConstants.REMODELNAME;
	}

	@Override
	public String getText() {
		return GameConstants.REMODELTEXT;
	}

}
