package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Gardens extends Card {

	@Override
	public Set<CardType> getType() {
		Set<CardType> toReturn = new HashSet<CardType>();
		toReturn.add(CardType.VICTORY);
		return toReturn;
	}

	@Override
	public int getCost() {
		return GameConstants.GARDENSCOST;
	}

	@Override
	public String getName() {
		return GameConstants.GARDENSNAME;
	}

	@Override
	public String getText() {
		return GameConstants.GARDENSTEXT;
	}

}
