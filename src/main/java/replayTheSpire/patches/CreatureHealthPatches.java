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
	@SpirePatch(cls = "com.megacrit.cardcrawl.core.AbstractCreature", method = "decrementBlock")
	public static class ShieldingBlockPatch {
		
		public static int Postfix(int damageAmount, AbstractCreature __Instance, final DamageInfo info, int originAmount) {
			if (!(__Instance instanceof AbstractPlayer)) {
				return damageAmount;
			}
			int currentShielding = ReplayTheSpireMod.shieldingAmount(__Instance);
			if (currentShielding > 0 && info.type != DamageInfo.DamageType.HP_LOSS) {
				if (damageAmount > currentShielding) {
					//CardCrawlGame.sound.play("BLOCK_BREAK");
					damageAmount -= currentShielding;
					ReplayTheSpireMod.clearShielding(__Instance);
					/*if (Settings.SHOW_DMG_BLOCK) {
						AbstractDungeon.effectList.add(new BlockedNumberEffect(this.hb.cX, this.hb.cY + this.hb.height / 2.0f, Integer.toString(this.currentBlock)));
					}
					this.loseBlock();
					AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0f + AbstractCreature.BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0f + AbstractCreature.BLOCK_ICON_Y));*/
				}
				else if (damageAmount == currentShielding) {
					damageAmount = 0;
					ReplayTheSpireMod.clearShielding(__Instance);
					/*this.loseBlock();
					AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0f + AbstractCreature.BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0f + AbstractCreature.BLOCK_ICON_Y));
					CardCrawlGame.sound.play("BLOCK_BREAK");
					AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractCreature.TEXT[1]));*/
				}
				else {
					//CardCrawlGame.sound.play("BLOCK_ATTACK");
					ReplayTheSpireMod.addShielding(__Instance, damageAmount * -1);
					/*this.loseBlock(damageAmount);
					for (int i = 0; i < 18; ++i) {
						AbstractDungeon.effectList.add(new BlockImpactLineEffect(this.hb.cX, this.hb.cY));
					}
					if (Settings.SHOW_DMG_BLOCK) {
						AbstractDungeon.effectList.add(new BlockedNumberEffect(this.hb.cX, this.hb.cY + this.hb.height / 2.0f, Integer.toString(damageAmount)));
					}*/
					damageAmount = 0;
				}
			}
			return damageAmount;
		}
		
		
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.core.AbstractCreature", method = "renderBlockIconAndValue")
	public static class RenderBlockPatch {
		public static void Prefix(AbstractCreature __Instance, final SpriteBatch sb, final float x, final float y) {
			if (!(__Instance instanceof AbstractPlayer)) {
				return;
			}
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
		
		/*@SpireInsertPatch(rloc=8)
		public static void Insert(AbstractCreature __Instance, final SpriteBatch sb) {
			final float x = __Instance.hb.cX - __Instance.hb.width / 2.0f;
			final float y = __Instance.hb.cY - __Instance.hb.height / 2.0f + __Instance.hbYOffset;
			if (__Instance.hasPower("Necrotic Poison") && !__Instance.hasPower("Poison")) {
				sb.setColor(__Instance.greenHbBarColor);
				if (__Instance.currentHealth > 0) {
					sb.draw(ImageMaster.HEALTH_BAR_L, x - AbstractCreature.HEALTH_BAR_HEIGHT, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
				}
				sb.draw(ImageMaster.HEALTH_BAR_B, x, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, __Instance.targetHealthBarWidth, AbstractCreature.HEALTH_BAR_HEIGHT);
				sb.draw(ImageMaster.HEALTH_BAR_R, x + __Instance.targetHealthBarWidth, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
			}
		}*/
		
		public static void Postfix(AbstractCreature __Instance, final SpriteBatch sb) {
			if (__Instance instanceof AbstractPlayer && __Instance.currentBlock == 0 && ReplayTheSpireMod.shieldingAmount(__Instance) > 0) {
				final float x = __Instance.hb.cX - __Instance.hb.width / 2.0f;
				final float y = __Instance.hb.cY - __Instance.hb.height / 2.0f + (float)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractCreature.class, "hbYOffset");
				sb.setColor(new Color(0.6f, 0.93f, 0.98f, 1.0f));
				sb.draw(ReplayTheSpireMod.shieldingIcon, x + (-46.0f * Settings.scale) - 32.0f, y + (-14.0f * Settings.scale) - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
				FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(ReplayTheSpireMod.shieldingAmount(__Instance)), x + (-46.0f * Settings.scale), y - 16.0f * Settings.scale, new Color(0.9f, 0.9f, 0.9f, 0.9f), 1f);
			}
		}
		
	}
	
	/*@SpirePatch(cls = "com.megacrit.cardcrawl.core.AbstractCreature", method = "renderRedHealthBar")
	public static class RedHealthPatch {
		
		public static void Replace(AbstractCreature __Instance, final SpriteBatch sb) {
			if (__Instance.currentBlock > 0) {
				sb.setColor(__Instance.blueHbBarColor);
			}
			else {
				sb.setColor(__Instance.redHbBarColor);
			}
			if (!__Instance.hasPower("Poison") && !__Instance.hasPower("Necrotic Poison")) {
				if (__Instance.currentHealth > 0) {
					sb.draw(ImageMaster.HEALTH_BAR_L, x - AbstractCreature.HEALTH_BAR_HEIGHT, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
				}
				sb.draw(ImageMaster.HEALTH_BAR_B, x, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, __Instance.targetHealthBarWidth, AbstractCreature.HEALTH_BAR_HEIGHT);
				sb.draw(ImageMaster.HEALTH_BAR_R, x + __Instance.targetHealthBarWidth, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
			}
			else {
				int poisonAmt = 0;
				if (__Instance.hasPower("Poison")) {
					poisonAmt += __Instance.getPower("Poison").amount;
				}
				if (__Instance.hasPower("Necrotic Poison")) {
					poisonAmt += __Instance.getPower("Necrotic Poison").amount * 2;
				}
				if (poisonAmt > 0 && __Instance.hasPower("Intangible")) {
					poisonAmt = 1;
				}
				if (__Instance.currentHealth > poisonAmt) {
					float w = 1.0f - (__Instance.currentHealth - poisonAmt) / __Instance.currentHealth;
					w *= __Instance.targetHealthBarWidth;
					if (__Instance.currentHealth > 0) {
						sb.draw(ImageMaster.HEALTH_BAR_L, x - AbstractCreature.HEALTH_BAR_HEIGHT, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
					}
					sb.draw(ImageMaster.HEALTH_BAR_B, x, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, __Instance.targetHealthBarWidth - w, AbstractCreature.HEALTH_BAR_HEIGHT);
					sb.draw(ImageMaster.HEALTH_BAR_R, x + __Instance.targetHealthBarWidth - w, y + AbstractCreature.HEALTH_BAR_OFFSET_Y, AbstractCreature.HEALTH_BAR_HEIGHT, AbstractCreature.HEALTH_BAR_HEIGHT);
				}
			}
		}
		
		
	}*/
	
}