package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class MightPower extends AbstractPower
{
    public static final String POWER_ID = "Replay:Might";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean justApplied;
    private static final float EFFECTIVENESS = 1.5f;
    private static final int EFFECTIVENESS_STRING = 50;
    
    public MightPower(final AbstractCreature owner, final int amount, final boolean isSourceMonster) {
        this.justApplied = false;
        this.name = MightPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("strength");
        if (AbstractDungeon.actionManager.turnHasEnded && isSourceMonster) {
            this.justApplied = true;
        }
        this.isTurnBased = true;
    }

	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
	
    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }
    
    @Override
    public void updateDescription() {
    	if (this.amount > 1) {
    		this.description = DESCRIPTIONS[0] + "#b" + this.amount + DESCRIPTIONS[1];
    	} else {
    		this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
    	}
        
    }
    @Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
    	if (this.justApplied && !this.owner.isPlayer) {
    		return damage;
    	}
        if (type != DamageInfo.DamageType.NORMAL) {
            return damage;
        }
        return damage * 1.25f;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = MightPower.powerStrings.NAME;
        DESCRIPTIONS = MightPower.powerStrings.DESCRIPTIONS;
    }
}
