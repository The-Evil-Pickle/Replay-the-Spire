package replayTheSpire.patches;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.ByrdFeeder;

import javassist.CtBehavior;

@SpirePatch(clz = AbstractCreature.class, method = "heal", paramtypez = { int.class, boolean.class })
public class PostHealPatch
{
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(final AbstractCreature __instance, final int amount, final boolean showEffect) {
        if (__instance != null && __instance.isPlayer) {
        	if (AbstractDungeon.player.hasRelic(ByrdFeeder.ID) && __instance.currentHealth > __instance.maxHealth) {
        		AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(__instance, __instance, __instance.currentHealth - __instance.maxHealth));
        	}
        }
    }
    
    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(final CtBehavior ctMethodToPatch) throws Exception {
            final Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentHealth");
            int[] lines = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            for (int i=0; i < lines.length; i++) {
            	lines[i]++;
            }
            return lines;
        }
    }
}
