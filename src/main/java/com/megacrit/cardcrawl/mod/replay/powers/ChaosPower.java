package com.megacrit.cardcrawl.mod.replay.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.mod.replay.relics.RingOfChaos;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import basemod.interfaces.CloneablePowerInterface;

public class ChaosPower extends TwoAmountPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "ReplayChaosPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int baseamount;
    public ChaosPower(final AbstractCreature owner, int amount) {
        this(owner, amount, -1);
    }
    public ChaosPower(final AbstractCreature owner, int amount, int amount2) {
        this.name = ChaosPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount2;
        this.baseamount = amount;
        this.amount2 = amount;
        if (amount2 > 0) {
        	this.isTurnBased = true;
        	this.type = PowerType.DEBUFF;
        }
        this.updateDescription();
        this.loadRegion("unawakened");
    }

    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount2 += stackAmount;
        this.baseamount += stackAmount;
        this.amount = -1;
        this.isTurnBased = false;
        this.updateDescription();
    }
    
    @Override
    public void updateDescription() {
        this.description = ChaosPower.DESCRIPTIONS[0] + this.baseamount + ChaosPower.DESCRIPTIONS[1] + this.amount2 + ChaosPower.DESCRIPTIONS[2];
        if (this.isTurnBased) {
        	this.description += " NL Lasts #b" + this.amount + " more rounds.";
        }
    }

	@Override
    public void atEndOfTurn(final boolean isPlayer) {
		this.amount2 = this.baseamount;
		if (this.isTurnBased) {
	        if (this.amount == 0) {
	            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
	        }
	        else {
	            this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
	        }
		}
        this.updateDescription();
	}

    @Override
    public void onCardDraw(final AbstractCard card) {
    	if (this.amount2 > 0) {
			if (RingOfChaos.ChaosScrambleCard(card)) {
				this.amount2--;
				this.updateDescription();
			}
		}
    }
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = ChaosPower.powerStrings.NAME;
        DESCRIPTIONS = ChaosPower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new ChaosPower(owner, amount);
	}
}
