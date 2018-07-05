package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "upgradeName")
public class ReplayPostNameUpgradePatch {
	
	@SpireInsertPatch(rloc=4)
	public static void Insert(AbstractCard c) {
		if (AbstractDungeon.player != null && c.rarity == AbstractCard.CardRarity.BASIC && AbstractDungeon.player.hasRelic("Simple Rune")) {
			if (c.type == AbstractCard.CardType.ATTACK && c.cardID.toLowerCase().contains("strike")) {
				c.name = c.makeCopy().name + "+" + c.timesUpgraded;
				if (c.baseDamage > 0) {
					c.baseDamage += (c.timesUpgraded - 1);
				}
			} else {
				if (c.type == AbstractCard.CardType.SKILL && c.cardID.toLowerCase().contains("defend")) {
					c.name = c.makeCopy().name + "+" + c.timesUpgraded;
					if (c.baseBlock > 0) {
						c.baseBlock += (c.timesUpgraded - 1);
					}
				}
			}
		}
	}
	
	public static void PostFix(AbstractCard c) {
		if (AbstractDungeon.player != null && c.rarity == AbstractCard.CardRarity.BASIC && AbstractDungeon.player.hasRelic("Simple Rune")) {
			if ((c.type == AbstractCard.CardType.ATTACK && c.cardID.toLowerCase().contains("strike")) || (c.type == AbstractCard.CardType.SKILL && c.cardID.toLowerCase().contains("defend"))) {
				c.upgraded = false;
			}
		}
	}
	
}