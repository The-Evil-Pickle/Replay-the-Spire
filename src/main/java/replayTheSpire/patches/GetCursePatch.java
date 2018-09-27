package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.AbeCurse;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.random.Random;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.CardLibrary", method = "getCurse", paramtypes={})
public class GetCursePatch {
	
	public static AbstractCard Postfix(AbstractCard __result) {
		if (__result != null) {
			if (__result.cardID.equals(AbeCurse.ID)) {
				return CardLibrary.getCurse();
			}
		}
		return __result;
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "getCard", paramtypez={AbstractCard.CardRarity.class})
	public static class GetCurseRarityPatch {
		public static AbstractCard Postfix(AbstractCard __result, AbstractCard.CardRarity rarity) {
			if (__result != null) {
				if (__result.cardID.equals(AbeCurse.ID)) {
					return AbstractDungeon.getCard(rarity);
				}
			}
			return __result;
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "getCard", paramtypez={AbstractCard.CardRarity.class, Random.class})
	public static class GetCurseRarityRNGPatch {
		public static AbstractCard Postfix(AbstractCard __result, AbstractCard.CardRarity rarity, Random rng) {
			if (__result != null) {
				if (__result.cardID.equals(AbeCurse.ID)) {
					return AbstractDungeon.getCard(rarity, rng);
				}
			}
			return __result;
		}
	}
	
}