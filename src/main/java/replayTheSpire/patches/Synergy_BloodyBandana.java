package replayTheSpire.patches;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.Bandana;
import com.megacrit.cardcrawl.relics.BloodyIdol;

@SpirePatch(cls = "com.megacrit.cardcrawl.relics.BloodyIdol", method = "onGainGold")
public class Synergy_BloodyBandana {
	public static SpireReturn Prefix(BloodyIdol __Instance) {
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(Bandana.ID) && AbstractDungeon.player.getRelic(Bandana.ID).counter > 0) {
			__Instance.flash();
			AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, __Instance));
	        AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, 5));
			return SpireReturn.Return(null);
		}
		return SpireReturn.Continue();
	}
}
