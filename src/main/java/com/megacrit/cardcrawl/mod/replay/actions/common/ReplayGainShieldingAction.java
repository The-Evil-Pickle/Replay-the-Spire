package com.megacrit.cardcrawl.mod.replay.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import replayTheSpire.*;

public class ReplayGainShieldingAction extends AbstractGameAction
{
    private static final float DUR = 0.25f;
    
    public ReplayGainShieldingAction(final AbstractCreature target, final AbstractCreature source, final int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.BLOCK;
        this.duration = 0.25f;
        this.startDuration = 0.25f;
    }
    
    public ReplayGainShieldingAction(final AbstractCreature target, final AbstractCreature source, final int amount, final boolean superFast) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.BLOCK;
        this.duration = 0.1f;
        this.startDuration = 0.1f;
    }
    
    @Override
    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.duration == this.startDuration) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SHIELD));
            //this.target.addBlock(this.amount);
			ReplayTheSpireMod.addShielding(this.target, this.amount);
            for (final AbstractCard c : AbstractDungeon.player.hand.group) {
                c.applyPowers();
            }
        }
        this.tickDuration();
    }
}
