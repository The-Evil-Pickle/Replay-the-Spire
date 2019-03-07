package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.mod.replay.ui.campfire.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.mod.replay.vfx.campfire.*;
import com.megacrit.cardcrawl.mod.replay.vfx.combat.ShieldingNumberEffect;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import java.util.*;

import basemod.*;

import com.megacrit.cardcrawl.daily.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;

public class CreatureHealthPatches
{
	@SpirePatch(
	        cls="com.megacrit.cardcrawl.core.AbstractCreature",
	        method=SpirePatch.CLASS
	)
	public static class ReplayCreatureFields {
		public static SpireField<Integer> shielding = new SpireField<>(() -> 0);
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.core.AbstractCreature", method = "decrementBlock")
	public static class ShieldingBlockPatch {
		
		public static int Postfix(int damageAmount, AbstractCreature __Instance, final DamageInfo info, int originAmount) {
			/*if (!(__Instance instanceof AbstractPlayer) && !ReplayTheSpireMod.monstersUsingShielding) {
				return damageAmount;
			}*/
			int currentShielding = ReplayTheSpireMod.shieldingAmount(__Instance);
			if (currentShielding > 0 && info.type != DamageInfo.DamageType.HP_LOSS) {
				if (damageAmount > currentShielding) {
					//CardCrawlGame.sound.play("BLOCK_BREAK");
					damageAmount -= currentShielding;
					if (Settings.SHOW_DMG_BLOCK) {
						AbstractDungeon.effectList.add(new ShieldingNumberEffect(__Instance, __Instance.hb.cX, __Instance.hb.cY + __Instance.hb.height / 2.0f, currentShielding));
					}
					ReplayTheSpireMod.clearShielding(__Instance);
					//AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0f + AbstractCreature.BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0f + AbstractCreature.BLOCK_ICON_Y));
				}
				else if (damageAmount == currentShielding) {
					ReplayTheSpireMod.clearShielding(__Instance);
					if (Settings.SHOW_DMG_BLOCK) {
						AbstractDungeon.effectList.add(new ShieldingNumberEffect(__Instance, __Instance.hb.cX, __Instance.hb.cY + __Instance.hb.height / 2.0f, damageAmount));
					}
					damageAmount = 0;
					/*CardCrawlGame.sound.play("BLOCK_BREAK");
					AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractCreature.TEXT[1]));*/
				}
				else {
					//CardCrawlGame.sound.play("BLOCK_ATTACK");
					ReplayTheSpireMod.addShielding(__Instance, damageAmount * -1);
					if (Settings.SHOW_DMG_BLOCK) {
						AbstractDungeon.effectList.add(new ShieldingNumberEffect(__Instance, __Instance.hb.cX, __Instance.hb.cY + __Instance.hb.height / 2.0f, damageAmount));
					}
					damageAmount = 0;
				}
			}
			return damageAmount;
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.core.AbstractCreature", method = "renderBlockIconAndValue")
	public static class RenderBlockPatch {
		public static void Prefix(AbstractCreature __Instance, final SpriteBatch sb, final float x, final float y) {
			/*if (!(__Instance instanceof AbstractPlayer) && !ReplayTheSpireMod.monstersUsingShielding) {
				return;
			}*/
			int currentShielding = ReplayTheSpireMod.shieldingAmount(__Instance);
			if (currentShielding > 0) {
				sb.setColor(new Color(0.6f, 0.93f, 0.98f, 1.0f));
				sb.draw(ReplayTheSpireMod.shieldingIcon, x + (-46.0f * Settings.scale) - 32.0f, y + (-14.0f * Settings.scale) - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
				FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(currentShielding), x + (-46.0f * Settings.scale), y - 16.0f * Settings.scale, new Color(0.9f, 0.9f, 0.9f, 0.9f), 1f);
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.core.AbstractCreature", method = "renderHealth")
	public static class MainFunctionPatch {
		
		public static void Postfix(AbstractCreature __Instance, final SpriteBatch sb) {
			if (__Instance.currentBlock == 0 && ReplayTheSpireMod.shieldingAmount(__Instance) > 0) {//((__Instance instanceof AbstractPlayer) || ReplayTheSpireMod.monstersUsingShielding) && 
				final float x = __Instance.hb.cX - __Instance.hb.width / 2.0f;
				final float y = __Instance.hb.cY - __Instance.hb.height / 2.0f + (float)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractCreature.class, "hbYOffset");
				sb.setColor(new Color(0.6f, 0.93f, 0.98f, 1.0f));
				sb.draw(ReplayTheSpireMod.shieldingIcon, x + (-46.0f * Settings.scale) - 32.0f, y + (-14.0f * Settings.scale) - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
				FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(ReplayTheSpireMod.shieldingAmount(__Instance)), x + (-46.0f * Settings.scale), y - 16.0f * Settings.scale, new Color(0.9f, 0.9f, 0.9f, 0.9f), 1f);
			}
		}
		
	}
	
}