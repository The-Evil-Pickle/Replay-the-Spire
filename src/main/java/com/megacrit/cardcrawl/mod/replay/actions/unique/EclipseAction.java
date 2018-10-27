package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
//import com.megacrit.cardcrawl.actions.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

public class EclipseAction extends AbstractGameAction
{
    private int refund;
    private float startingDuration;
    
    public EclipseAction(final AbstractCreature target, final int refund) {
        this.setValues(target, target);
        this.refund = refund;
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            final int count = AbstractDungeon.player.hand.size();
            AbstractDungeon.actionManager.addToTop(new EclipseSecondAction(count));
            for (int i = 0; i < count; ++i) {
                AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, true, true));
            }
            /*
            if (this.refund > 0 ) {
            	AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.refund));
            }*/
        }
        this.tickDuration();
    }
}
