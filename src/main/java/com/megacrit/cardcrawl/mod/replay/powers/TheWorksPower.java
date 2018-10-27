package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class TheWorksPower extends AbstractPower
{
    public static final String POWER_ID = "The Works";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    boolean isActive;
    private static final int RESET_AMT = 3;
    private int damage;
    private int energy;
    
    public TheWorksPower(final AbstractCreature owner, final int damage) {
        this.name = TheWorksPower.NAME;
        this.ID = TheWorksPower.POWER_ID;
        this.owner = owner;
        this.amount = RESET_AMT;
        this.damage = damage;
        this.energy = 1;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/drawCard.png");
        this.isActive = true;
    }
    
    @Override
    public void updateDescription() {
        this.description = TheWorksPower.DESCRIPTIONS[0] + RESET_AMT + TheWorksPower.DESCRIPTIONS[1];
        for (int i = 0; i < this.energy; i++) {
        	this.description += "[E]";
        }
        this.description += TheWorksPower.DESCRIPTIONS[2] + this.damage + TheWorksPower.DESCRIPTIONS[3];
    }

    @Override
    public void atEndOfRound() {
    	AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.damage += stackAmount;
        this.energy++;
    }
    
    @Override
    public void onSpecificTrigger() {
    	--this.amount;
        if (this.amount == 0) {
            this.flash();
            this.amount = RESET_AMT;
            AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster(true), new DamageInfo(this.owner, this.damage, DamageInfo.DamageType.THORNS), 1));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.energy));
        }
        this.updateDescription();
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("The Works");
        NAME = TheWorksPower.powerStrings.NAME;
        DESCRIPTIONS = TheWorksPower.powerStrings.DESCRIPTIONS;
    }
}
