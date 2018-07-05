package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ScrapShanksPower extends AbstractPower
{
    public static final String POWER_ID = "Scrap Shanks";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public int normalStack;
    public int upgradedStack;
    
    public ScrapShanksPower(final AbstractCreature owner, final int amount) {
        this.name = ScrapShanksPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.normalStack = 0;
        this.upgradedStack = 0;
        if (amount > 0) {
        	this.normalStack = amount;
        } else {
        	this.upgradedStack = Math.abs(amount);
        }
        this.amount = this.normalStack + this.upgradedStack;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/infiniteBlades.png");
    }
    
    @Override
    public void stackPower(final int stackAmount) {
    	if (stackAmount > 0) {
    		this.normalStack += stackAmount;
    	} else {
    		this.upgradedStack += Math.abs(stackAmount);
    	}
        this.fontScale = 8.0f;
        this.amount += Math.abs(stackAmount);
    }
    
    @Override
    public void updateDescription() {
        this.description = ScrapShanksPower.DESCRIPTIONS[0];
        if (this.normalStack > 0) {
        	this.description += this.normalStack;
        	if (this.normalStack > 1) {
        		this.description += ScrapShanksPower.DESCRIPTIONS[2];
        	} else {
        		this.description += ScrapShanksPower.DESCRIPTIONS[1];
        	}
        }
        if (this.upgradedStack > 0) {
        	if (this.normalStack > 0) {
        		this.description += ScrapShanksPower.DESCRIPTIONS[3];
        	}
        	this.description += this.upgradedStack;
        	if (this.upgradedStack > 1) {
        		this.description += ScrapShanksPower.DESCRIPTIONS[4] + ScrapShanksPower.DESCRIPTIONS[2];
        	} else {
        		this.description += ScrapShanksPower.DESCRIPTIONS[4] + ScrapShanksPower.DESCRIPTIONS[1];
        	}
        }
        this.description += ScrapShanksPower.DESCRIPTIONS[5];
    }
    

    @Override
    public void onSpecificTrigger() {
    	if (this.normalStack > 0) {
    		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), this.normalStack));
    	}
    	if (this.upgradedStack > 0) {
    		AbstractCard shiv = new Shiv();
    		shiv.upgrade();
    		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(shiv, this.upgradedStack));
    	}
    	this.flash();
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Scrap Shanks");
        NAME = ScrapShanksPower.powerStrings.NAME;
        DESCRIPTIONS = ScrapShanksPower.powerStrings.DESCRIPTIONS;
    }
}