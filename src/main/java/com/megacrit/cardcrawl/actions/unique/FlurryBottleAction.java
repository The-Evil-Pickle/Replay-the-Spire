package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.BottledFlurry;

import replayTheSpire.patches.BottlePatches;

public class FlurryBottleAction extends AbstractGameAction
{
    private BottledFlurry bottle;
    
    public FlurryBottleAction(final BottledFlurry bottle) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.bottle = bottle;
    }
    
    public void update() {
        boolean gotem = false;
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (BottlePatches.BottleFields.inBottleFlurry.get(c)) {
            	if (c.isEthereal) {
            		AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(c));
            	} else {
            		c.retain = true;
            	}
            	c.flash();
            	gotem = true;
            }
        }
        if (gotem) {
        	this.bottle.flash();
        	AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction(AbstractDungeon.player, this.bottle));
        }
        this.isDone = true;
    }
}
