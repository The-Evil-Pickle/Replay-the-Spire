package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.powers.AccuracyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;

public class ShivTossAction extends AbstractGameAction
{
    public ShivTossAction(final int amt) {
        this.amount = amt;
        this.source = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.DISCARD;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
            	if (c instanceof Shiv) {
            		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new AccuracyPower(this.target, this.amount), this.amount));
            		AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(c));
            	}
            }
        }
        this.tickDuration();
    }
}
