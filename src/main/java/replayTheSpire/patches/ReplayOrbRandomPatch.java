package replayTheSpire.patches;

import java.util.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.orbs.*;
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
        if (useCardRng) {
            return orbs.get(AbstractDungeon.cardRng.random(orbs.size() - 1));
        }
        return orbs.get(MathUtils.random(orbs.size() - 1));
	}
	
}