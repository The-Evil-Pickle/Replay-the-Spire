package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.DoublePoisonAction;
import com.megacrit.cardcrawl.actions.unique.TriplePoisonAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;

import basemod.ReflectionHacks;

public class NecroticIsAPoison {
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.actions.unique.DoublePoisonAction", method = "update")
	public static class DoublePoisonPatch {
		public static void Postfix(DoublePoisonAction __Instance) {
			float duration = (float)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractGameAction.class, "duration");
			float startDuration = (float)ReflectionHacks.getPrivate((Object)__Instance, (Class)DoublePoisonAction.class, "startingDuration");
			if (duration == startDuration && __Instance.target != null && __Instance.target.hasPower("Necrotic Poison")) {
	            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__Instance.target, __Instance.source, new NecroticPoisonPower(__Instance.target, __Instance.source, __Instance.target.getPower("Necrotic Poison").amount), __Instance.target.getPower("Necrotic Poison").amount));
	        }
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.actions.unique.TriplePoisonAction", method = "update")
	public static class TriplePoisonPatch {
		public static void Postfix(TriplePoisonAction __Instance) {
			float duration = (float)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractGameAction.class, "duration");
			float startDuration = (float)ReflectionHacks.getPrivate((Object)__Instance, (Class)TriplePoisonAction.class, "startingDuration");
			if (duration == startDuration && __Instance.target != null && __Instance.target.hasPower("Necrotic Poison")) {
	            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__Instance.target, __Instance.source, new NecroticPoisonPower(__Instance.target, __Instance.source, __Instance.target.getPower("Necrotic Poison").amount * 2), __Instance.target.getPower("Necrotic Poison").amount * 2));
	        }
		}
	}
}
