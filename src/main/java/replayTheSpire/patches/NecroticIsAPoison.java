package replayTheSpire.patches;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.BaneAction;
import com.megacrit.cardcrawl.actions.unique.DoublePoisonAction;
import com.megacrit.cardcrawl.actions.unique.TriplePoisonAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import basemod.ReflectionHacks;

public class NecroticIsAPoison {
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.actions.unique.DoublePoisonAction", method = "update")
	public static class DoublePoisonPatch {
		public static void Postfix(DoublePoisonAction __Instance) {
			if (__Instance.target != null && __Instance.target.hasPower(NecroticPoisonPower.POWER_ID)) {
	            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__Instance.target, __Instance.source, new NecroticPoisonPower(__Instance.target, __Instance.source, __Instance.target.getPower("Necrotic Poison").amount), __Instance.target.getPower(NecroticPoisonPower.POWER_ID).amount));
	        }
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.actions.unique.TriplePoisonAction", method = "update")
	public static class TriplePoisonPatch {
		public static void Postfix(TriplePoisonAction __Instance) {
			if (__Instance.target != null && __Instance.target.hasPower(NecroticPoisonPower.POWER_ID)) {
	            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__Instance.target, __Instance.source, new NecroticPoisonPower(__Instance.target, __Instance.source, __Instance.target.getPower("Necrotic Poison").amount * 2), __Instance.target.getPower(NecroticPoisonPower.POWER_ID).amount * 2));
	        }
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.actions.unique.BaneAction", method = "update")
	public static class BanePatch {
		public static void Postfix(BaneAction __Instance) {
			AbstractMonster m = (AbstractMonster)ReflectionHacks.getPrivate((Object)__Instance, (Class)BaneAction.class, "m");
	        if (!m.hasPower("Poison") && m.hasPower(NecroticPoisonPower.POWER_ID)) {
	        	__Instance.isDone = false;
				float duration = (float)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractGameAction.class, "duration");
				DamageInfo info = (DamageInfo)ReflectionHacks.getPrivate((Object)__Instance, (Class)BaneAction.class, "info");
	            if (duration == 0.01f && __Instance.target != null && __Instance.target.currentHealth > 0) {
	                if (info.type != DamageInfo.DamageType.THORNS && info.owner.isDying) {
	                	__Instance.isDone = true;
	                    return;
	                }
	                AbstractDungeon.effectList.add(new FlashAtkImgEffect(__Instance.target.hb.cX, __Instance.target.hb.cY, __Instance.attackEffect));
	            }
	            duration -= Gdx.graphics.getDeltaTime();
	            ReflectionHacks.setPrivate(__Instance, AbstractGameAction.class, "duration", duration);
	            if (duration < 0.0f) {
	            	__Instance.isDone = true;
	            }
	            if (__Instance.isDone && __Instance.target != null && __Instance.target.currentHealth > 0) {
	            	__Instance.target.damage(info);
	                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
	                    AbstractDungeon.actionManager.clearPostCombatActions();
	                }
	                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
	            }
	        }
		}
	}
}
