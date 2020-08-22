package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.LoomingThreatPowerAction;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class LoomingThreatPower extends AbstractPower
{
    public static final String POWER_ID = "LoomingThreat";
    private static final PowerStrings powerStrings;
    
    public LoomingThreatPower(final AbstractCreature owner, final int strengthAmount) {
        this.name = LoomingThreatPower.powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = strengthAmount;
        this.updateDescription();
        this.loadRegion("establishment");
    }
    
    @Override
    public void updateDescription() {
        this.description = LoomingThreatPower.powerStrings.DESCRIPTIONS[0] + this.amount + LoomingThreatPower.powerStrings.DESCRIPTIONS[1];
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        this.flash();
        this.addToBot(new LoomingThreatPowerAction(this.amount));
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}
