package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.beaked.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses.CompoundingHeadache;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import beaked.cards.AbstractWitherCard;
import replayTheSpire.ReplayTheSpireMod;

public class beakedbs {
	public static void addBeakedCards() {
		ReplayTheSpireMod.AddAndUnlockCard(new RavenHex());
		ReplayTheSpireMod.AddAndUnlockCard(new WingsOfSteel());
		ReplayTheSpireMod.AddAndUnlockCard(new OwlGaze());
		ReplayTheSpireMod.AddAndUnlockCard(new CompoundingHeadache());
	}
	public static boolean chaosCheck(AbstractCard c) {
		return (c instanceof AbstractWitherCard && ((AbstractWitherCard)c).linkWitherAmountToMagicNumber);
	}
}
