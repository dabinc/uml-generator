package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Smithy extends Card {

	@Override
	public Set<CardType> getType() {
		Set<CardType> toReturn = new HashSet<CardType>();
		toReturn.add(CardType.ACTION);
		return toReturn;
	}

	@Override
	public int getCost() {
		return GameConstants.SMITHYCOST;
	}

	@Override
	public int getCardsAdded() {
		return GameConstants.SMITHYCARDSADDED;
	}

	@Override
	public String getName() {
		return GameConstants.SMITHYNAME;
	}

}
