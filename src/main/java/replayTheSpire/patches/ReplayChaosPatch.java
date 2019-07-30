package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.modifiers.ChaoticModifier;
import com.megacrit.cardcrawl.mod.replay.potions.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;

import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.random.Random;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "getRewardCards")
public class ReplayChaosPatch {
	
	public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result) {
		if (AbstractDungeon.player.hasRelic(OnyxGauntlets.ID)) {
			for (int i = 0; i < __result.size() - 2; i++){
				if (__result.get(i).cost == -1 && __result.get(i).rawDescription.contains("X") && __result.get(i).rarity != AbstractCard.CardRarity.SPECIAL && __result.get(i).rarity != AbstractCard.CardRarity.BASIC) {
					__result.set(i, AbstractDungeon.getCard(__result.get(i).rarity).makeCopy());
				}
			}
		}
		
		if (AbstractDungeon.player.hasRelic(RingOfChaos.ID)) {
			int numToChange = 0;
			for (int i = 0; i < __result.size(); i++){
				if (RingOfChaos.SETTING_CHANCE.testChance(AbstractDungeon.miscRng) || (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(ChaoticModifier.ID) && AbstractDungeon.miscRng.randomBoolean(0.9f))) {
					numToChange += 1;
				}
			}
			for (int i = 0; i < __result.size() && numToChange > 0; i++){
				if (((RingOfChaos)ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic(RingOfChaos.ID)).chaosUpgradeCard(__result.get(i))) {
					numToChange -= 1;
				}
			}
		}
		return __result;
	}
	
}