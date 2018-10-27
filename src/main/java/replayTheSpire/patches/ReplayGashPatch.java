package replayTheSpire.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.GashAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.blue.ReplayGash;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import basemod.ReflectionHacks;
import replayTheSpire.ReplayTheSpireMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.defect.GashAction", method = "update")
public class ReplayGashPatch {
	public static void Prefix(GashAction __Instance) {
		float duration = (float)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractGameAction.class, "duration");
		if (duration == Settings.ACTION_DUR_FAST) {
            for (final AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof ReplayGash) {
                    final AbstractCard abstractCard = c;
                    abstractCard.baseDamage += __Instance.amount;
                    c.applyPowers();
                }
            }
            for (final AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c instanceof ReplayGash) {
                    final AbstractCard abstractCard2 = c;
                    abstractCard2.baseDamage += __Instance.amount;
                    c.applyPowers();
                }
            }
            for (final AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof ReplayGash) {
                    final AbstractCard abstractCard3 = c;
                    abstractCard3.baseDamage += __Instance.amount;
                    c.applyPowers();
                }
            }
        }
	}
}
