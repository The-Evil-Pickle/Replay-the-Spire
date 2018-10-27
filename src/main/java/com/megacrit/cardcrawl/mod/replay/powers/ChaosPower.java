package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import tobyspowerhouse.powers.TPH_ConfusionPower;

public class ChaosPower extends AbstractPower
{
    public static final String POWER_ID = "ReplayChaosPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int baseamount;
    
    public ChaosPower(final AbstractCreature owner, int amount) {
        this.name = ChaosPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.baseamount = amount;
        this.updateDescription();
        this.loadRegion("unawakened");
    }

    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        this.baseamount += stackAmount;
        this.updateDescription();
    }
    
    @Override
    public void updateDescription() {
        this.description = ChaosPower.DESCRIPTIONS[0] + this.baseamount + ChaosPower.DESCRIPTIONS[1] + this.amount + ChaosPower.DESCRIPTIONS[2];
    }

	@Override
    public void atEndOfTurn(final boolean isPlayer) {
		this.amount = this.baseamount;
        this.updateDescription();
	}
	
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = ChaosPower.powerStrings.NAME;
        DESCRIPTIONS = ChaosPower.powerStrings.DESCRIPTIONS;
    }
}
