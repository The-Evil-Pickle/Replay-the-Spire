package ReplayTheSpireMod.patches;

import java.util.*;
import ReplayTheSpireMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.powers.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.orbs.*;
import basemod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.Dark", method = "applyFocus")
public class ReplayDarkOrbIntPatch {
	
	public static void Postfix(AbstractOrb __Instance) {
		final AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if (power == null) {
			final int basePassiveAmount = (int)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractOrb.class, "basePassiveAmount");
            __Instance.passiveAmount = Math.max(0, basePassiveAmount);
        }
		int mypos = AbstractDungeon.player.orbs.indexOf(__Instance);
		if (mypos > -1) {
			if (mypos > 0) {
				AbstractOrb adorb = AbstractDungeon.player.orbs.get(mypos - 1);
				if (adorb != null && adorb.ID != null && adorb.ID.equals("Crystal")) {
					__Instance.passiveAmount += adorb.passiveAmount;
				}
			}
			if (mypos < AbstractDungeon.player.orbs.size() - 1) {
				AbstractOrb adorb = AbstractDungeon.player.orbs.get(mypos + 1);
				if (adorb != null && adorb.ID != null && adorb.ID.equals("Crystal")) {
					__Instance.passiveAmount += adorb.passiveAmount;
				}
			}
		}
	}
	
}