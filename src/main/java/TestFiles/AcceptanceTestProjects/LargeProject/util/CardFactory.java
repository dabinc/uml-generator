package TestFiles.AcceptanceTestProjects.LargeProject.util;

import TestFiles.AcceptanceTestProjects.LargeProject.cards.Artisan;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Card;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Cellar;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Chancellor;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Chapel;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Copper;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.CouncilRoom;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Curse;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Duchy;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Estate;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Feast;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Festival;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Gardens;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Gold;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Laboratory;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Market;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Militia;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Moat;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Moneylender;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Province;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Remodel;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Silver;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Smithy;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.ThroneRoom;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Vassal;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Village;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Woodcutter;
import TestFiles.AcceptanceTestProjects.LargeProject.cards.Workshop;

public class CardFactory {

	public static Card makeCard(Class<? extends Card> cardClass) {

		if (cardClass.equals(Copper.class)) {
			return new Copper();
		} else if (cardClass.equals(Silver.class)) {
			return new Silver();
		} else if (cardClass.equals(Gold.class)) {
			return new Gold();
		} else if (cardClass.equals(Estate.class)) {
			return new Estate();
		} else if (cardClass.equals(Duchy.class)) {
			return new Duchy();
		} else if (cardClass.equals(Province.class)) {
			return new Province();
		} else if (cardClass.equals(Curse.class)) {
			return new Curse();
		} else if (cardClass.equals(Cellar.class)) {
			return new Cellar();
		} else if (cardClass.equals(Chancellor.class)) {
			return new Chancellor();
		} else if (cardClass.equals(Chapel.class)) {
			return new Chapel();
		} else if (cardClass.equals(Festival.class)) {
			return new Festival();
		} else if (cardClass.equals(Laboratory.class)) {
			return new Laboratory();
		} else if (cardClass.equals(Market.class)) {
			return new Market();
		} else if (cardClass.equals(Moat.class)) {
			return new Moat();
		} else if (cardClass.equals(Moneylender.class)) {
			return new Moneylender();
		} else if (cardClass.equals(Smithy.class)) {
			return new Smithy();
		} else if (cardClass.equals(ThroneRoom.class)) {
			return new ThroneRoom();
		} else if (cardClass.equals(Vassal.class)) {
			return new Vassal();
		} else if (cardClass.equals(Village.class)) {
			return new Village();
		} else if (cardClass.equals(Woodcutter.class)) {
			return new Woodcutter();
		} else if (cardClass.equals(Remodel.class)) {
			return new Remodel();
		} else if (cardClass.equals(Artisan.class)) {
			return new Artisan();
		} else if (cardClass.equals(Feast.class)) {
			return new Feast();
		} else if (cardClass.equals(CouncilRoom.class)) {
			return new CouncilRoom();
		} else if (cardClass.equals(Gardens.class)) {
			return new Gardens();
		} else if (cardClass.equals(Workshop.class)) {
			return new Workshop();
		} else if (cardClass.equals(Militia.class)) {
			return new Militia();
		} else {
			return new Copper();
		}
	}

}
