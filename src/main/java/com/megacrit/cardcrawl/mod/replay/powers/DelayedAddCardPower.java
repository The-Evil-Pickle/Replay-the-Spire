package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
//import com.megacrit.cardcrawl.powers.PowerType;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;

public class DelayedAddCardPower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "replay:DelayedReturnCardPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCard card;
    private static int fedIDs = 0;
    
    public DelayedAddCardPower(final AbstractCreature owner, final int newAmount, final AbstractCard c) {
        this.name = DelayedAddCardPower.NAME;
        this.ID = POWER_ID + ":" + fedIDs;
        this.owner = owner;
        this.amount = newAmount;
        this.card = c.makeStatEquivalentCopy();
        this.updateDescription();
        this.loadRegion("stasis");
        this.isTurnBased = true;
        fedIDs++;
    }
    
    @Override
    public void updateDescription() {
        this.description = DelayedAddCardPower.DESCRIPTIONS[0] + this.amount + DelayedAddCardPower.DESCRIPTIONS[1] + this.card.name + DelayedAddCardPower.DESCRIPTIONS[2];
    }
    
    @Override
    public void atStartOfTurn() {
    	if (this.amount > 1) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        } else {
        	AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(this.card, 1, true, true));
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = DelayedAddCardPower.powerStrings.NAME;
        DESCRIPTIONS = DelayedAddCardPower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new DelayedAddCardPower(owner, amount, card.makeCopy());
	}
}
