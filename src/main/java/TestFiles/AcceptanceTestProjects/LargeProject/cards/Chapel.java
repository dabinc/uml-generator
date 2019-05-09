package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.states.CardPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.states.ChapelPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Chapel extends Card {

	@Override
	public Set<CardType> getType() {
		HashSet<CardType> toReturn = new HashSet<CardType>();
		toReturn.add(CardType.ACTION);
		return toReturn;
	}

	@Override
	public int getCost() {
		return GameConstants.CHAPELCOST;
	}
	
	@Override
	public CardPlayState getPlayState(){
		return new ChapelPlayState();
	}

	@Override
	public String getName() {
		return GameConstants.CHAPELNAME;
	}
	
	@Override
	public String getText() {
		return GameConstants.CHAPELTEXT;
	}

}
