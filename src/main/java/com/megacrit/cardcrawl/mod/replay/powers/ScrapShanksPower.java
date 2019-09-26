package com.megacrit.cardcrawl.mod.replay.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;

public class ScrapShanksPower extends TwoAmountPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "Scrap Shanks";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public ScrapShanksPower(final AbstractCreature owner, final int amt) {
        this.name = ScrapShanksPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.amount2 = -1;
        if (amt > 0) {
        	this.amount = amt;
        } else {
        	this.amount2 = Math.abs(amt);
        }
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/infiniteBlades.png");
    }
    public ScrapShanksPower(final AbstractCreature owner, final int amt, final int amt2) {
        this.name = ScrapShanksPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.amount2 = amt2;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/infiniteBlades.png");
    }
    @Override
    public void stackPower(final int stackAmount) {
    	if (stackAmount > 0) {
    		if (this.amount < 0)
    			this.amount = 0;
    		this.amount += stackAmount;
    	} else {
    		if (this.amount2 < 0)
    			this.amount2 = 0;
    		this.amount2 += Math.abs(stackAmount);
    	}
        this.fontScale = 8.0f;
    }
    
    @Override
    public void updateDescription() {
        this.description = ScrapShanksPower.DESCRIPTIONS[0];
        if (this.amount > 0) {
        	this.description += this.amount;
        	if (this.amount > 1) {
        		this.description += ScrapShanksPower.DESCRIPTIONS[2];
        	} else {
        		this.description += ScrapShanksPower.DESCRIPTIONS[1];
        	}
        }
        if (this.amount2 > 0) {
        	if (this.amount > 0) {
        		this.description += ScrapShanksPower.DESCRIPTIONS[3];
        	}
        	this.description += this.amount2;
        	if (this.amount2 > 1) {
        		this.description += ScrapShanksPower.DESCRIPTIONS[4] + ScrapShanksPower.DESCRIPTIONS[2];
        	} else {
        		this.description += ScrapShanksPower.DESCRIPTIONS[4] + ScrapShanksPower.DESCRIPTIONS[1];
        	}
        }
        this.description += ScrapShanksPower.DESCRIPTIONS[5];
    }
    
    @Override
    public void onSpecificTrigger() {
    	if (this.amount > 0) {
    		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), this.amount));
    	}
    	if (this.amount2 > 0) {
    		AbstractCard shiv = new Shiv();
    		shiv.upgrade();
    		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(shiv, this.amount2));
    	}
    	this.flash();
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Scrap Shanks");
        NAME = ScrapShanksPower.powerStrings.NAME;
        DESCRIPTIONS = ScrapShanksPower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new ScrapShanksPower(owner, amount, amount2);
	}
}