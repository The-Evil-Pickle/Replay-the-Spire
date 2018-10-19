package replayTheSpire.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ReplayRefundAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import javassist.CannotCompileException;
import javassist.CtBehavior;

public class CardFieldStuff {
	@SpirePatch(
	        cls="com.megacrit.cardcrawl.cards.AbstractCard",
	        method=SpirePatch.CLASS
	)
	public static class ReplayCardFields {
		public static SpireField<Boolean> isSpectral = new SpireField<>(() -> false);
		public static SpireField<Integer> refund = new SpireField<>(() -> 0);
	}
	@SpirePatch(
	        cls="com.megacrit.cardcrawl.characters.AbstractPlayer",
	        method="useCard"
	)
	public static class ReplayRefundPatch {
		public static void Postfix(AbstractPlayer __Instance, final AbstractCard c, final AbstractMonster monster, final int energyOnUse) {
			if (CardFieldStuff.ReplayCardFields.refund.get(c) > 0 && c.cost > 0 && !c.freeToPlayOnce && (!__Instance.hasPower("Corruption") || c.type != AbstractCard.CardType.SKILL)) {
	            AbstractDungeon.actionManager.addToBottom(new ReplayRefundAction(c, CardFieldStuff.ReplayCardFields.refund.get(c)));
	        }
		}
	}
	@SpireEnum
    public static AbstractCard.CardTags CHAOS_NEGATIVE_MAGIC;//means higher magic number is less powerful (used by ring of chaos)
}
