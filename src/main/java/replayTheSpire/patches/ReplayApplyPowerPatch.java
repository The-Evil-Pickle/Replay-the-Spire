package replayTheSpire.patches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.core.*;

import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

import basemod.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.ApplyPowerAction", method = "update")
public class ReplayApplyPowerPatch {
	
	public static void Prefix(ApplyPowerAction __instance) {
		float duration = (float)ReflectionHacks.getPrivate((Object)__instance, (Class)AbstractGameAction.class, "duration");
		float startDuration = (float)ReflectionHacks.getPrivate((Object)__instance, (Class)ApplyPowerAction.class, "startingDuration");
		if (__instance != null && __instance.target != null && !__instance.target.isDying && !__instance.target.isDead && duration == startDuration) {
			AbstractPower powerToApply = (AbstractPower)ReflectionHacks.getPrivate((Object)__instance, (Class)ApplyPowerAction.class, "powerToApply");
			
			if (ReplayTheSpireMod.foundmod_servant && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("m_ScarletBlood") && __instance.target.isPlayer && powerToApply instanceof StrengthPower) {
				((m_ScarletBlood)ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("m_ScarletBlood")).onGainStrength(__instance);
				//AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__instance.target, __instance.source, new KnivesPower(__instance.target, __instance.amount * 2), __instance.amount * 2));
				//AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(__instance.target, ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("m_ScarletBlood")));
			}
			
			if (!__instance.target.isPlayer && powerToApply.ID.equals("Strength") && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Counterbalance")) {
				AbstractRelic cbr = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Counterbalance");
				if ((AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.getCurrRoom().eliteTrigger) && (__instance.amount / 2) > 0) {
					__instance.amount -= (__instance.amount / 2);
					powerToApply.amount  -= (powerToApply.amount / 2);
					if (cbr != null) {
						cbr.flash();
					}
				}
			}
		}
	}
	
}