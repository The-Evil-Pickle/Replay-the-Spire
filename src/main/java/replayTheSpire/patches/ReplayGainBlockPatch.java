package replayTheSpire.patches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.core.*;

import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

import basemod.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.GainBlockAction", method = "update")
public class ReplayGainBlockPatch {
	
	public static void Prefix(GainBlockAction __instance) {
		float duration = (float)ReflectionHacks.getPrivate((Object)__instance, (Class)AbstractGameAction.class, "duration");
		float startDuration = (float)ReflectionHacks.getPrivate((Object)__instance, (Class)AbstractGameAction.class, "startDuration");
		if (!__instance.target.isDying && !__instance.target.isDead && duration == startDuration) {
			if (!__instance.target.isPlayer && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Counterbalance")) {
				AbstractRelic cbr = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Counterbalance");
				if ((AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.getCurrRoom().eliteTrigger) && (__instance.amount / 2) > 0) {
					__instance.amount -= (__instance.amount / 2);
					if (cbr != null) {
						cbr.flash();
					}
				}
			}
		}
	}
	
}