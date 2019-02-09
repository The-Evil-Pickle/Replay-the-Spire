package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
//import com.megacrit.cardcrawl.powers.PowerType;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class AgingPower extends AbstractPower
{
    public static final String POWER_ID = "Replay:Aging";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int baseAmount;
    private int langStacks;
    
    public AgingPower(final AbstractCreature owner, final int amount) {
        this.baseAmount = amount;
        this.name = AgingPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/countdown.png");
        this.type = PowerType.DEBUFF;
        this.langStacks = 1;
    }
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (damageAmount > 0 && !this.owner.hasPower("Buffer")) {
            this.amount -= Math.round(damageAmount);
            int langamt = 0;
            while (this.amount <= 0) {
            	this.amount += this.baseAmount;
            	langamt += this.langStacks;
            }
            if (langamt > 0) {
            	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new LanguidPower(this.owner, langamt, this.owner.isPlayer), langamt));
            	this.flash();
            }
        }
        return damageAmount;
    }
    @Override
    public void updateDescription() {
        this.description = AgingPower.DESCRIPTIONS[0] + this.baseAmount + AgingPower.DESCRIPTIONS[1] + this.owner.name + AgingPower.DESCRIPTIONS[2];
    }

    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
    	this.baseAmount = Math.min(this.baseAmount, stackAmount);
    	this.langStacks += 1;
    }
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = AgingPower.powerStrings.NAME;
        DESCRIPTIONS = AgingPower.powerStrings.DESCRIPTIONS;
    }
}
