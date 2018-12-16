package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import replayTheSpire.ReplayTheSpireMod;

public class chronobs {
	public static void addCards() {
		if (ReplayTheSpireMod.foundmod_stslib) {
			ReplayTheSpireMod.AddAndUnlockCard(new SwitchThreat());
		}
		ReplayTheSpireMod.AddAndUnlockCard(new SwitchConfidence());
	}
	public static void setCompendiumSwitchCards() {
		final AbstractCard c = new SwitchThreat(ImminentThreat.ID);
        c.cardID = "ImminentThreat.ID";
        BaseMod.addCard(c);
        BaseMod.addCard(new SwitchThreat(VengefulThreat.ID));
	}
	public static void setGameSwitchCards() {
        BaseMod.removeCard(ImminentThreat.ID, AbstractCard.CardColor.CURSE);
        BaseMod.removeCard("SwitchThreat", AbstractCard.CardColor.CURSE);
        //REMOVING CARDS FROM LIBRARY NOW
        BaseMod.addCard(new SwitchThreat());
	}
}
