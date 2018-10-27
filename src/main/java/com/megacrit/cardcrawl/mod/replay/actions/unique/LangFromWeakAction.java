package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.powers.LanguidPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import replayTheSpire.ReplayTheSpireMod;

public class LangFromWeakAction extends AbstractGameAction
{
    private static final float DUR = 0.25f;

    public LangFromWeakAction(final AbstractCreature target, final AbstractCreature source) {
        this(target, source, 1);
    }
    public LangFromWeakAction(final AbstractCreature target, final AbstractCreature source, final int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.25f;
        this.startDuration = 0.25f;
    }
    
    @Override
    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.duration == this.startDuration) {
            if (this.target.hasPower(WeakPower.POWER_ID)) {
            	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new LanguidPower(this.target, this.amount * this.target.getPower(WeakPower.POWER_ID).amount, false), this.amount * this.target.getPower(WeakPower.POWER_ID).amount));
            }
        }
        this.tickDuration();
    }
}
