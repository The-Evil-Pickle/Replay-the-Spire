package replayTheSpire.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.mod.replay.relics.BlankCodex;
import com.megacrit.cardcrawl.mod.replay.relics.M_MistRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import replayTheSpire.ReplayTheSpireMod;

@SpirePatch(clz = AbstractCard.class, method = "getPrice")
public class CardPricePatch
{
    public static int PostFix(int result, final CardRarity rarity) {
        if (rarity == CardRarity.BASIC) {
        	if (AbstractDungeon.player != null) {
        		for (AbstractRelic r : AbstractDungeon.player.relics) {
        			if (r instanceof M_MistRelic) {
        				return AbstractCard.getPrice(AbstractCard.CardRarity.COMMON);
        			}
        		}
        	}
        }
        return result;
    }
}
