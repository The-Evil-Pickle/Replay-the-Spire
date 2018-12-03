package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.relics.DimensionalGlitch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;

import replayTheSpire.ReplayTheSpireMod;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "damage")
public class ReplayMonsterDamagePatch {
	
	private static int initialDamage;
	public static boolean altered;
	private static int starthp;
	private static int endhp;
	
	public static void Prefix(AbstractMonster m, DamageInfo info) {
		starthp = m.currentHealth;
		altered = false;
		if (AbstractDungeon.player != null && (info.owner == null || info.owner == AbstractDungeon.player) && info.type != DamageInfo.DamageType.NORMAL && !m.hasPower(IntangiblePower.POWER_ID) && !m.hasPower(IntangiblePlayerPower.POWER_ID)) {
			ReplayMonsterDamagePatch.initialDamage = info.output;
			if (AbstractDungeon.player.hasPower("Specialist")) {
				altered = true;
				info.output += AbstractDungeon.player.getPower("Specialist").amount;
			}
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(DimensionalGlitch.ID)) {
				altered=true;
				info.output = Math.max(0, MathUtils.floor((float)info.output / 2.0f));
			}
		}
	}
	public static void Postfix(AbstractMonster m, DamageInfo info) {
		endhp = m.currentHealth;
		if (endhp < starthp) {
			ReplayTheSpireMod.onMonsterHpLoss(m, starthp - endhp);
		}
		if (altered) {//if (AbstractDungeon.player != null && (info.owner == null || info.owner == AbstractDungeon.player) && info.type != DamageInfo.DamageType.NORMAL && (AbstractDungeon.player.hasPower("Specialist") || ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(DimensionalGlitch.ID))) {
			info.output = ReplayMonsterDamagePatch.initialDamage;
		}
	}
	
}