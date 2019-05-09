package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.Set;
import java.util.TreeSet;

import TestFiles.AcceptanceTestProjects.LargeProject.states.CardPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.states.WorkshopPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Workshop extends Card {

	@Override
	public Set<CardType> getType() {
		TreeSet<CardType> ans = new TreeSet<CardType>();
		ans.add(CardType.ACTION);
		return ans;
	}

	@Override
	public int getCost() {
		return GameConstants.WORKSHOPCOST;
	}

	@Override
	public String getName() {
		return GameConstants.WORKSHOPNAME;
	}

	public String getText() {
		return GameConstants.WORKSHOPTEXT;
	}

	public CardPlayState getPlayState() {
		return new WorkshopPlayState();
	}
}
