package replayTheSpire.replayxover;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.construct.ArmamentsMkII;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.construct.MirrorSystem;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses.MeltdownSequence;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;
import replayTheSpire.ReplayTheSpireMod;

public class constructbs {
	public static void addCards() {
		if (ConstructMod.overheatedExpansion) {
			ReplayTheSpireMod.AddAndUnlockCard(new MeltdownSequence());
			ReplayTheSpireMod.AddAndUnlockCard(new MirrorSystem());
		}
		ReplayTheSpireMod.AddAndUnlockCard(new ArmamentsMkII());
	}
	public static int chaos_overheat(AbstractCard c) {
		if (!(c instanceof AbstractConstructCard)) {
			return 0;
		}
		return ((AbstractConstructCard)c).overheat;
	}
	public static float chaos_overheat_downside(AbstractCard card, float downtarg) {
		AbstractConstructCard c = (AbstractConstructCard)card;
		float downmult = downtarg;
		int prevnum;
		prevnum = c.overheat;
		c.upgradeOverheat((int) (prevnum / (-1.0f / downtarg)));
		downmult = (float)c.overheat / (float)prevnum;
		return downmult;
	}
	public static void chaos_overheat_upside(AbstractCard card, float downmult) {
		AbstractConstructCard c = (AbstractConstructCard)card;
		int prevnum;
		prevnum = MathUtils.ceilPositive((float)c.overheat / downmult) - c.overheat;
		if (prevnum <= 0) {
			prevnum = 1;
		}
		c.upgradeOverheat(prevnum);
	}
}
