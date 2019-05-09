package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Curse extends Card {

	@Override
	public int getVictoryValue() {
		return GameConstants.CURSEVICTORYVALUE;
	}

	@Override
	public int getCost() {
		return GameConstants.CURSECOST;
	}

	@Override
	public Set<CardType> getType() {
		Set<CardType> toReturn = new HashSet<CardType>();
		toReturn.add(CardType.CURSE);
		return toReturn;
	}

	@Override
	public String getName() {
		return GameConstants.CURSENAME;
	}

}
