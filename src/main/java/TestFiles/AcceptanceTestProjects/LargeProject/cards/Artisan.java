package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.states.ArtisanPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.states.CardPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Artisan extends Card {

	@Override
	public Set<CardType> getType() {
		Set<CardType> ans = new HashSet<CardType>();
		ans.add(CardType.ACTION);
		return ans;
	}

	@Override
	public int getCost() {
		return GameConstants.ARTISANCOST;
	}

	@Override
	public String getName() {
		return GameConstants.ARTISANNAME;
	}

	public String getText() {
		return GameConstants.ARTISANTEXT;
	}

	public CardPlayState getPlayState() {
		return new ArtisanPlayState();
	}

}
