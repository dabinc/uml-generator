package TestFiles.AcceptanceTestProjects.LargeProject.gameComponents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Copper;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Estate;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Gardens;
import TestFiles.AcceptanceTestProjects.LargeProject.util.CardFactory;

public class Player {

	private String name;
	List<Card> hand;
	Stack<Card> drawPile;
	Stack<Card> discardPile;
	private GUI gui;

	public Player(String name, GUI gui) {
		this.hand = new ArrayList<Card>();
		this.drawPile = new Stack<Card>();
		this.discardPile = new Stack<Card>();
		this.addStarterCards();
		this.name = name;
		this.gui = gui;
	}

	public List<Card> playCard(Card card, List<Card> playArea) {
		if (this.hand.contains(card)) {
			playArea.add(card);
			this.hand.remove(card);
		}
		return playArea;
	}

	public void drawACard() {
		if (this.drawPile.size() > 0) {
			this.hand.add(this.drawPile.pop());
		} else if (this.discardPile.size() > 0) {
			this.drawPile.addAll(this.discardPile);
			this.discardPile.clear();
			Collections.shuffle(this.drawPile);
			this.drawACard();
		}
	}

	public void drawNewHand() {
		for (int i = 0; i < 5; i++) {
			this.drawACard();
		}
	}

	public void cleanup(List<Card> playArea) {
		this.discardPile.addAll(playArea);
		playArea.clear();
		this.discardHand();
		this.drawNewHand();
	}

	public void discardHand() {
		this.discardPile.addAll(this.hand);
		this.hand.clear();
	}

	public void discardDrawPile() {
		this.discardPile.addAll(this.drawPile);
		this.drawPile.clear();
	}

	public Optional<Card> chooseCardToPlay(String phaseKey, int actions, int buys, int coins) {
		return this.gui.chooseCardToPlay(this.hand, name, phaseKey, actions, buys, coins,
				this.discardPile.size(), this.drawPile.size()).join();
	}

	public Optional<Card> buy(Supply supplyPiles, String phaseKey, int actions, int buys,
			int coins) {
		return this.gui.chooseCardToBuy(supplyPiles.getAvailableCards(), this.name, phaseKey,
				actions, buys, coins, this.discardPile.size(), this.drawPile.size()).join();
	}

	public Card forcedBuy(Supply supplyPiles, String phaseKey, int tempCoins) {
		return this.gui.forceCardToBuy(supplyPiles.getAvailableCards(), this.name, phaseKey,
				tempCoins, this.discardPile.size(), this.drawPile.size()).join();
	}

	public int getPoints() {
		int deckSize = this.hand.size() + this.drawPile.size() + this.discardPile.size();
		int totalPoints = this.addPointsFromPile(this.drawPile, deckSize);
		totalPoints += this.addPointsFromPile(this.hand, deckSize);
		totalPoints += this.addPointsFromPile(this.discardPile, deckSize);
		return totalPoints;
	}

	public String getName() {
		return name;
	}

	public int sizeOfDrawPile() {
		return this.drawPile.size();
	}

	public int sizeOfHand() {
		return this.hand.size();
	}

	public int sizeOfDiscardPile() {
		return this.discardPile.size();
	}

	public List<Card> getHand() {
		return this.hand;
	}

	public void gainCard(Card card) {
		this.discardPile.push(card);
	}

	private void addStarterCards() {
		for (int i = 0; i < 7; i++) {
			this.drawPile.push(CardFactory.makeCard(Copper.class));
		}
		for (int i = 0; i < 3; i++) {
			this.drawPile.push(CardFactory.makeCard(Estate.class));
		}
		Collections.shuffle(drawPile);
	}

	private int addPointsFromPile(Collection<Card> cards, int deckSize) {
		int totalPoints = 0;
		for (Card card : cards) {
			totalPoints += card.getVictoryValue();
			if (card.getClass().equals(Gardens.class)) {
				totalPoints += (deckSize / 10);
			}
		}
		return totalPoints;
	}

	public boolean promptYesNo(String messageKey) {
		return this.gui.promptYesNo(messageKey, this.name).join();
	}

	public boolean trashCardFromHand(Class<? extends Card> cardClass) {
		for (Card c : this.hand) {
			if (c.getClass() == cardClass) {
				this.hand.remove(c);
				return true;
			}
		}
		return false;
	}

	public Optional<Card> discardTopCardOfDrawPile() {
		if (this.sizeOfDrawPile() == 0 && this.sizeOfDiscardPile() == 0) {
			return Optional.empty();
		} else if (this.sizeOfDrawPile() == 0) {
			this.drawPile.addAll(this.discardPile);
			this.discardPile.clear();
			return this.discardTopCardOfDrawPile();
		} else {
			Card toMove = this.drawPile.pop();
			this.discardPile.push(toMove);
			return Optional.of(toMove);
		}
	}

	public Card chooseCardFromHand(String phaseKey, int actions, int buys, int coins) {
		return gui.chooseCardFromHand(this.hand, this.name, phaseKey, actions, buys, coins,
				this.discardPile.size(), this.drawPile.size()).join();
	}

	public boolean discardCardFromHand(Class<? extends Card> cardClass) {
		for (Card c : this.hand) {
			if (c.getClass() == cardClass) {
				this.hand.remove(c);
				this.discardPile.push(c);
				return true;
			}
		}
		return false;
	}

	public boolean hasCardInHand(Class<? extends Card> cardClass) {
		for (Card c : this.hand) {
			if (c.getClass() == cardClass) {
				return true;
			}
		}
		return false;
	}

	public void gainCardToHand(Card card) {
		this.hand.add(card);
	}

	public void placeOnDrawPile(Card card) {
		this.drawPile.push(card);
	}
}
