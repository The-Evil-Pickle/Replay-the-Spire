package replayTheSpire.patches;

import java.util.ArrayList;
import java.util.Iterator;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.BottledSteam;

import replayTheSpire.ReplayTheSpireMod;


public class BottlePatches {
	@SpirePatch(
	        cls="com.megacrit.cardcrawl.cards.AbstractCard",
	        method=SpirePatch.CLASS
	)
	public static class BottleFields {
		public static SpireField<Boolean> inBottleFlurry = new SpireField<>(() -> false);
	    public static SpireField<Boolean> inBottleSteam = new SpireField<>(() -> false);
	    public static SpireField<Boolean> inBottleWhirlpool = new SpireField<>(() -> false);
	    public static SpireField<Boolean> inBottleFirefly = new SpireField<>(() -> false);
	}
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method="makeStatEquivalentCopy")
    public static class StatEquivCopyPatch {
    	public static AbstractCard Postfix(AbstractCard __Result, AbstractCard __Instance) {
    		BottlePatches.BottleFields.inBottleFlurry.set(__Result, BottlePatches.BottleFields.inBottleFlurry.get(__Instance));
    		BottlePatches.BottleFields.inBottleSteam.set(__Result, BottlePatches.BottleFields.inBottleSteam.get(__Instance));
    		BottlePatches.BottleFields.inBottleWhirlpool.set(__Result, BottlePatches.BottleFields.inBottleWhirlpool.get(__Instance));
    		BottlePatches.BottleFields.inBottleFirefly.set(__Result, BottlePatches.BottleFields.inBottleFirefly.get(__Instance));
    		
    		return __Result;
    	}
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction", method="update")
    public static class ShufflePatch {
    	
    	@SpireInsertPatch(rloc=4)
    	public static void DiscardOrderingPatch(EmptyDeckShuffleAction __Instance) {
    		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(BottledSteam.ID)) {
    			BottledSteam bottle = (BottledSteam)ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic(BottledSteam.ID);
    			if (bottle.isActive) {
    				ArrayList<AbstractCard> bottleCards = new ArrayList<AbstractCard>();
        			final Iterator<AbstractCard> c = AbstractDungeon.player.discardPile.group.iterator();
        	        if (c.hasNext()) {
        	        	final AbstractCard e = c.next();
        	            if (BottlePatches.BottleFields.inBottleSteam.get(e)) {
        	            	bottleCards.add(e);
        	            	c.remove();
        	            }
        	        }
        	        if (bottleCards.size() > 0) {
        	        	for (AbstractCard card : bottleCards) {
        	        		AbstractDungeon.player.discardPile.addToTop(card);
        	        	}
        	        	bottle.flash();
        	        	AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RelicAboveCreatureAction(AbstractDungeon.player, bottle));
        	        }
    			}
    		}
    	}
    }
    
}
