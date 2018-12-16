package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses.Sluggish;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.marisa.LightBash;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.marisa.SparkCircuitry;

import replayTheSpire.ReplayTheSpireMod;

public class marisabs {
	public static void addCards() {
		ReplayTheSpireMod.AddAndUnlockCard(new LightBash());
		ReplayTheSpireMod.AddAndUnlockCard(new SparkCircuitry());
		ReplayTheSpireMod.AddAndUnlockCard(new Sluggish());
	}
}
