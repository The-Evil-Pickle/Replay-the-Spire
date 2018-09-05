package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.evacipated.cardcrawl.modthespire.patcher.PrefixPatchInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.relics.SimpleRune;

import basemod.BaseMod;
import basemod.helpers.*;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;
import replayTheSpire.ReplayTheSpireMod;

public class SimplicityRunePatches {
	@SpirePatch(
	        cls="com.megacrit.cardcrawl.cards.AbstractCard",
	        method="canUpgrade"
	)
	public static class CanUpgradePatch {
		public static boolean Postfix(boolean __Result, AbstractCard card) {
			if (!__Result && (CardTags.hasTag(card, BaseModTags.BASIC_STRIKE) || CardTags.hasTag(card, BaseModTags.BASIC_DEFEND)) && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
				return true;
			}
			return __Result;
		}
	}
	/*
	@SpirePatch(
	        cls="com.megacrit.cardcrawl.cards.AbstractCard",
	        method="upgradeName"
	)
	public static class OnUpgradeNamePatch {
		@SpireInsertPatch(rloc=4)
		public static void ASDHJK(AbstractCard card) {
			if ((CardTags.hasTag(card, BaseModTags.BASIC_STRIKE) || CardTags.hasTag(card, BaseModTags.BASIC_DEFEND)) && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
				
				card.name = card.makeCopy().name + "+" + card.timesUpgraded;
			}
		}
	}*/
	public static void TryPatchingShit() {
		try {
			TryPatchingCard(new Strike_Red());
			TryPatchingCard(new Strike_Green());
			TryPatchingCard(new Strike_Blue());
			TryPatchingCard(new Defend_Red());
			TryPatchingCard(new Defend_Green());
			TryPatchingCard(new Defend_Blue());
			for (AbstractCard c : BaseMod.getCustomCardsToAdd()) {
				if(CardTags.hasTag(c, BaseModTags.BASIC_STRIKE) || CardTags.hasTag(c, BaseModTags.BASIC_DEFEND)) {
					TryPatchingCard(c);
				}
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void TryPatchingCard(AbstractCard c) throws NotFoundException {
		CtClass ctClsToPatch = ClassPool.getDefault().get(c.getClass().getName());
		CtBehavior ctMethodToPatch = ctClsToPatch.getDeclaredMethod("upgrade");
		CtClass[] cArg = new CtClass[1];
        cArg[0] = ClassPool.getDefault().get(AbstractCard.class.getName());
        PrefixPatchInfo patch = new PrefixPatchInfo(ctMethodToPatch, ClassPool.getDefault().get(SimplicityRunePatches.class.getName()).getDeclaredMethod("RunicUpdatePatch", cArg));
		try {
			patch.doPatch();
		} catch (PatchingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static SpireReturn RunicUpdatePatch(AbstractCard card) {
		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
			if (CardTags.hasTag(card, BaseModTags.BASIC_STRIKE)) {
				card.baseDamage += 3 + card.timesUpgraded;
		        card.upgradedDamage = true;
			} else if (CardTags.hasTag(card, BaseModTags.BASIC_DEFEND)) {
				card.baseBlock += 3 + card.timesUpgraded;
		        card.upgradedBlock = true;
			}
			card.upgraded = true;
	        card.timesUpgraded++;
	        card.name = Strike_Red.NAME + "+" + card.timesUpgraded;
			return SpireReturn.Return(null);
		}
		
		return SpireReturn.Continue();
	}
}
