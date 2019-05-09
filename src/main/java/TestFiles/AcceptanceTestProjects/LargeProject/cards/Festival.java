package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Festival extends Card {

	@Override
	public Set<CardType> getType() {
		Set<CardType> toReturn = new HashSet<CardType>();
		toReturn.add(CardType.ACTION);
		return toReturn;
	}

	@Override
	public int getCost() {
		return GameConstants.FESTIVALCOST;
	}

	@Override
	public int getCoinsAdded() {
		return GameConstants.FESTIVALCOINSADDED;
	}

	@Override
	public int getActionsAdded() {
		return GameConstants.FESTIVALACTIONSADDED;
	}

	@Override
	public int getBuysAdded() {
		return GameConstants.FESTIVALBUYSADDED;
	}

	@Override
	public String getName() {
		return GameConstants.FESTIVALNAME;
	}
}
