package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.DimensionalGlitch;
import com.megacrit.cardcrawl.relics.GhostHeart;

import replayTheSpire.ReplayTheSpireMod;


@SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method = "damage")
public class PlayerDamagePatch {
	//68
	@SpireInsertPatch(rloc = 68)
	public static SpireReturn Insert(AbstractPlayer player, final DamageInfo info) {
		
		if (player.hasRelic(GhostHeart.ID) && ((GhostHeart)player.getRelic(GhostHeart.ID)).counter == -1) {
			player.currentHealth = 0;
			player.getRelic(GhostHeart.ID).onTrigger();
            return SpireReturn.Return(null);
        }
		
		return SpireReturn.Continue();
	}
	

	private static int initialDamage;
	
	public static void Prefix(AbstractPlayer player, DamageInfo info) {
		if (player != null && info.type != DamageInfo.DamageType.NORMAL) {
			boolean altered = false;
			if (info.owner != null && info.owner != player && info.owner.hasPower("Specialist")) {
				altered = true;
				info.output += info.owner.getPower("Specialist").amount;
			}
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(DimensionalGlitch.ID)) {
				altered=true;
				info.output += 2;
			}
			if (altered) {
				PlayerDamagePatch.initialDamage = info.output;
			}
		}
	}
	public static void Postfix(AbstractPlayer player, DamageInfo info) {
		if (player != null && info.type != DamageInfo.DamageType.NORMAL && ((info.owner != AbstractDungeon.player && info.owner.hasPower("Specialist")) || ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(DimensionalGlitch.ID))) {
			info.output = PlayerDamagePatch.initialDamage;
		}
	}
}
