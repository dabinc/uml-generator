package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.states.CardPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.states.ChancellorPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Chancellor extends Card {

	@Override
	public Set<CardType> getType() {
		HashSet<CardType> toReturn = new HashSet<CardType>();
		toReturn.add(CardType.ACTION);
		return toReturn;
	}

	@Override
	public int getCost() {
		return GameConstants.CHANCELLORCOST;
	}
	
	@Override
	public int getCoinsAdded() {
		return GameConstants.CHANCELLORCOINSADDED;
	}
	
	@Override
	public CardPlayState getPlayState() {
		return new ChancellorPlayState();
	}

	@Override
	public String getName() {
		return GameConstants.CHANCELLORNAME;
	}
	
	@Override
	public String getText() {
		return GameConstants.CHANCELLORTEXT;
	}

}
