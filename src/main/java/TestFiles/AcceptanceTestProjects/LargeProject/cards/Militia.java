package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.Set;
import java.util.TreeSet;

import TestFiles.AcceptanceTestProjects.LargeProject.states.CardPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.states.MilitiaPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Militia extends Card {

	@Override
	public Set<CardType> getType() {
		Set<CardType> set = new TreeSet<CardType>();
		set.add(CardType.ACTION);
		set.add(CardType.ATTACK);
		return set;
	}

	@Override
	public int getCost() {
		return GameConstants.MILITIACOST;
	}

	@Override
	public String getName() {
		return GameConstants.MILITIANAME;
	}

	public int getCoinsAdded() {
		return GameConstants.MILITIACOINSADDED;
	}

	public String getText() {
		return GameConstants.MILITIATEXT;
	}

	public CardPlayState getPlayState() {
		return new MilitiaPlayState();
	}

}
