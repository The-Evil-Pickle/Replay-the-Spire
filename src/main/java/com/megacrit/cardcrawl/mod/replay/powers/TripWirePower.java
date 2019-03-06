package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;

public class TripWirePower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "Replay:Tripwire";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public TripWirePower(final AbstractCreature owner) {
    	this(owner, 1);
    }
    public TripWirePower(final AbstractCreature owner, final int amount) {
        this.name = TripWirePower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/stslib/powers/32/stun.png");
    }
    
    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (int i = 0; i < this.amount; ++i) {
            	AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(AbstractDungeon.getRandomMonster(), owner));
            }
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this.ID));
        }
    }
    
    @Override
    public void updateDescription() {
    	this.description = TripWirePower.DESCRIPTIONS[0] + this.amount + TripWirePower.DESCRIPTIONS[1];
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = TripWirePower.powerStrings.NAME;
        DESCRIPTIONS = TripWirePower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new TripWirePower(owner, amount);
	}
}
