package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Gold extends Card {

	@Override
	public int getCoinsAdded() {
		return GameConstants.GOLDCOINSADDED;
	}

	@Override
	public int getCost() {
		return GameConstants.GOLDCOST;
	}

	@Override
	public Set<CardType> getType() {
		Set<CardType> toReturn = new HashSet<CardType>();
		toReturn.add(CardType.TREASURE);
		return toReturn;
	}

	@Override
	public String getName() {
		return GameConstants.GOLDNAME;
	}

}
