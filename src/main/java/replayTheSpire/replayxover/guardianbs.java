package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.mod.replay.cards.replayxover.guardian.GuardianBash;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.guardian.GuardianCast;

import replayTheSpire.ReplayTheSpireMod;

public class guardianbs {
	public static void addCards() {
		ReplayTheSpireMod.AddAndUnlockCard(new GuardianBash());
		ReplayTheSpireMod.AddAndUnlockCard(new GuardianCast());
	}
}
