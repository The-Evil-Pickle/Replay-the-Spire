package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.replayxover.curses.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import replayTheSpire.ReplayTheSpireMod;

public class chronobs {
	static void AddAndUnlockCard(AbstractCard c)
	{
		BaseMod.addCard(c);
		UnlockTracker.unlockCard(c.cardID);
	}
	public static void addCards() {
		if (ReplayTheSpireMod.foundmod_stslib) {
			AddAndUnlockCard(new SwitchThreat());
		}
		AddAndUnlockCard(new SwitchConfidence());
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
