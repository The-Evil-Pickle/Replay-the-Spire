package replayTheSpire.replayxover;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss.*;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.FadingForestBoss;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.PondfishBoss;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.hec.HellsEngine;
import com.megacrit.cardcrawl.mod.replay.powers.replayxover.StudyFableSpinnerPower;
import com.megacrit.cardcrawl.mod.replay.powers.replayxover.StudyHecPower;
import com.megacrit.cardcrawl.mod.replay.powers.replayxover.StudyPondfishPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.BaseMod;
import downfall.cards.OctoChoiceCard;
import expansioncontent.expansionContentMod;
import expansioncontent.cards.QuickStudy;
import expansioncontent.cards.StudyTheSpire;
import slimebound.powers.SlimedPower;

public class downfallbs {
	public static void addBossCards() {
		BaseMod.addCard(new SS_Fish_DragToHell());
		BaseMod.addCard(new SS_Fish_LivingLantern());
		BaseMod.addCard(new SS_Fish_SixFeetUnder());
		BaseMod.addCard(new SS_Forest_Treasure());
		BaseMod.addCard(new SS_Forest_LostForever());
		BaseMod.addCard(new SS_Forest_Fishers());
		BaseMod.addCard(new SS_Hec_ForgedInHellfire());
		BaseMod.addCard(new SS_Hec_SteelHeart());
		BaseMod.addCard(new SS_Hec_Dynamite());
	}
	
	
	
	
	
	/*@SpirePatch(cls = "expansioncontent.cards.StudyTheSpire", method = "use", optional = true)
	public static class StudyTheSpirePatch
	{
		@SpireInsertPatch(rloc = 3, localvars = { "powers" })
	    public static void Insert(final StudyTheSpire __instance, final AbstractPlayer p, final AbstractMonster m, ArrayList<AbstractPower> powers) {
	        powers.add(new StudyPondfishPower(p, p, __instance.magicNumber, __instance.upgraded));
	        powers.add(new StudyFableSpinnerPower(p, p, __instance.magicNumber, __instance.upgraded));
	        powers.add(new StudyHecPower(p, p, __instance.magicNumber, __instance.upgraded));
	    }
	}*/
    @SpirePatch(cls = "slimebound.powers.SlimedPower", method = "onAttacked", optional = true)
	public static class SlimedRelicPatch
	{
	    public static void Prefix(final SlimedPower __instance, final DamageInfo info, final int damageAmount) {
	    	if (info.type == DamageInfo.DamageType.NORMAL) {
	            if (AbstractDungeon.player.hasRelic("Slimebound:AbsorbEndCombat")) {
	                AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
	                AbstractDungeon.player.getRelic("Slimebound:AbsorbEndCombat").flash();
	            }
	    	}
	    }
	}
    @SpirePatch(cls = "expansioncontent.cards.QuickStudy", method = "choiceList", optional = true)
	public static class QuickStudyPatch
	{
		@SpireInsertPatch(rloc = 12, localvars = { "cardList" })
	    public static void Insert(final QuickStudy __instance, ArrayList<OctoChoiceCard> cardList) {
			String NAMESTRING = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").NAMES[3];
			String TEXTSTRING = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").TEXT[3];
			cardList.add(new OctoChoiceCard(expansionContentMod.makeID("9r"), FadingForestBoss.NAME, "cards/replay/ss_forest_skill.png", TEXTSTRING.replace(NAMESTRING, FadingForestBoss.NAME), new SS_Forest_Fishers(), new SS_Forest_LostForever(), new SS_Forest_Treasure()));
			cardList.add(new OctoChoiceCard(expansionContentMod.makeID("10r"), PondfishBoss.NAME, "cards/replay/ss_fish_light.png", TEXTSTRING.replace(NAMESTRING, PondfishBoss.NAME), new SS_Fish_DragToHell(), new SS_Fish_LivingLantern(), new SS_Fish_SixFeetUnder()));
			cardList.add(new OctoChoiceCard(expansionContentMod.makeID("11r"), HellsEngine.NAME, "cards/replay/ss_train.png", TEXTSTRING.replace(NAMESTRING, HellsEngine.NAME), new SS_Hec_Dynamite(), new SS_Hec_ForgedInHellfire(), new SS_Hec_SteelHeart()));
	    }
	}
    @SpirePatch(cls = "expansioncontent.cards.QuickStudy", method = "doChoiceStuff", optional = true)
	public static class QuickStudyChoicePatch
	{
	    public static SpireReturn Prefix(final QuickStudy __instance, final OctoChoiceCard card) {
			if (card.cardID.equals("expansioncontent:9r")) {
				AbstractCard q = new SS_Forest_Fishers();
		        AbstractCard r = new SS_Forest_LostForever();
		        AbstractCard z = new SS_Forest_Treasure();
		        q.freeToPlayOnce = true;
		        r.freeToPlayOnce = true;
		        z.freeToPlayOnce = true;
		        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(q));
		        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(r));
		        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(z));
		        return SpireReturn.Return(null);
			} else if (card.cardID.equals("expansioncontent:10r")) {
				AbstractCard q = new SS_Fish_DragToHell();
		        AbstractCard r = new SS_Fish_LivingLantern();
		        AbstractCard z = new SS_Fish_SixFeetUnder();
		        q.freeToPlayOnce = true;
		        r.freeToPlayOnce = true;
		        z.freeToPlayOnce = true;
		        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(q));
		        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(r));
		        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(z));
		        return SpireReturn.Return(null);
			} else if (card.cardID.equals("expansioncontent:11r")) {
				AbstractCard q = new SS_Hec_Dynamite();
		        AbstractCard r = new SS_Hec_ForgedInHellfire();
		        AbstractCard z = new SS_Hec_SteelHeart();
		        q.freeToPlayOnce = true;
		        r.freeToPlayOnce = true;
		        z.freeToPlayOnce = true;
		        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(q));
		        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(r));
		        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(z));
		        return SpireReturn.Return(null);
			}
	        return SpireReturn.Continue();
	    }
	}
}
