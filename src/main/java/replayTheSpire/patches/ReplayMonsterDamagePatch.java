package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.relics.DimensionalGlitch;

import replayTheSpire.ReplayTheSpireMod;

import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "damage")
public class ReplayMonsterDamagePatch {
	
	private static int initialDamage;
	
	public static void Prefix(AbstractMonster m, DamageInfo info) {
		if (AbstractDungeon.player != null && (info.owner == null || info.owner == AbstractDungeon.player) && info.type != DamageInfo.DamageType.NORMAL) {
			boolean altered = false;
			if (AbstractDungeon.player.hasPower("Specialist")) {
				altered = true;
				info.output += AbstractDungeon.player.getPower("Specialist").amount;
			}
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(DimensionalGlitch.ID)) {
				altered=true;
				info.output = Math.max(0, info.output - 2);
			}
			if (altered) {
				ReplayMonsterDamagePatch.initialDamage = info.output;
			}
		}
	}
	public static void Postfix(AbstractMonster m, DamageInfo info) {
		if (AbstractDungeon.player != null && (info.owner == null || info.owner == AbstractDungeon.player) && info.type != DamageInfo.DamageType.NORMAL && (AbstractDungeon.player.hasPower("Specialist") || ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(DimensionalGlitch.ID))) {
			info.output = ReplayMonsterDamagePatch.initialDamage;
		}
	}
	
}