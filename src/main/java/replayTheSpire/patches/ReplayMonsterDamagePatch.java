package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "damage")
public class ReplayMonsterDamagePatch {
	
	private static int initialDamage;
	
	public static void Prefix(AbstractMonster m, DamageInfo info) {
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower("Specialist") && info.owner == AbstractDungeon.player && info.type != DamageInfo.DamageType.NORMAL) {
			ReplayMonsterDamagePatch.initialDamage = info.output;
			info.output += AbstractDungeon.player.getPower("Specialist").amount;
		}
	}
	public static void Postfix(AbstractMonster m, DamageInfo info) {
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower("Specialist") && info.owner == AbstractDungeon.player && info.type != DamageInfo.DamageType.NORMAL) {
			info.output = ReplayMonsterDamagePatch.initialDamage;
		}
	}
	
}