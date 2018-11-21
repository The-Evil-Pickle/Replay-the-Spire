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
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.AbstractOrb", method = "getRandomOrb")
public class ReplayOrbRandomPatch {
	
	public static AbstractOrb Replace(final boolean useCardRng) {
		final ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
        orbs.add(new Dark());
        orbs.add(new Frost());
        orbs.add(new Lightning());
        orbs.add(new Plasma());
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("Iron Core")) {
			orbs.add(new HellFireOrb());
		} else {
			orbs.add(new CrystalOrb());
		}
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("m_SpellCore")) {
			orbs.add(new ManaSparkOrb());
		}
        if (useCardRng) {
            return orbs.get(AbstractDungeon.cardRng.random(orbs.size() - 1));
        }
        return orbs.get(MathUtils.random(orbs.size() - 1));
	}
	
}