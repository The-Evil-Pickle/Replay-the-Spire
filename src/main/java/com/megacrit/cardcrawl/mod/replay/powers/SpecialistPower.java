package com.megacrit.cardcrawl.mod.replay.powers;

import replayTheSpire.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
//import com.megacrit.cardcrawl.powers.PowerType;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.unlock.*;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class SpecialistPower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "Specialist";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public SpecialistPower(final AbstractCreature owner, final int amount) {
        this.name = SpecialistPower.NAME;
        this.ID = "Specialist";
        this.owner = owner;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        if (this.amount <= -999) {
            this.amount = -999;
        }
        this.updateDescription();
        this.loadRegion("specialist");
		//this.img = ImageMaster.loadImage("images/powers/32/venomology.png");
        this.canGoNegative = true;
    }
    
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
	
    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "Specialist"));
        }
        if (this.amount >= 50) {
            UnlockTracker.unlockAchievement("JAXXED");
        }
        if (this.amount >= 999) {
            this.amount = 999;
        }
        if (this.amount <= -999) {
            this.amount = -999;
        }
    }
    
    @Override
    public void reducePower(final int reduceAmount) {
        this.fontScale = 8.0f;
        this.amount -= reduceAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, SpecialistPower.NAME));
        }
        if (this.amount >= 999) {
            this.amount = 999;
        }
        if (this.amount <= -999) {
            this.amount = -999;
        }
    }
    
    @Override
    public void updateDescription() {
        if (this.amount > 0) {
            this.description = SpecialistPower.DESCRIPTIONS[0] + this.amount + ".";
            this.type = PowerType.BUFF;
        }
        else {
            final int tmp = -this.amount;
            this.description = SpecialistPower.DESCRIPTIONS[1] + tmp + ".";
            this.type = PowerType.DEBUFF;
        }
    }
	/*
    @Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.NORMAL) {
            return damage + this.amount;
        }
        return damage;
    }
	*/
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Specialist");
        NAME = SpecialistPower.powerStrings.NAME;
        DESCRIPTIONS = SpecialistPower.powerStrings.DESCRIPTIONS;
    }
	@Override
	public AbstractPower makeCopy() {
		return new SpecialistPower(owner, amount);
	}
}
