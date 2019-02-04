package replayTheSpire.replayxover;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss.*;
import com.megacrit.cardcrawl.mod.replay.powers.replayxover.StudyPondfishPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.BaseMod;
import slimebound.cards.*;

public class slimeboundbs {
	
	public static void addBossCards() {
		BaseMod.addCard(new SS_Fish_DragToHell());
		BaseMod.addCard(new SS_Fish_LivingLantern());
		BaseMod.addCard(new SS_Fish_SixFeetUnder());
		BaseMod.addCard(new SS_Fish_CaptainsOrders());
	}
	
	
	@SpireEnum
    public static AbstractCard.CardTags STUDY_PONDFISH;
    @SpireEnum
    public static AbstractCard.CardTags STUDY_FOREST;
	
	
	@SpirePatch(cls = "slimebound.cards.StudyTheSpire", method = "use", optional = true)
	public static class StudyTheSpirePatch
	{
		@SpireInsertPatch(rloc = 8, localvars = { "powers" })
	    public static void Insert(final StudyTheSpire __instance, final AbstractPlayer p, final AbstractMonster m, @ByRef final ArrayList<AbstractPower>[] powers) {
	        powers[0].add(new StudyPondfishPower(p, p, __instance.magicNumber));
	    }
	}
}
