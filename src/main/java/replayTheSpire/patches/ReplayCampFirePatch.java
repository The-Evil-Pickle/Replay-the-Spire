package replayTheSpire.patches;

import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.ui.campfire.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.ui.campfire.RestOption;

import java.util.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.ui.campfire.RestOption", method = "useOption")
public class ReplayCampFirePatch {
	
	public static void Postfix(RestOption __instance) {
		if (AbstractDungeon.player.hasRelic("Gremlin Food")) {
			final ArrayList<AbstractCard> upgradableCards = new ArrayList<AbstractCard>();
			for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
				if (c.canUpgrade()) {
					upgradableCards.add(c);
				}
			}
			Collections.shuffle(upgradableCards);
			if (!upgradableCards.isEmpty()) {
				upgradableCards.get(0).upgrade();
				AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
				AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy()));
			}
		}
	}
	
}