package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = AbstractCard.class, method = "applyPowersToBlock")
public class CardApplyPowersPatch {
	public static void Postfix(final AbstractCard __instance) {
		if (__instance.block > 0 && AbstractDungeon.player.stance.ID == "Replay:Guard") {
			__instance.block *= 2;
			__instance.isBlockModified = true;
		}
	}
}
