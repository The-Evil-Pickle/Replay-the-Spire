package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.DrinkMe;

import javassist.CtBehavior;

@SpirePatch(cls = "com.megacrit.cardcrawl.core.AbstractCreature", method = "loadAnimation")
public class LoadAnimPatch {
	public static void Prefix(AbstractCreature __instance, final String atlasUrl, final String skeletonUrl, float scale) {
		if (__instance.isPlayer && AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(DrinkMe.ID)) {
			scale += 0.3f;
		}
	}
}
