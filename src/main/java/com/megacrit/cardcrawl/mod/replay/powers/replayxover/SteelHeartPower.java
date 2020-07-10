package com.megacrit.cardcrawl.mod.replay.powers.replayxover;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import basemod.interfaces.CloneablePowerInterface;
import replayTheSpire.ReplayTheSpireMod;

public class SteelHeartPower extends TwoAmountPower implements CloneablePowerInterface{
	public static final String POWER_ID = "Replay:SteelHeart";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
	
	public SteelHeartPower(final AbstractCreature owner, final int max, final int delta) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = max;
        this.amount2 = delta;
        this.updateDescription();
        this.loadRegion("crystallizer");
    }
    
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    }
	@Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
    	this.amount = Math.max(this.amount, stackAmount);
    	this.amount2 += 1;
    }
	@Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
        	if (this.owner.hasPower(PlatedArmorPower.POWER_ID) == false || this.owner.getPower(PlatedArmorPower.POWER_ID).amount < this.amount) {
        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner, this.amount2), this.amount2));
        	}
        }
    }
	
	@Override
	public AbstractPower makeCopy() {
		// TODO Auto-generated method stub
		return new SteelHeartPower(owner, amount, amount2);
	}
	static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
