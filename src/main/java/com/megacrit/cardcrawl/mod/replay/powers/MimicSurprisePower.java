package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.graphics.*;
import replayTheSpire.*;

public class MimicSurprisePower
  extends AbstractPower
{
	public static final String POWER_ID = "MimicSurprisePower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MimicSurprisePower");
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final int ENERGY_AMT = 1;
    private boolean justApplied;
	
	
	public MimicSurprisePower(AbstractCreature owner) {
		this (owner, 1, false);
	}
	public MimicSurprisePower(AbstractCreature owner, final int amount) {
		this (owner, amount, false);
	}
	public MimicSurprisePower(AbstractCreature owner, final int amount, final boolean justApplied)
	{
		this.name = NAME;
		this.ID = "MimicSurprisePower";
		this.owner = owner;
		this.amount = amount;
		this.justApplied = justApplied;
        this.isTurnBased = true;
		this.description = DESCRIPTIONS[0];
		loadRegion("surprise");
		//this.img = new Texture("img/powers/Reflection.png");
	}
    
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
	
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
    /*@Override
    public void atEndOfTurn(final boolean isPlayer) {
		if (this.justApplied) {
			this.justApplied = false;
			return;
		}
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "MimicSurprisePower"));
    }*/
	
	@Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "MimicSurprisePower"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "MimicSurprisePower", 1));
        }
    }
	
	@Override
	public void onEnergyRecharge() {
		this.flash();
        AbstractDungeon.player.loseEnergy(this.amount);//AbstractDungeon.player.energy.energy
	}
	
}
