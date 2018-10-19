package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.replayxover.curses.MeltdownSequence;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;

public class constructbs {
	static void AddAndUnlockCard(AbstractCard c)
	{
		BaseMod.addCard(c);
		UnlockTracker.unlockCard(c.cardID);
	}
	public static void addCards() {
		AddAndUnlockCard(new MeltdownSequence());
	}
}
