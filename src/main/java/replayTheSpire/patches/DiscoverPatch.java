package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(clz=CardRewardScreen.class, method = "discoveryOpen", paramtypez = {})
public class DiscoverPatch {
	public static AbstractCard.CardColor lookingForColor = null;
	public static AbstractCard.CardRarity lookingForRarity = null;
	public static AbstractCard.CardType lookingForType = null;
	public static int lookingForCount = 3;
	public static AbstractCard lookingForProhibit = null;

	@SpireInsertPatch(locator = Locator.class, localvars = {"derp"})
	public static void Insert(CardRewardScreen __instance, ArrayList<AbstractCard> derp){
		if(lookingForColor != null || lookingForRarity != null || lookingForType != null) {
			derp.clear();
			while(derp.size() != lookingForCount) {
				boolean dupe = false;
				AbstractCard tmp;
				tmp = getTrulyRandomCard();

				if(tmp.hasTag(AbstractCard.CardTags.HEALING)
						|| (lookingForColor != null && lookingForColor != tmp.color)
						|| (lookingForRarity != null && lookingForRarity != tmp.rarity)
						|| (lookingForType != null && lookingForType != tmp.type)
						){
					dupe = true;
				}
				
				for(AbstractCard c : derp) {
					if(c.cardID.equals(tmp.cardID)){
						dupe = true;
						break;
					}
				}

				if(!dupe) {
					AbstractCard c = tmp.makeCopy();
					derp.add(c);
				}
			}
			__instance.rewardGroup = derp;
			lookingForColor = null;
			lookingForRarity = null;
			lookingForType = null;
			lookingForCount = 3;
			lookingForProhibit = null;
		}
	}

	private static AbstractCard getTrulyRandomCard() {
		AbstractCard card = CardLibrary.getAllCards().get(AbstractDungeon.cardRng.random(0, CardLibrary.getAllCards().size()));

		if(card == null) {
			card = new Madness();
		}

		if(card.type != AbstractCard.CardType.CURSE)
			card.setCostForTurn(0);

		return card;
	}

	private static class Locator extends SpireInsertLocator
	{
		@Override
		public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
		{
			Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardRewardScreen.class, "rewardGroup");
			return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
		}
	}
}
