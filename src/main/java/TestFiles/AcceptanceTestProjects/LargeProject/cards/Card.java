package TestFiles.AcceptanceTestProjects.LargeProject.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.states.CardPlayState;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardType;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public abstract class Card {

	public abstract Set<CardType> getType();

	public int getCoinsAdded() {
		return GameConstants.DEFAULTCARDATTRIBUTE;
	}

	public int getActionsAdded() {
		return GameConstants.DEFAULTCARDATTRIBUTE;
	}

	public int getBuysAdded() {
		return GameConstants.DEFAULTCARDATTRIBUTE;
	}

	public int getCardsAdded() {
		return GameConstants.DEFAULTCARDATTRIBUTE;
	}

	public int getVictoryValue() {
		return GameConstants.DEFAULTCARDATTRIBUTE;
	}

	public abstract int getCost();
	
	public abstract String getName();
	
	public String getText() {
		return GameConstants.DEFAULTCARDTEXT;
	}

	public CardPlayState getPlayState() {
		return new CardPlayState();
	}

	public List<String> getTypeTranslationKeys() {
		ArrayList<String> translationsKeys = new ArrayList<>();
		Set<CardType> cardTypes = this.getType();

		if (cardTypes.contains(CardType.ACTION)) {
			translationsKeys.add("cardtypeAction");
		}
		if (cardTypes.contains(CardType.TREASURE)) {
			translationsKeys.add("cardtypeTreasure");
		}
		if (cardTypes.contains(CardType.VICTORY)) {
			translationsKeys.add("cardtypeVictory");
		}
		if (cardTypes.contains(CardType.CURSE)) {
			translationsKeys.add("cardtypeCurse");
		}
		if (cardTypes.contains(CardType.ATTACK)) {
			translationsKeys.add("cardtypeAttack");
		}
		if (cardTypes.contains(CardType.REACTION)) {
			translationsKeys.add("cardtypeReaction");
		}

		return translationsKeys;
	}

}
