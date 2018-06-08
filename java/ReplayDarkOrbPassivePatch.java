package ReplayTheSpireMod.patches;

import java.util.*;
import ReplayTheSpireMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.powers.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.orbs.*;
import basemod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.Dark", method = "onEndOfTurn")
public class ReplayDarkOrbPassivePatch {
	
	public static void Postfix(AbstractOrb __Instance) {
		if (AbstractDungeon.player.hasRelic("Raider's Mask")) {
			AbstractMonster weakestMonster = null;
			int weakestHP = 0;
			for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
				if (!m.isDeadOrEscaped()) {
					if (weakestMonster == null) {
						weakestMonster = m;
						weakestHP = m.currentHealth;
					}
					else {
						if (m.currentHealth >= weakestMonster.currentHealth) {
							continue;
						}
						weakestMonster = m;
						weakestHP = m.currentHealth;
					}
				}
			}
			for (final AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
				if (m2.hasPower("Lockon") && !m2.isDeadOrEscaped()) {
					weakestMonster = m2;
					weakestHP = m2.currentHealth;
					break;
				}
			}
			if (weakestMonster != null && weakestHP <= __Instance.evokeAmount) {
				__Instance.onEvoke();
				final AbstractOrb orbSlot = new EmptyOrbSlot();
				int index = AbstractDungeon.player.orbs.indexOf(__Instance);
				AbstractDungeon.player.orbs.set(index, orbSlot);
				for (int i = index + 1; i < AbstractDungeon.player.orbs.size(); ++i) {
					Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
				}
				for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
					AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
				}
			}
		}
	}
	
}