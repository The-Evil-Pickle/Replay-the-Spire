package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
//import com.megacrit.cardcrawl.powers.PowerType;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;

public class DelayedLoseStrengthPower extends AbstractPower
{
    public static final String POWER_ID = "Replay:Fed";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    int delay;
    private static int fedIDs = 0;
    
    public DelayedLoseStrengthPower(final AbstractCreature owner, final int newAmount) {
        this.name = DelayedLoseStrengthPower.NAME;
        this.ID = POWER_ID + ":" + fedIDs;
        this.owner = owner;
        this.amount = newAmount;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
        this.loadRegion("flex");
        this.delay = 1;
        fedIDs++;
    }
    public void stackPower(final int stackAmount) {
    	if (this.delay > 0) {
            this.fontScale = 8.0f;
            this.amount += stackAmount;
    	} else {
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.amount), -this.amount));
    		this.fontScale = 8.0f;
            this.amount = stackAmount;
            this.delay = 1;
    	}
    }
    
    @Override
    public void updateDescription() {
        this.description = DelayedLoseStrengthPower.DESCRIPTIONS[0] + this.amount + DelayedLoseStrengthPower.DESCRIPTIONS[1] + this.amount + DelayedLoseStrengthPower.DESCRIPTIONS[2];
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
    	if (this.delay <= 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.amount), -this.amount));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    	} else {
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
    	}
    	this.delay--;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = DelayedLoseStrengthPower.powerStrings.NAME;
        DESCRIPTIONS = DelayedLoseStrengthPower.powerStrings.DESCRIPTIONS;
    }
}
