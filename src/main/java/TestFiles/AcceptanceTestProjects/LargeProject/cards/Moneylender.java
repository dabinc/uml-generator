package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.states.CardPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.states.MoneylenderPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Moneylender extends Card {

	@Override
	public Set<CardType> getType() {
		HashSet<CardType> toReturn = new HashSet<CardType>();
		toReturn.add(CardType.ACTION);
		return toReturn;
	}

	@Override
	public int getCost() {
		return GameConstants.MONEYLENDERCOST;
	}

	@Override
	public CardPlayState getPlayState() {
		return new MoneylenderPlayState();
	}

	@Override
	public String getName() {
		return GameConstants.MONEYLENDERNAME;
	}

	@Override
	public String getText() {
		return GameConstants.MONEYLENDERTEXT;
	}

}
