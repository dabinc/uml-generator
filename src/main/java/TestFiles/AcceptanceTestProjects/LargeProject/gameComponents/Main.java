package TestFiles.AcceptanceTestProjects.LargeProject.gameComponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Artisan;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Cellar;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Chancellor;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Chapel;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.CouncilRoom;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Feast;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Festival;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Gardens;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Laboratory;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Market;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Militia;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Moat;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Moneylender;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Remodel;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Smithy;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.ThroneRoom;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Vassal;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Village;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Woodcutter;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Workshop;
import TestFiles.AcceptanceTestProjects.LargeProject.util.AvailableLocales;
import TestFiles.AcceptanceTestProjects.LargeProject.util.GameConstants;

public class Main {

	public static void main(String[] args) {
		boolean playAgain = true;
		GUI gui = new GUI();
		while (playAgain) {
			gui = new GUI();

			GameConstants.messages = ResourceBundle.getBundle("MessagesBundle", chooseLocale(gui));

			int numPlayers = getNumPlayers(gui);
			Player[] players = new Player[numPlayers];
			for (int i = 0; i < numPlayers; ++i) {
				players[i] = createPlayer(gui, i + 1);
			}

			Supply supply = new Supply(numPlayers);
			supply.makeKingdomCardList(Main.getAvailableKingdomCards(), new Random());

			Game game = new Game(supply, players);

			Set<Player> winners = game.runGame();

			playAgain = promptPlayAgainDisplayWinners(gui, winners);
			gui.clear();
			gui.setVisible(false);
		}
		closeGUI(gui);
	}

	static void closeGUI(GUI gui) {
		gui.quitGame();
	}

	static boolean promptPlayAgainDisplayWinners(GUI gui, Set<Player> winners) {
		return gui.getPlayAgainDisplayWinners(winners).join();
	}

	static int getNumPlayers(GUI gui) {
		return gui.initNumPlayers().join();
	}

	static Player createPlayer(GUI gui, int number) {
		String name = gui.getPlayerXName(number).join();
		return new Player(name, gui);
	}

	static Locale chooseLocale(GUI gui) {
		AvailableLocales chosenLocale = gui.chooseLocale().join();
		Locale locale;

		if (chosenLocale.equals(AvailableLocales.EN)) {
			locale = new Locale("en");
		} else if (chosenLocale.equals(AvailableLocales.ES)) {
			locale = new Locale("es");
		} else {
			locale = new Locale("en");
		}

		return locale;
	}

	static List<Card> getAvailableKingdomCards() {
		List<Card> availableKingdomCards = new ArrayList<Card>();

		availableKingdomCards.add(new Cellar());
		availableKingdomCards.add(new Chancellor());
		availableKingdomCards.add(new Chapel());
		availableKingdomCards.add(new Festival());
		availableKingdomCards.add(new Market());
		availableKingdomCards.add(new Laboratory());
		availableKingdomCards.add(new Moat());
		availableKingdomCards.add(new Moneylender());
		availableKingdomCards.add(new Smithy());
		availableKingdomCards.add(new ThroneRoom());
		availableKingdomCards.add(new Vassal());
		availableKingdomCards.add(new Village());
		availableKingdomCards.add(new Woodcutter());
		availableKingdomCards.add(new Remodel());
		availableKingdomCards.add(new Artisan());
		availableKingdomCards.add(new Feast());
		availableKingdomCards.add(new CouncilRoom());
		availableKingdomCards.add(new Gardens());
		availableKingdomCards.add(new Workshop());
		availableKingdomCards.add(new Militia());

		return availableKingdomCards;
	}
}
