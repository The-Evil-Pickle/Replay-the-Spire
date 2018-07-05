package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.ui.panels.*;

public class RetainSomeEnergyPower extends AbstractPower
{
    public static final String POWER_ID = "Retain Some Energy";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RetainSomeEnergyPower(final AbstractCreature owner, final int energyAmt) {
        this.name = RetainSomeEnergyPower.NAME;
        this.ID = "Retain Some Energy";
        this.owner = owner;
        this.amount = energyAmt;
        this.updateDescription();
		this.loadRegion("conserve");
        //this.img = ImageMaster.loadImage("images/powers/32/outmaneuver.png");
    }
    
    @Override
    public void updateDescription() {
        this.description = RetainSomeEnergyPower.DESCRIPTIONS[0] + this.amount + RetainSomeEnergyPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (isPlayer && EnergyPanel.totalCount > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
			int r = Math.min(EnergyPanel.totalCount, this.amount);
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new EnergizedPower(this.owner, r), r));
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Retain Some Energy");
        NAME = RetainSomeEnergyPower.powerStrings.NAME;
        DESCRIPTIONS = RetainSomeEnergyPower.powerStrings.DESCRIPTIONS;
    }
}
