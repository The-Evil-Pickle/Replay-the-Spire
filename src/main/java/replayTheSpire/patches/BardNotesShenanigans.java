package replayTheSpire.patches;

import java.util.List;

import com.evacipated.cardcrawl.mod.bard.notes.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.M_MusicBoxCore;

import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.replayxover.bard.OrbNote;


public class BardNotesShenanigans {
	@SpirePatch(cls = "com.evacipated.cardcrawl.mod.bard.cards.AbstractBardCard", method = "determineNoteTypes", optional = true)
	public static class DetermineNotePatch {
		public static List<AbstractNote> Postfix(final List<AbstractNote> result, final AbstractCard card) {
			if (ReplayTheSpireMod.foundmod_bard) {
				if (card.cardID.equals(GrandFinale.ID) || card.cardID.equals("ReplayTheSpireMod:??????????????????????")) {
					result.clear();
					result.add(WildCardNote.get());
				} else if ((AbstractDungeon.player == null || AbstractDungeon.player.hasRelic(M_MusicBoxCore.ID)) && (card.showEvokeValue || card.showEvokeOrbCount > 0 || (result.isEmpty() && card.color == AbstractCard.CardColor.BLUE))) {
					result.add(OrbNote.get());
				}
			}
			return result;
		}
	}
}
