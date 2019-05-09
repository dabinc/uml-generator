package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.HashSet;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.states.CouncilRoomPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class CouncilRoom extends Card {

	@Override
	public Set<CardType> getType() {
		Set<CardType> set = new HashSet<CardType>();
		set.add(CardType.ACTION);
		return set;
	}

	@Override
	public int getCost() {
		return GameConstants.COUNCILROOMCOST;
	}

	@Override
	public String getName() {
		return GameConstants.COUNCILROOMNAME;
	}

	public String getText() {
		return GameConstants.COUNCILROOMTEXT;
	}

	public int getBuysAdded() {
		return GameConstants.COUNCILROOMBUYS;
	}

	public int getCardsAdded() {
		return GameConstants.COUNCILROOMCARDSADDED;
	}

	public CouncilRoomPlayState getPlayState() {
		return new CouncilRoomPlayState();
	}

}
