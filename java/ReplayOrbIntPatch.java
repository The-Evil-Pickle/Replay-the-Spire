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

@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.AbstractOrb", method = "applyFocus")
public class ReplayOrbIntPatch {
	
	public static void Postfix(AbstractOrb __Instance) {
		final AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if (power == null) {
			final int basePassiveAmount = (int)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractOrb.class, "basePassiveAmount");
			final int baseEvokeAmount = (int)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractOrb.class, "baseEvokeAmount");
            __Instance.passiveAmount = Math.max(0, basePassiveAmount);
			if (!__Instance.ID.equals("Dark")) {
				__Instance.evokeAmount = Math.max(0, baseEvokeAmount);
			}
        }
		//ReplayTheSpireMod.logger.info(__Instance.ID);
		int mypos = AbstractDungeon.player.orbs.indexOf(__Instance);
		//ReplayTheSpireMod.logger.info(mypos);
		if (mypos > -1 && !__Instance.ID.equals("Plasma")) {
			//ReplayTheSpireMod.logger.info("CP0");
			if (mypos > 0) {
				//ReplayTheSpireMod.logger.info("CP1");
				AbstractOrb adorb = AbstractDungeon.player.orbs.get(mypos - 1);
				/*if (adorb != null && adorb.ID != null) {
					ReplayTheSpireMod.logger.info(adorb.ID);
				}*/
				if (adorb != null && adorb.ID != null && adorb.ID.equals("Crystal")) {
					if (!__Instance.ID.equals("Crystal")) {
						__Instance.passiveAmount += adorb.passiveAmount;
					}
					if (!__Instance.ID.equals("Dark")) {
						__Instance.evokeAmount += adorb.passiveAmount;
					}
				}
			}
			if (mypos < AbstractDungeon.player.orbs.size() - 1) {
				//ReplayTheSpireMod.logger.info("CP2");
				AbstractOrb adorb = AbstractDungeon.player.orbs.get(mypos + 1);
				/*if (adorb != null && adorb.ID != null) {
					ReplayTheSpireMod.logger.info(adorb.ID);
				}*/
				if (adorb != null && adorb.ID != null && adorb.ID.equals("Crystal")) {
					if (!__Instance.ID.equals("Crystal")) {
						__Instance.passiveAmount += adorb.passiveAmount;
					}
					if (!__Instance.ID.equals("Dark")) {
						__Instance.evokeAmount += adorb.passiveAmount;
					}
				}
			}
		}
	}
	
}