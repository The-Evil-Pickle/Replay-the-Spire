package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses.Sluggish;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.marisa.LightBash;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.marisa.SparkCircuitry;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;

public class marisabs {
	static void AddAndUnlockCard(AbstractCard c)
	{
		BaseMod.addCard(c);
		UnlockTracker.unlockCard(c.cardID);
	}
	public static void addCards() {
		AddAndUnlockCard(new LightBash());
		AddAndUnlockCard(new SparkCircuitry());
		AddAndUnlockCard(new Sluggish());
	}
}
