package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class LanguidPower extends AbstractPower
{
    public static final String POWER_ID = "LanguidPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean justApplied;
    private static final int EFFECTIVENESS_STRING = 25;
    
    public LanguidPower(final AbstractCreature owner, final int amount, final boolean isSourceMonster) {
        this.justApplied = false;
        this.name = LanguidPower.NAME;
        this.ID = "LanguidPower";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("weak");
        if (isSourceMonster) {
            this.justApplied = true;
        }
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        //this.priority = 99;
    }
    
    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "LanguidPower"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "LanguidPower", 1));
        }
    }
    
    @Override
    public void updateDescription() {
        this.description = LanguidPower.DESCRIPTIONS[0] + this.owner.name + LanguidPower.DESCRIPTIONS[1] + this.amount + LanguidPower.DESCRIPTIONS[2] + this.amount + LanguidPower.DESCRIPTIONS[3];
    }
    
    @Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.NORMAL) {
            return damage;
        }
        return damage - this.amount;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("LanguidPower");
        NAME = LanguidPower.powerStrings.NAME;
        DESCRIPTIONS = LanguidPower.powerStrings.DESCRIPTIONS;
    }
}
