package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;

import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.core.*;

public class ReplayBrewPower extends AbstractPower
{
    public static final String POWER_ID = "ReplayBrewPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public ReplayBrewPower(final AbstractCreature owner, final int cardAmt) {
        this.name = ReplayBrewPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = cardAmt;
        this.updateDescription();
        this.loadRegion("brewmaster");
    }
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
    
    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
        	for (final AbstractPotion p : AbstractDungeon.player.potions) {
                if (p instanceof PotionSlot) {
                    this.flash();
                    break;
                }
            }
            for (int i = 0; i < this.amount; ++i) {
            	AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
            }
        }
    }
    
    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }
    
    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = ReplayBrewPower.DESCRIPTIONS[0] + this.amount + ReplayBrewPower.DESCRIPTIONS[2];
        }
        else {
            this.description = ReplayBrewPower.DESCRIPTIONS[0] + this.amount + ReplayBrewPower.DESCRIPTIONS[1];
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = ReplayBrewPower.powerStrings.NAME;
        DESCRIPTIONS = ReplayBrewPower.powerStrings.DESCRIPTIONS;
    }
}
