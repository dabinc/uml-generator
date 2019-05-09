package TestFiles.AcceptanceTestProjects.LargeProject.gameComponents;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;
import TestFiles.AcceptanceTestProjects.LargeProject.util.AvailableLocales;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private Container pane;
	JButton quitB;

	public GUI() {
		setTitle(GameConstants.messages.getString("guiDominionTitle"));
		pane = getContentPane();

		quitB = new JButton(GameConstants.messages.getString("guiQuit"));
		quitB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(DISPOSE_ON_CLOSE);
			}
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pane.add(quitB);
	}

	public void clear() {
		this.pane.removeAll();
		this.pane.setLayout(new GridLayout(0, 3, 0, 0));
	}

	private void drawExitButton(CompletableFuture<Optional<Card>> future) {
		JButton end = new JButton();
		end.setText(GameConstants.messages.getString("guiEndPhase"));
		end.addActionListener(new EndTurnListener(future));
		this.pane.add(end);

	}

	private void drawCard(Card card, CompletableFuture<Optional<Card>> future) {
		JButton box = new JButton();
		String text = this.makeText(card);
		box.setText(text);
		box.addActionListener(new CardListener(future, card));
		this.pane.add(box);// add at the end
	}

	private class EndTurnListener implements ActionListener {
		CompletableFuture<Optional<Card>> future;

		public EndTurnListener(CompletableFuture<Optional<Card>> future) {
			this.future = future;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			future.complete(Optional.empty());

		}

	}

	private class CardListener implements ActionListener {
		CompletableFuture<Optional<Card>> future;
		Card card;

		public CardListener(CompletableFuture<Optional<Card>> future, Card card) {
			this.future = future;
			this.card = card;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.future.complete(Optional.of(card));
		}

	}

	private String makeText(Card card) {
		StringBuilder cardText = new StringBuilder();
		cardText.append("<html>");// only way to get newline on buttons
		cardText.append(GameConstants.messages.getString(card.getName()));
		cardText.append("<br />");

		cardText.append(
				String.format(GameConstants.messages.getString("guiCardCost"), card.getCost()));
		cardText.append("<br />");

		if (card.getActionsAdded() != 0) {
			cardText.append(String.format(GameConstants.messages.getString("guiCardActions"),
					card.getActionsAdded()));
			cardText.append("<br />");
		}
		if (card.getBuysAdded() != 0) {
			cardText.append(String.format(GameConstants.messages.getString("guiCardBuys"),
					card.getBuysAdded()));
			cardText.append("<br />");
		}
		if (card.getCardsAdded() != 0) {
			cardText.append(String.format(GameConstants.messages.getString("guiCardCards"),
					card.getCardsAdded()));
			cardText.append("<br />");
		}
		if (card.getCoinsAdded() != 0) {
			cardText.append(String.format(GameConstants.messages.getString("guiCardCoins"),
					card.getCoinsAdded()));
			cardText.append("<br />");
		}
		if (card.getVictoryValue() != 0) {
			cardText.append(String.format(GameConstants.messages.getString("guiCardVictoryPoints"),
					card.getVictoryValue()));
			cardText.append("<br />");
		}
		if (card.getText().length() != 0) {
			cardText.append(String.format(GameConstants.messages.getString(card.getText())));
			cardText.append("<br />");
		}
		cardText.append("</html>");
		return cardText.toString();
	}

	public CompletableFuture<AvailableLocales> chooseLocale() {
		this.setTitle(GameConstants.messages.getString("guiChooseLocale"));
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		pane.setLayout(new GridLayout(1, 2));

		CompletableFuture<AvailableLocales> localeChosenFuture = new CompletableFuture<>();

		JButton english = new JButton(GameConstants.messages.getString("languageChoiceEnglish"));
		english.addActionListener(
				new ChooseLocaleButtonListener(AvailableLocales.EN, localeChosenFuture));

		JButton spanish = new JButton(GameConstants.messages.getString("languageChoiceSpanish"));
		spanish.addActionListener(
				new ChooseLocaleButtonListener(AvailableLocales.ES, localeChosenFuture));

		pane.add(english);
		pane.add(spanish);

		this.pane.repaint();
		this.pack();
		this.setSize(GameConstants.GUIWIDTH, GameConstants.GUIHEIGHT);

		return localeChosenFuture;
	}

	private class ChooseLocaleButtonListener implements ActionListener {

		AvailableLocales localeChosen;
		CompletableFuture<AvailableLocales> localeChosenFuture;

		public ChooseLocaleButtonListener(AvailableLocales localeChosen,
				CompletableFuture<AvailableLocales> localeChosenFuture) {
			this.localeChosen = localeChosen;
			this.localeChosenFuture = localeChosenFuture;
		}

		@Override
		public void actionPerformed(ActionEvent localeButtonPressed) {
			this.localeChosenFuture.complete(this.localeChosen);
		}
	}

	public CompletableFuture<Integer> initNumPlayers() {
		this.clear();
		this.setTitle(GameConstants.messages.getString("guiNumPlayersTitle"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		pane.setLayout(new GridLayout(0, 3));

		CompletableFuture<Integer> numPlayersFuture = new CompletableFuture<>();

		JButton two = new JButton("2");
		two.addActionListener(new InitNumPlayersButtonListener(2, numPlayersFuture));

		JButton three = new JButton("3");
		three.addActionListener(new InitNumPlayersButtonListener(3, numPlayersFuture));

		JButton four = new JButton("4");
		four.addActionListener(new InitNumPlayersButtonListener(4, numPlayersFuture));

		pane.add(two);
		pane.add(three);
		pane.add(four);

		this.setVisible(true);
		this.pane.repaint();
		this.pack();
		this.setSize(GameConstants.GUIWIDTH, GameConstants.GUIHEIGHT);

		return numPlayersFuture;
	}

	private class InitNumPlayersButtonListener implements ActionListener {

		int numPlayers;
		CompletableFuture<Integer> numPlayersFuture;

		InitNumPlayersButtonListener(int numPlayers, CompletableFuture<Integer> numPlayersFuture) {
			this.numPlayers = numPlayers;
			this.numPlayersFuture = numPlayersFuture;
		}

		@Override
		public void actionPerformed(ActionEvent buttonPress) {
			this.numPlayersFuture.complete(this.numPlayers);
		}

	}

	public CompletableFuture<String> getPlayerXName(int number) {
		this.clear();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		CompletableFuture<String> playerXNameFuture = new CompletableFuture<>();

		String textPrompt = String.format(GameConstants.messages.getString("guiPlayerNamePrompt"),
				number);
		this.setTitle(textPrompt);

		JTextField textField = new JTextField(textPrompt);
		textField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				textField.selectAll();
			}

			@Override
			public void focusLost(FocusEvent arg0) {

			}

		});
		this.pane.add(textField);
		JButton submit = new JButton();
		submit.addActionListener(new SubmitListener(textField, playerXNameFuture));
		submit.setText(GameConstants.messages.getString("submit"));
		this.pane.add(submit);

		this.pack();
		this.setSize(GameConstants.GUIWIDTH, GameConstants.GUIHEIGHT);

		return playerXNameFuture;
	}

	private class SubmitListener implements ActionListener {
		JTextField textField;
		private CompletableFuture<String> future;

		public SubmitListener(JTextField textField, CompletableFuture<String> playerXNameFuture) {
			this.textField = textField;
			this.future = playerXNameFuture;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.future.complete(this.textField.getText());
		}

	}

	public CompletableFuture<Boolean> getPlayAgainDisplayWinners(Set<Player> winners) {
		this.clear();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		CompletableFuture<Boolean> playAgain = new CompletableFuture<Boolean>();
		StringBuilder title = new StringBuilder();
		title.append(GameConstants.messages.getString("guiWinGameTitle"));
		for (Player winner : winners) {
			title.append(" : ");
			title.append(winner.getName());
		}
		title.append(". ");
		title.append(GameConstants.messages.getString("guiPromptPlayAgain"));
		this.setTitle(title.toString());

		pane.setLayout(new GridLayout(1, 2));

		JButton playAgainButton = new JButton(GameConstants.messages.getString("guiYes"));
		playAgainButton.addActionListener(new PlayAgainButtonListener(true, playAgain));

		JButton quitButton = new JButton(GameConstants.messages.getString("guiNo"));
		quitButton.addActionListener(new PlayAgainButtonListener(false, playAgain));

		pane.add(playAgainButton);
		pane.add(quitButton);

		this.pane.repaint();
		this.pack();
		this.setSize(GameConstants.GUIWIDTH, GameConstants.GUIHEIGHT);

		return playAgain;
	}

	private class PlayAgainButtonListener implements ActionListener {

		boolean choice;
		CompletableFuture<Boolean> futureChoice;

		public PlayAgainButtonListener(boolean b, CompletableFuture<Boolean> playAgain) {
			choice = b;
			futureChoice = playAgain;
		}

		@Override
		public void actionPerformed(ActionEvent makeChoice) {
			futureChoice.complete(choice);
		}

	}

	public void quitGame() {
		System.exit(DISPOSE_ON_CLOSE);
	}

	public CompletableFuture<Optional<Card>> chooseCardToPlay(List<Card> availableCards,
			String name, String phaseKey, int actions, int buys, int coins, int discardSize,
			int drawSize) {
		this.clear();
		this.pane.setLayout(new GridLayout(0, availableCards.size() + 1, 0, 0));
		this.setVisible(true);
		CompletableFuture<Optional<Card>> cardToPlay = new CompletableFuture<Optional<Card>>();
		this.setTitle(name + ": " + GameConstants.messages.getString(phaseKey) + " : "
				+ GameConstants.messages.getString("chooseCardToPlay") + "    "
				+ String.format(GameConstants.messages.getString("guiHeaderInfo"), actions, buys,
						coins, discardSize, drawSize));

		for (Card card : availableCards) {
			this.drawCard(card, cardToPlay);
		}

		this.drawExitButton(cardToPlay);

		this.setVisible(true);
		this.setSize(GameConstants.GUICARDSIZE * availableCards.size(), GameConstants.GUICARDSIZE);
		this.pane.repaint();
		this.repaint();

		return cardToPlay;
	}

	public CompletableFuture<Boolean> promptYesNo(String messageKey, String name) {
		String message = GameConstants.messages.getString(messageKey);
		message = name + ": " + message;

		CompletableFuture<Boolean> response = new CompletableFuture<Boolean>();

		int ans = JOptionPane.showConfirmDialog(null, message, "", JOptionPane.YES_NO_OPTION);
		response.complete(ans == JOptionPane.YES_OPTION);

		return response;
	}

	public CompletableFuture<Card> chooseCardFromHand(List<Card> availableCards, String name,
			String phaseKey, int actions, int buys, int coins, int discardSize, int drawSize) {
		this.clear();
		this.pane.setLayout(new GridLayout(0, availableCards.size(), 0, 0));
		this.setVisible(true);

		CompletableFuture<Card> chosenCardFuture = new CompletableFuture<Card>();
		String title = GameConstants.messages.getString("chooseCardFromHand");
		title = name + ": " + GameConstants.messages.getString(phaseKey) + " : " + title + "    "
				+ String.format(GameConstants.messages.getString("guiHeaderInfo"), actions, buys,
						coins, discardSize, drawSize);

		this.setTitle(title);

		for (Card card : availableCards) {
			this.drawForcedCard(card, chosenCardFuture);
		}

		this.setVisible(true);
		this.setSize(GameConstants.GUICARDSIZE * availableCards.size(), GameConstants.GUICARDSIZE);
		this.pane.repaint();
		this.repaint();

		return chosenCardFuture;
	}

	private void drawForcedCard(Card card, CompletableFuture<Card> future) {

		JButton box = new JButton();
		String text = this.makeText(card);
		box.setText(text);
		box.addActionListener(new ForcedCardListener(future, card));
		this.pane.add(box, -1);

	}

	private class ForcedCardListener implements ActionListener {
		CompletableFuture<Card> future;
		Card card;

		public ForcedCardListener(CompletableFuture<Card> future, Card card) {
			this.future = future;
			this.card = card;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.future.complete(card);
		}

	}

	public CompletableFuture<Optional<Card>> chooseCardToBuy(List<Card> availableCards, String name,
			String phaseKey, int actions, int buys, int coins, int discardSize, int drawSize) {
		this.clear();
		CompletableFuture<Optional<Card>> cardToBuy = new CompletableFuture<Optional<Card>>();

		String title = GameConstants.messages.getString("chooseCardToBuy");
		title = name + ": " + GameConstants.messages.getString(phaseKey) + " : " + title + "    "
				+ String.format(GameConstants.messages.getString("guiHeaderInfo"), actions, buys,
						coins, discardSize, drawSize);
		this.setTitle(title);

		for (Card card : availableCards) {
			this.drawCard(card, cardToBuy);
		}
		this.drawExitButton(cardToBuy);

		this.setSize(GameConstants.GUICARDSIZE * 3, GameConstants.GUICARDSIZE * 3);
		this.setVisible(true);
		this.pane.repaint();
		this.repaint();

		return cardToBuy;
	}

	public CompletableFuture<Card> forceCardToBuy(List<Card> availableCards, String name,
			String phaseKey, int tempCoins, int discardSize, int drawSize) {
		this.clear();
		CompletableFuture<Card> cardToBuy = new CompletableFuture<Card>();

		String title = GameConstants.messages.getString("chooseCardToBuy");
		title = name + ": " + GameConstants.messages.getString(phaseKey) + " : " + title + "    "
				+ String.format(GameConstants.messages.getString("guiHeaderInfoForcedBuy"), 1,
						tempCoins, discardSize, drawSize);
		this.setTitle(title);

		for (Card card : availableCards) {
			this.drawForcedCard(card, cardToBuy);
		}

		this.setSize(GameConstants.GUICARDSIZE * 3, GameConstants.GUICARDSIZE * 3);
		this.setVisible(true);
		this.pane.repaint();
		this.repaint();

		return cardToBuy;
	}

}
