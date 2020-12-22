package com.megacrit.cardcrawl.mod.replay.actions.replayxover;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import theHexaghost.powers.BurnPower;

public class HexaringAction extends AbstractGameAction
{
	int amt;   
	AbstractRelic relic;
    public HexaringAction(AbstractRelic relic, int amt) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DEBUFF;
        this.relic = relic;
        this.amt = amt;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
        	for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            	if (m.hasPower(BurnPower.POWER_ID)) {
            		int a = Math.min(m.getPower(BurnPower.POWER_ID).amount, this.amt);
            		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, a), a));
            		AbstractDungeon.actionManager.addToTop(new ReducePowerAction(m, AbstractDungeon.player, m.getPower(BurnPower.POWER_ID), a));
            		AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(m, this.relic));
            	}
            }
        }
        this.tickDuration();
    }
}