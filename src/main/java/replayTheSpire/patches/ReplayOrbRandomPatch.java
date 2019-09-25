package replayTheSpire.patches;

import java.util.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.orbs.Plasma;

import javassist.CtBehavior;

import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.AbstractOrb", method = "getRandomOrb")
public class ReplayOrbRandomPatch {
	
	@SpireInsertPatch(locator = Locator.class, localvars = {"orbs"})
    public static void addInfiniteSpireOrbs(boolean useCardRng, ArrayList<AbstractOrb> orbs) {
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("Iron Core")) {
			orbs.add(new HellFireOrb());
		} else {
			orbs.add(new CrystalOrb());
		}
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("m_SpellCore")) {
			orbs.add(new ManaSparkOrb());
		}
    }
	public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
	
}