package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "canUpgrade")
public class ReplayCanUpgradePatch {
	
	public static boolean Postfix(boolean __result, AbstractCard c) {
		if (!__result && AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("Simple Rune")) {
			if (c.rarity == AbstractCard.CardRarity.BASIC &&
			((c.type == AbstractCard.CardType.ATTACK && c.cardID.toLowerCase().contains("strike")) || 
			(c.type == AbstractCard.CardType.SKILL && c.cardID.toLowerCase().contains("defend")))
			) {
				if (c.color != AbstractCard.CardColor.RED && c.color != AbstractCard.CardColor.GREEN) {
					c.upgraded = false;
				}
				return true;
			}
		}
		return __result;
	}
	
}