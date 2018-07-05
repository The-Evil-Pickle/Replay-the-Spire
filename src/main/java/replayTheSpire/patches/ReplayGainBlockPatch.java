package replayTheSpire.patches;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.rooms.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import basemod.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.GainBlockAction", method = "update")
public class ReplayGainBlockPatch {
	
	public static void Prefix(GainBlockAction __instance) {
		float duration = (float)ReflectionHacks.getPrivate((Object)__instance, (Class)AbstractGameAction.class, "duration");
		float startDuration = (float)ReflectionHacks.getPrivate((Object)__instance, (Class)AbstractGameAction.class, "startDuration");
		if (!__instance.target.isDying && !__instance.target.isDead && duration == startDuration) {
			if (!__instance.target.isPlayer && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Counterbalance")) {
				AbstractRelic cbr = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Counterbalance");
				if ((AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) && (__instance.amount / 2) > 0) {
					__instance.amount -= (__instance.amount / 2);
					if (cbr != null) {
						cbr.flash();
					}
				}
			}
		}
	}
	
}