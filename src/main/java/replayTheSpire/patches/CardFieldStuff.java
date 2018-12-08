package replayTheSpire.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.TheMausoleum;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayRefundAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class CardFieldStuff {
	@SpirePatch(
	        cls="com.megacrit.cardcrawl.cards.AbstractCard",
	        method=SpirePatch.CLASS
	)
	public static class ReplayCardFields {
		public static SpireField<Boolean> isSpectral = new SpireField<>(() -> false);
	}
	@SpireEnum
    public static AbstractCard.CardTags CHAOS_NEGATIVE_MAGIC;//means higher magic number is less powerful (used by ring of chaos)
	
	///////[[[[[[[[[[[CHAOS NEGATIVE TAGGING]]]]]]]]]]]/////////
	@SpirePatch(clz = Hemokinesis.class, method = "<ctor>")
    public static class ChangeOptionText
    {
        public static void Postfix(final Hemokinesis __instance) {
            __instance.tags.add(CHAOS_NEGATIVE_MAGIC);
        }
    }
}
