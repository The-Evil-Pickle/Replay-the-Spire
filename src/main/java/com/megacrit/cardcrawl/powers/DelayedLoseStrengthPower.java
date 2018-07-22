package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class DelayedLoseStrengthPower extends AbstractPower
{
    public static final String POWER_ID = "Flex";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    int delay;
    
    public DelayedLoseStrengthPower(final AbstractCreature owner, final int newAmount) {
        this.name = DelayedLoseStrengthPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
        this.loadRegion("flex");
        this.delay = 1;
    }
    
    @Override
    public void updateDescription() {
        this.description = DelayedLoseStrengthPower.DESCRIPTIONS[0] + this.amount + DelayedLoseStrengthPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
    	if (this.delay <= 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.amount), -this.amount));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    	}
    	this.delay--;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Flex");
        NAME = DelayedLoseStrengthPower.powerStrings.NAME;
        DESCRIPTIONS = DelayedLoseStrengthPower.powerStrings.DESCRIPTIONS;
    }
}
