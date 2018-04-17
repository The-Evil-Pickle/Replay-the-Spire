package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "initializeTitle")
public class ReplayCardTitlePatch {
	
	public static void Prefix(AbstractCard c) {
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("Simple Rune")) {
			if (c.rarity == AbstractCard.CardRarity.BASIC &&
			((c.type == AbstractCard.CardType.ATTACK && c.cardID.toLowerCase().contains("strike")) || 
			(c.type == AbstractCard.CardType.SKILL && c.cardID.toLowerCase().contains("defend")))
			) {
				c.upgraded = (c.timesUpgraded > 0);
			}
		}
	}
	public static void Postfix(AbstractCard c) {
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("Simple Rune")) {
			if (c.rarity == AbstractCard.CardRarity.BASIC &&
			((c.type == AbstractCard.CardType.ATTACK && c.cardID.toLowerCase().contains("strike")) || 
			(c.type == AbstractCard.CardType.SKILL && c.cardID.toLowerCase().contains("defend")))
			) {
				c.upgraded = false;
			}
		}
	}
	
}