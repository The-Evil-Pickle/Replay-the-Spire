package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.relics.*;
import ReplayTheSpireMod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.random.Random;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "getRewardCards")
public class ReplayChaosPatch {
	
	public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result) {
		if (AbstractDungeon.player.hasRelic("Honey Jar") && __result.size() < 4) {
			final ArrayList<AbstractCard> retVal = new ArrayList<AbstractCard>();
			final AbstractCard.CardRarity rarity = AbstractDungeon.rollRarity();
			AbstractCard card = null;
			switch (rarity) {
				case RARE: {
					card = AbstractDungeon.getCard(rarity);
					AbstractDungeon.cardBlizzRandomizer = AbstractDungeon.cardBlizzStartOffset;
					break;
				}
				case UNCOMMON: {
					card = AbstractDungeon.getCard(rarity);
					break;
				}
				case COMMON: {
					card = AbstractDungeon.getCard(rarity);
					AbstractDungeon.cardBlizzRandomizer -= AbstractDungeon.cardBlizzGrowth;
					if (AbstractDungeon.cardBlizzRandomizer <= AbstractDungeon.cardBlizzMaxOffset) {
						AbstractDungeon.cardBlizzRandomizer = AbstractDungeon.cardBlizzMaxOffset;
						break;
					}
					break;
				}
				default: {
					//AbstractDungeon.logger.info("WTF?");
					break;
				}
			}
			final int dupeCount = 0;
			while (retVal.contains(card)) {
				if (card != null) {
					//AbstractDungeon.logger.info("DUPE: " + card.originalName);
				}
				if (dupeCount < 4) {
					card = AbstractDungeon.getCard(rarity);
				}
				else {
					//AbstractDungeon.logger.info("FALLBACK FOR CARD RARITY HAS OCCURRED");
					switch (rarity) {
						case RARE: {
							card = AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON);
							continue;
						}
						case UNCOMMON: {
							card = AbstractDungeon.getCard(AbstractCard.CardRarity.COMMON);
							continue;
						}
						case COMMON: {
							card = AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON);
							continue;
						}
						default: {
							card = AbstractDungeon.getCard(AbstractCard.CardRarity.COMMON);
							continue;
						}
					}
				}
			}
			if (card != null) {
				retVal.add(card);
			}
			for (final AbstractCard c : retVal) {
				__result.add(c.makeCopy());
			}
		}
		
		
		
		if (AbstractDungeon.player.hasRelic("Ring of Chaos")) {
			int numToChange = AbstractDungeon.miscRng.random(2);
			for (int i = 0; i < __result.size() - 2; i++){
				numToChange += AbstractDungeon.miscRng.random(2);
			}
			for (int i = 0; i < __result.size() && numToChange > 0; i++){
				if (((RingOfChaos)ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Ring of Chaos")).chaosUpgradeCard(__result.get(i))) {
					numToChange -= 1;
				}
			}
		}
		return __result;
	}
	
}